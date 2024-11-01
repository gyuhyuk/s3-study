package capstone.viewIt.question.service;

import capstone.viewIt.categoryAndTitle.domain.CategoryAndTitle;
import capstone.viewIt.categoryAndTitle.repository.CategoryAndTitleRepository;
import capstone.viewIt.common.exception.CustomException;
import capstone.viewIt.common.exception.ErrorCode;
import capstone.viewIt.member.domain.Member;
import capstone.viewIt.member.repository.MemberRepository;
import capstone.viewIt.question.domain.Question;
import capstone.viewIt.question.dto.ResumeRequestDto;
import capstone.viewIt.question.dto.ResumeResponseToAIDto;
import capstone.viewIt.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final CategoryAndTitleRepository categoryAndTitleRepository;

    //
    @Transactional
    public ResumeResponseToAIDto submitQuestions(ResumeRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        CategoryAndTitle categoryAndTitle = categoryAndTitleRepository
                .findFirstByMemberOrderByCreatedAtDesc(member)
                .orElseThrow(() -> new CustomException(ErrorCode.TITLE_OR_CATEGORY_REQUIRED));

        List<Question> questions = requestDto.questions().stream()
                .map(questionDto -> {
                    Question question = Question.of(
                            questionDto.question(),
                            questionDto.answer(),
                            categoryAndTitle
                    );
                    categoryAndTitle.addQuestion(question);
                    return question;
                })
                .collect(Collectors.toList());

        if (questions.size() < 3 || questions.size() > 5) {
            throw new CustomException(ErrorCode.QUESTION_ANSWER_LENGTH_REQUIRED);
        }

        for(int i=0; i<questions.size(); i++) {
            if(questions.get(i).getResumeQuestion().isEmpty() || questions.get(i).getResumeAnswer().isEmpty()) {
                throw new CustomException(ErrorCode.BAD_REQUEST);
            }
        }

        List<Question> savedQuestions = questionRepository.saveAll(questions);

//        return savedQuestions.stream()
//                .map(savedQuestion -> ResumeResponseToAIDto.of(
//                        savedQuestion.getResumeAnswer(),
//                        savedQuestion.getCategoryAndTitle().getCategoryName()
//                ))
//                .collect(Collectors.toList());
        List<String> resumeItems = savedQuestions.stream()
                .map(Question::getResumeAnswer)
                .toList();

        String jobtype = savedQuestions.get(0).getCategoryAndTitle().getCategoryName();
        return new ResumeResponseToAIDto(resumeItems, jobtype);
    }

//    @Transactional
//    public List<ResumeResponseDto> submitQuestions(ResumeRequestDto requestDto, Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
//
//        CategoryAndTitle categoryAndTitle = categoryAndTitleRepository
//                .findFirstByMemberOrderByCreatedAtDesc(member)
//                .orElseThrow(() -> new CustomException(ErrorCode.TITLE_OR_CATEGORY_REQUIRED));
//
//        List<Question> questions = requestDto.questions().stream()
//                .map(questionDto -> {
//                    Question question = Question.of(
//                            questionDto.question(),
//                            questionDto.answer(),
//                            categoryAndTitle
//                    );
//                    categoryAndTitle.addQuestion(question);
//                    return question;
//                })
//                .collect(Collectors.toList());
//
//        List<Question> savedQuestions = questionRepository.saveAll(questions);
//
//        return savedQuestions.stream()
//                .map(savedQuestion -> ResumeResponseDto.of(
//                        savedQuestion.getResumeQuestion(),
//                        savedQuestion.getResumeAnswer()
//                ))
//                .collect(Collectors.toList());
//    }
}