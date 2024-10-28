package capstone.viewIt.categoryAndTitle.repository;

import capstone.viewIt.categoryAndTitle.domain.CategoryAndTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryAndTitleRepository extends JpaRepository<CategoryAndTitle, Long> {

}
