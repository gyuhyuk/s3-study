package capstone.viewIt.expect.domain;

import capstone.viewIt.common.entity.BaseEntity;
import capstone.viewIt.resume.domain.Resume;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class ExpectedQuestion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String expectedQuestion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "resume_id")
    @Setter private Resume resume;

    protected ExpectedQuestion() {}

    private ExpectedQuestion(String expectedQuestion, Resume resume) {
        this.expectedQuestion = expectedQuestion;
        this.resume = resume;
    }

    public static ExpectedQuestion of(String expectedQuestion, Resume resume) {
        return new ExpectedQuestion(expectedQuestion, resume);
    }
}
