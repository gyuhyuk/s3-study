package capstone.viewIt.resume.service;

import capstone.viewIt.categoryAndTitle.domain.CategoryAndTitle;
import capstone.viewIt.categoryAndTitle.repository.CategoryAndTitleRepository;
import capstone.viewIt.common.exception.CustomException;
import capstone.viewIt.common.exception.ErrorCode;
import capstone.viewIt.common.response.CustomResponse;
import capstone.viewIt.expect.domain.ExpectedQuestion;
import capstone.viewIt.expect.repository.ExpectedQuestionRepository;
import capstone.viewIt.member.domain.Member;
import capstone.viewIt.member.repository.MemberRepository;
import capstone.viewIt.resume.domain.Resume;
import capstone.viewIt.resume.dto.ResumeRequestDto;
import capstone.viewIt.resume.repository.ResumeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final MemberRepository memberRepository;
    private final CategoryAndTitleRepository categoryAndTitleRepository;
    private final ExpectedQuestionRepository expectedQuestionRepository;

    @Transactional
    public CustomResponse<List<String>> submitQuestions(ResumeRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        CategoryAndTitle categoryAndTitle = categoryAndTitleRepository
                .findFirstByMemberOrderByCreatedAtDesc(member)
                .orElseThrow(() -> new CustomException(ErrorCode.TITLE_OR_CATEGORY_REQUIRED));

        List<Resume> resumes = requestDto.Resume().stream()
                .map(questionDto -> {
                    Resume resume = Resume.of(
                            questionDto.question(),
                            questionDto.answer(),
                            categoryAndTitle
                    );
                    categoryAndTitle.addQuestion(resume);
                    return resume;
                })
                .collect(Collectors.toList());

        if (resumes.size() < 3 || resumes.size() > 5) {
            throw new CustomException(ErrorCode.QUESTION_ANSWER_LENGTH_REQUIRED);
        }

        for(int i = 0; i< resumes.size(); i++) {
            if(resumes.get(i).getResumeQuestion().isEmpty() || resumes.get(i).getResumeAnswer().isEmpty()) {
                throw new CustomException(ErrorCode.BAD_REQUEST);
            }
        }

        List<Resume> savedResumes = resumeRepository.saveAll(resumes);

        List<String> resumeItems = savedResumes.stream()
                .map(Resume::getResumeAnswer)
                .toList();

        String jobtype = savedResumes.get(0).getCategoryAndTitle().getCategoryName();

        String aiServerUrl = "http://13.124.185.106/generate-questions"; // AI 서버의 URL
        // 전송할 데이터 구성
        Map<String, Object> payload = new HashMap<>();
        payload.put("resume_items", resumeItems);
        payload.put("job_type", jobtype);
        // POST 요청을 통해 AI 서버에 데이터 전송
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.postForEntity(aiServerUrl, payload, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            // AI 서버의 응답에서 예상 질문을 추출
            List<String> expectedQuestions = parseExpectedQuestionsFromResponse(response.getBody());

            // 예상 질문을 데이터베이스에 저장
            for (String question : expectedQuestions) {
                ExpectedQuestion expectedQuestion = ExpectedQuestion.of(question, savedResumes.get(0)); // 첫 번째 이력서와 연결
                expectedQuestionRepository.save(expectedQuestion);
            }

            // CustomResponse로 반환할 내용 구성
            return new CustomResponse<>(HttpStatus.OK.value(), "Success", expectedQuestions);
        } else {
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
    }

    private List<String> parseExpectedQuestionsFromResponse(String responseBody) {
        List<String> expectedQuestions = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode questionsNode = jsonNode.get("questions");

            if (questionsNode != null && questionsNode.isArray()) {
                for (JsonNode questionNode : questionsNode) {
                    expectedQuestions.add(questionNode.asText());
                }
            }
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
        }

        return expectedQuestions;
    }
}