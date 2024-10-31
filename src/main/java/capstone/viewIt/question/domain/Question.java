package capstone.viewIt.question.domain;

import capstone.viewIt.categoryAndTitle.domain.CategoryAndTitle;
import capstone.viewIt.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false) private String resumeQuestion;
    @Column(length = 2000, nullable = false) private String resumeAnswer;

    @Setter
    @ManyToOne
    @JoinColumn(name = "category_and_title_id", nullable = false)
    private CategoryAndTitle categoryAndTitle;

    protected Question() {}

    private Question(String resumeQuestion, String resumeAnswer, CategoryAndTitle categoryAndTitle) {
        this.resumeQuestion = resumeQuestion;
        this.resumeAnswer = resumeAnswer;
        this.categoryAndTitle = categoryAndTitle;
    }

    public static Question of(String resumeQuestion, String resumeAnswer, CategoryAndTitle categoryAndTitle) {
        return new Question(resumeQuestion, resumeAnswer, categoryAndTitle);
    }
}
