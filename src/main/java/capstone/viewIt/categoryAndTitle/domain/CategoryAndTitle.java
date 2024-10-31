package capstone.viewIt.categoryAndTitle.domain;

import capstone.viewIt.common.entity.BaseEntity;
import capstone.viewIt.member.domain.Member;
import capstone.viewIt.question.domain.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class CategoryAndTitle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false)
    private String categoryName;

    @Setter
    @ManyToOne(optional = false)
    private Member member;

    // Question과의 1:N 매핑 설정
    @OneToMany(mappedBy = "categoryAndTitle", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    protected CategoryAndTitle() {}

    private CategoryAndTitle(String title, String categoryName, Member member) {
        this.title = title;
        this.categoryName = categoryName;
        this.member = member;
    }

    public static CategoryAndTitle of(String title, String categoryName, Member member) {
        return new CategoryAndTitle(title, categoryName, member);
    }

    public void addQuestion(Question question) {
        questions.add(question);
        question.setCategoryAndTitle(this);
    }
}
