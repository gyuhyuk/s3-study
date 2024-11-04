package capstone.viewIt.member.domain;

import capstone.viewIt.categoryAndTitle.domain.CategoryAndTitle;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) private String memberId;

    private String password;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private final Set<CategoryAndTitle> categoryAndTitles = new LinkedHashSet<>();

    @Builder
    public Member(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    public static Member of(String memberId, String password) {
        return new Member(memberId, password);
    }
    public void addCategoryAndTitle(CategoryAndTitle categoryAndTitle) {
        this.categoryAndTitles.add(categoryAndTitle);
    }
}