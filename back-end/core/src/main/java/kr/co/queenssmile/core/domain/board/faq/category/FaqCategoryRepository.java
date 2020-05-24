package kr.co.queenssmile.core.domain.board.faq.category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FaqCategoryRepository extends
    PagingAndSortingRepository<FaqCategory, Long>,
    QuerydslPredicateExecutor<FaqCategory>,
    FaqCategoryRepositoryCustom {

  @Query("select max(c.orderAscending) from FaqCategory c")
  Long highestOrder();

  @Query("from FaqCategory c where c.orderAscending < ?1 order by c.orderAscending desc")
  java.util.List<FaqCategory> previous(long orderAscending, Pageable pageable);

  @Query("from FaqCategory c where c.orderAscending > ?1 order by c.orderAscending asc")
  java.util.List<FaqCategory> next(long orderAscending, Pageable pageable);

  @Query("from FaqCategory c order by c.orderAscending desc")
  java.util.List<FaqCategory> getAllOrder();
}

