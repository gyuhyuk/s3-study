package capstone.viewIt.expect.repository;

import capstone.viewIt.expect.domain.ExpectedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpectedQuestionRepository extends JpaRepository<ExpectedQuestion, Long> {
}
