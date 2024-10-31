package capstone.viewIt.question.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ResumeRequestDto(
        @NotEmpty(message = "최소 세 개 이상의 질문이 필요합니다.")
        List<QuestionRequestDto> questions
) {}
