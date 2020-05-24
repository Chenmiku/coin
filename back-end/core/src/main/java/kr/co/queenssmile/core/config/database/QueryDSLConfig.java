package kr.co.queenssmile.core.config.database;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * QueryDSL JPAQueryFactory Bean 등록
 * <p>
 * MANUAL - https://www.baeldung.com/intro-to-querydsl -> 4.Querying with Querydsl(4.1.) 참고
 * MANUAL - http://www.querydsl.com/static/querydsl/latest/reference/html/ch02.html#jpa_integration
 * http://www.querydsl.com/static/querydsl/4.1.3/reference/html_single/
 */
@Slf4j
@Configuration
public class QueryDSLConfig {

  @Autowired
  private EntityManager entityManager;

  @org.springframework.context.annotation.Bean
  public JPAQueryFactory getJPAQueryFactory() {
    return new JPAQueryFactory(() -> entityManager);
  }
}
