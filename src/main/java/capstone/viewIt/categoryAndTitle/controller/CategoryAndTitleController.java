package capstone.viewIt.categoryAndTitle.controller;

import capstone.viewIt.categoryAndTitle.dto.CategoryAndTitleRequestDto;
import capstone.viewIt.categoryAndTitle.dto.CategoryAndTitleResponseDto;
import capstone.viewIt.categoryAndTitle.service.CategoryAndTitleService;
import capstone.viewIt.common.response.CustomResponse;
import capstone.viewIt.member.controller.helper.TokenInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryAndTitleController {

    private final CategoryAndTitleService categoryAndTitleService;

    @PostMapping("/categoryAndTitle")
    public CustomResponse<CategoryAndTitleResponseDto> postCategoryAndTitle(@RequestBody CategoryAndTitleRequestDto categoryAndTitleRequestDto, @TokenInformation Long memberId) {
        return new CustomResponse<>(
                HttpStatus.OK.value(), "카테고리와 제목이 정상적으로 저장되었습니다.", categoryAndTitleService.saveCategoryAndTitle(categoryAndTitleRequestDto, memberId)
        );
    }
}
