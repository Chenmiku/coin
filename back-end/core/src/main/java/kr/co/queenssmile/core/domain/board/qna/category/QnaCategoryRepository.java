package kr.co.queenssmile.core.domain.board.qna.category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QnaCategoryRepository extends
    PagingAndSortingRepository<QnaCategory, Long>,
    QuerydslPredicateExecutor<QnaCategory>,
    QnaCategoryRepositoryCustom {

  @Query("select max(c.orderAscending) from QnaCategory c")
  Long highestOrder();

  @Query("from QnaCategory c where c.orderAscending < ?1 order by c.orderAscending desc")
  java.util.List<QnaCategory> previous(long orderAscending, Pageable pageable);

  @Query("from QnaCategory c where c.orderAscending > ?1 order by c.orderAscending asc")
  java.util.List<QnaCategory> next(long orderAscending, Pageable pageable);
}
