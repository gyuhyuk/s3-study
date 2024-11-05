package capstone.viewIt.resume.controller;

import capstone.viewIt.common.response.CustomResponse;
import capstone.viewIt.member.controller.helper.TokenInformation;
import capstone.viewIt.resume.dto.ResumeRequestDto;
import capstone.viewIt.resume.dto.ResumeResponseFromAIDto;
import capstone.viewIt.resume.dto.ResumeResponseToAIDto;
import capstone.viewIt.resume.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ResumeController {
    private final ResumeService resumeService;
    @PostMapping("/question")
    public CustomResponse<List<String>> submitQuestion(@RequestBody @Valid ResumeRequestDto requestDto, @TokenInformation Long memberId) {
        return resumeService.submitQuestions(requestDto, memberId);
    }

}
