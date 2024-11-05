package capstone.viewIt.resume.dto;


public record ResumeResponseDto(
        String question,
        String answer
) {
    public static ResumeResponseDto of(String question, String answer) {
        return new ResumeResponseDto(question, answer);
    }
}