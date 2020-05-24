package kr.co.queenssmile.core.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditorConfig {

  @Bean
  public AuditorAware<Long> auditorProvider() {
    return new SpringSecurityAuditorAware();
  }

  // 하나의 인스턴스(SingleTon)를 가지고 공유하기 위해 bean 으로 설정 함.
  @Bean
  public SecurityUtils securityUtils() {
    return new SecurityUtils();
  }
}
