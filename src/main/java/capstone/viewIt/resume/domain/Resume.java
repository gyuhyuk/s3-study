package capstone.viewIt.resume.domain;

import capstone.viewIt.categoryAndTitle.domain.CategoryAndTitle;
import capstone.viewIt.common.entity.BaseEntity;
import capstone.viewIt.expect.domain.ExpectedQuestion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Resume extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false) private String resumeQuestion;
    @Column(length = 2000, nullable = false) private String resumeAnswer;

    @Setter
    @ManyToOne
    @JoinColumn(name = "category_and_title_id", nullable = false)
    private CategoryAndTitle categoryAndTitle;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpectedQuestion> expectedQuestions = new ArrayList<>();

    protected Resume() {}

    private Resume(String resumeQuestion, String resumeAnswer, CategoryAndTitle categoryAndTitle) {
        this.resumeQuestion = resumeQuestion;
        this.resumeAnswer = resumeAnswer;
        this.categoryAndTitle = categoryAndTitle;
    }

    public static Resume of(String resumeQuestion, String resumeAnswer, CategoryAndTitle categoryAndTitle) {
        return new Resume(resumeQuestion, resumeAnswer, categoryAndTitle);
    }
}
