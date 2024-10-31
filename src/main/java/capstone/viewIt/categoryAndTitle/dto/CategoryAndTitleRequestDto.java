package capstone.viewIt.categoryAndTitle.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryAndTitleRequestDto(
        @NotBlank(message = "제목은 필수 입력값입니다.")
        String title,
        String categoryName
) {}