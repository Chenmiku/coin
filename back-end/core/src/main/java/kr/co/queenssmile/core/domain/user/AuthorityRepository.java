package kr.co.queenssmile.core.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AuthorityRepository extends
    JpaRepository<Authority, Long>,
    QuerydslPredicateExecutor<Authority>,
    AuthorityRepositoryCustom {

  Authority findByRole(Authority.Role role);

}
