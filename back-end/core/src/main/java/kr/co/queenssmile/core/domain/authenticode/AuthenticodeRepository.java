package kr.co.queenssmile.core.domain.authenticode;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface AuthenticodeRepository extends
    PagingAndSortingRepository<Authenticode, String>,
    QuerydslPredicateExecutor<Authenticode> {

  boolean existsById(String id);

  @Modifying
  @Query("delete from Authenticode c where c.value LIKE CONCAT('%',:mobile,'%')")
  void deleteAllByMobile(@Param("mobile") String mobile);
}
