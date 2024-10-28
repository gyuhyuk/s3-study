package capstone.viewIt.categoryAndTitle.domain;

import capstone.viewIt.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class CategoryAndTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter private String title;
    @Setter private String categoryName;

    @OneToOne(mappedBy = "categoryAndTitle")
    private Member member;

    protected CategoryAndTitle() {}

    private CategoryAndTitle(String title, String categoryName, Member member) {
        this.title = title;
        this.categoryName = categoryName;
        this.member = member;
    }

    public static CategoryAndTitle of(String title, String categoryName, Member member) {
        return new CategoryAndTitle(title, categoryName, member);
    }
}

