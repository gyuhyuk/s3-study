package capstone.viewIt.categoryAndTitle.service;

import capstone.viewIt.categoryAndTitle.domain.CategoryAndTitle;
import capstone.viewIt.categoryAndTitle.dto.CategoryAndTitleRequestDto;
import capstone.viewIt.categoryAndTitle.dto.CategoryAndTitleResponseDto;
import capstone.viewIt.categoryAndTitle.repository.CategoryAndTitleRepository;
import capstone.viewIt.common.exception.CustomException;
import capstone.viewIt.common.exception.ErrorCode;
import capstone.viewIt.common.response.CustomResponse;
import capstone.viewIt.member.domain.Member;
import capstone.viewIt.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryAndTitleService {
    private final CategoryAndTitleRepository categoryAndTitleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CategoryAndTitleResponseDto saveCategoryAndTitle(CategoryAndTitleRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        CategoryAndTitle categoryAndTitle = CategoryAndTitle.of(
                requestDto.title(),
                requestDto.categoryName(),
                member
        );

        if(requestDto.title().isEmpty() || requestDto.categoryName().isEmpty()) {
            throw new CustomException(ErrorCode.TITLE_OR_CATEGORY_REQUIRED);
        }

        member.addCategoryAndTitle(categoryAndTitle);
        CategoryAndTitle savedCategoryAndTitle = categoryAndTitleRepository.save(categoryAndTitle);

        return CategoryAndTitleResponseDto.of(
                        savedCategoryAndTitle.getTitle(),
                        savedCategoryAndTitle.getCategoryName()
        );
    }
}