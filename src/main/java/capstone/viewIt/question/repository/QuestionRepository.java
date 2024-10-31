package capstone.viewIt.question.repository;

import capstone.viewIt.categoryAndTitle.domain.CategoryAndTitle;
import capstone.viewIt.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
