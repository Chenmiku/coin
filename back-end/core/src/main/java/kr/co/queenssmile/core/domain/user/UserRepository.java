package kr.co.queenssmile.core.domain.user;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends
    PagingAndSortingRepository<User, Long>,
    QuerydslPredicateExecutor<User>,
    UserRepositoryCustom {

  User findByEmail(String email);

  List<User> findByMobileAndUserDetailsMetaEnabledTrue(String mobile);
  List<User> findByMobileAndFullNameAndEmailAndUserDetailsMetaEnabledTrue(String mobile,String fullName,String email);

  boolean existsByEmail(String email);
  boolean existsByMobile(String mobile);

  User findBySocialIdFacebookId(String facebookId);
  User findBySocialIdKakaoTalkId(String kakaoTalkId);
  User findBySocialIdNaverId(String naverId);
}