package capstone.viewIt.question.controller;

import capstone.viewIt.categoryAndTitle.dto.CategoryAndTitleRequestDto;
import capstone.viewIt.common.response.CustomResponse;
import capstone.viewIt.member.controller.helper.TokenInformation;
import capstone.viewIt.question.dto.ResumeRequestDto;
import capstone.viewIt.question.dto.ResumeResponseToAIDto;
import capstone.viewIt.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/question")
    public ResumeResponseToAIDto submitQuestion(@RequestBody @Valid ResumeRequestDto resumeRequestDto, @TokenInformation Long memberId) {
        return questionService.submitQuestions(resumeRequestDto, memberId);
    }
//    @PostMapping("/question")
//    public CustomResponse<List> submitQuestion(@RequestBody @Valid ResumeRequestDto resumeRequestDto, @TokenInformation Long memberId) {
//        return new CustomResponse<>(
//                HttpStatus.OK.value(), "자기소개서 질문이 정상적으로 저장되었습니다.", questionService.submitQuestions(resumeRequestDto, memberId)
//        );
//    }
}
