package kr.co.queenssmile.core.domain.board.faq;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FaqRepository extends
    PagingAndSortingRepository<Faq, Long>,
    QuerydslPredicateExecutor<Faq>,
    FaqRepositoryCustom {

  @Query("select max(f.orderAscending) from Faq f")
  Long highestOrder();

  @Query("from Faq f where f.orderAscending < ?1 order by f.orderAscending desc")
  java.util.List<Faq> previous(long orderAscending, Pageable pageable);

  @Query("from Faq f where f.orderAscending > ?1 order by f.orderAscending asc")
  java.util.List<Faq> next(long orderAscending, Pageable pageable);

  @Query("from Faq f order by f.orderAscending desc")
  java.util.List<Faq> getAllOrder();

}
