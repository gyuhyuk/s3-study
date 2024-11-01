package capstone.viewIt.question.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

public record QuestionRequestDto(
        @NotBlank(message = "질문은 필수 입력값입니다.")
        String question,
        @NotBlank(message = "답변은 필수 입력값입니다.")
        String answer
) {}