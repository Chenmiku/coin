package kr.co.queenssmile.admin.config.language;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;


@Slf4j
@Component
@Configuration
public class MessageConfig {

  @Bean
  public org.springframework.context.MessageSource messageSource() {
    org.springframework.context.support.ResourceBundleMessageSource messageSource = new org.springframework.context.support.ResourceBundleMessageSource();
    messageSource.setBasename("messages");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setFallbackToSystemLocale(false);
    return messageSource;
  }

  @Bean(name = "localeResolver")
  public LocaleResolver acceptHeaderLocaleResolver() {
    return new AcceptHeaderCustomLocaleResolver();
  }
}
