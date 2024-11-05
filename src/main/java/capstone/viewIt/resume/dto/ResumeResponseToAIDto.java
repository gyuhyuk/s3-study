package capstone.viewIt.resume.dto;

import java.util.List;

public record ResumeResponseToAIDto(List<String> resume_items, String job_type) {
}
