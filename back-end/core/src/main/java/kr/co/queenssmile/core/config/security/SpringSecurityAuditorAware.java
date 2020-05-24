package kr.co.queenssmile.core.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Slf4j
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

  @Override
  public Optional<Long> getCurrentAuditor() {

    try {
      return Optional.ofNullable(SecurityUtils.getCurrentUserId());
    } catch (Exception ex) {
      log.error(ex.getMessage());
    }
    return Optional.empty();
  }
}