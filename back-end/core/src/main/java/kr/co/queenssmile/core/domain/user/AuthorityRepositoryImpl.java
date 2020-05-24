package kr.co.queenssmile.core.domain.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorityRepositoryImpl implements AuthorityRepositoryCustom {

  @Autowired
  private JPAQueryFactory jpaQueryFactory;

  @Override
  public java.util.List<Authority> listByRoles(Authority.Role... roles) {
    QAuthority qAuthority = QAuthority.authority;

    return jpaQueryFactory.selectFrom(qAuthority)
        .where(qAuthority.role.in(roles))
        .fetch();
  }
}
