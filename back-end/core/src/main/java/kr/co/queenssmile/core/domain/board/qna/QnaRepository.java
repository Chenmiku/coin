package kr.co.queenssmile.core.domain.board.qna;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QnaRepository extends
    PagingAndSortingRepository<Qna, Long>,
    QuerydslPredicateExecutor<Qna> {

  @Query("from QuestionsAndAnswers q where q.id = ?1 and q.relativeUser.email = ?2")
  Qna getByUser(Long id, String email);
}
