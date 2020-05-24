package kr.co.queenssmile.admin.config.mobile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
/* boot2.1 migration start */

/**
 * Controller 에서 사용하려면 ArgumentResolver 에 deviceHandlerMethodArgumentResolver 추가해줘야 함.
 */
@Slf4j
@Configuration
public class MobileArgumentResolverConfig implements WebMvcConfigurer {

  @Bean
  public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
    return new DeviceHandlerMethodArgumentResolver();
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(deviceHandlerMethodArgumentResolver());
  }
}
/* boot2.1 migration end */