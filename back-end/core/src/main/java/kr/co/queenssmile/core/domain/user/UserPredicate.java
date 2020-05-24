package kr.co.queenssmile.core.domain.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@NoArgsConstructor(staticName = "getInstance")
public class UserPredicate {

  private static final QUser Q_USER = QUser.user;

  private BooleanBuilder builder = new BooleanBuilder();

  public Predicate values() {
    return builder.getValue() == null ? builder.and(Q_USER.id.isNotNull()) : builder.getValue();
  }

  public UserPredicate startDate(final LocalDateTime startDate) {

    if (startDate != null) {
      builder.and(Q_USER.createdDate.goe(startDate));
    }
    return this;
  }

  public UserPredicate endDate(final LocalDateTime endDate) {

    if (endDate != null) {
      builder.and(Q_USER.createdDate.loe(endDate));
    }
    return this;
  }

  // TODO https://aartkorea0.atlassian.net/browse/CPGF-15 - 암호화된 기업정보 처리
  public UserPredicate search(String value) {

    if (!StringUtils.isEmpty(value)) {
      value = value.trim();

      builder.and(Q_USER.email.eq(value)
          .or(Q_USER.mobile.eq(value))
          .or(Q_USER.fullName.eq(value))
      );
    }
    return this;
  }

  public UserPredicate role(final Authority.Role role) {

    if (role != null) {
      builder.and(Q_USER.authorities.any().role.eq(role));
    }
    return this;
  }

  public UserPredicate roles(final Authority.Role... role) {

    if (role != null) {
      builder.and(Q_USER.authorities.any().role.in(role));
    }
    return this;
  }

  public UserPredicate roles(List<Authority.Role> roles) {

    if (roles != null) {
      builder.and(Q_USER.authorities.any().role.in(roles));
    }
    return this;
  }

  public UserPredicate notLeaved() {

    builder.and(Q_USER.leaveMeta.leave.eq(false));
    return this;
  }
}
