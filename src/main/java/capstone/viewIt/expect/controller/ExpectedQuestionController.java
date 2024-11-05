package capstone.viewIt.expect.controller;

import capstone.viewIt.member.controller.helper.TokenInformation;
import capstone.viewIt.resume.dto.ResumeRequestDto;
import capstone.viewIt.resume.dto.ResumeResponseToAIDto;
import capstone.viewIt.resume.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExpectedQuestionController {
}
