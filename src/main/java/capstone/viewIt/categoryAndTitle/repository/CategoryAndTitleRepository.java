package capstone.viewIt.categoryAndTitle.repository;

import capstone.viewIt.categoryAndTitle.domain.CategoryAndTitle;
import capstone.viewIt.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryAndTitleRepository extends JpaRepository<CategoryAndTitle, Long> {
    Optional<CategoryAndTitle> findFirstByMemberOrderByCreatedAtDesc(Member member);
}
