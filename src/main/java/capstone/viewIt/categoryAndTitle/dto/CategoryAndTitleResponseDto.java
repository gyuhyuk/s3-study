package capstone.viewIt.categoryAndTitle.dto;

public record CategoryAndTitleResponseDto(String title, String categoryName) {
    public static CategoryAndTitleResponseDto of(String title, String categoryName) {
        return new CategoryAndTitleResponseDto(title, categoryName);
    }
}
