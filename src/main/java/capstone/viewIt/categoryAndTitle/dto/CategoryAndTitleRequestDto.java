package capstone.viewIt.categoryAndTitle.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryAndTitleRequestDto {
    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;
    private String categoryName;
}
