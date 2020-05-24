package kr.co.queenssmile.core.domain.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserRepositoryImpl implements UserRepositoryCustom {

  @Autowired
  private JPAQueryFactory jpaQueryFactory;

//  @Override
//  public java.util.List<UserInfoResBody> findByUserInfo(String query) {
//    QUser qUser = QUser.user;
//    java.util.List<User> list = jpaQueryFactory.selectFrom(qUser)
//        .where(qUser.fullName.eq(query)
//          .or(qUser.mobile.eq(query))
//          .or(qUser.email.eq(query))
//        )
//        .orderBy(qUser.fullName.asc()).fetch();
//
//    return list.stream().map(user -> {
//      UserInfoResBody ui = new UserInfoResBody();
//      ui.setId(user.getId());
//      ui.setName(user.getFullName());
//      ui.setEmail(user.getEmail());
//      ui.setMobile(ui.getMobile());
//      return ui;
//    }).collect(Collectors.toList());
//  }
}
