package kr.co.queenssmile.api.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.mobile.device.DeviceResolverRequestFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Spring boot 2.X 설정 변견 사항
 * * ControllerAdvice 에서 사용하려면 필터 또는 인터셉터에서 설정해야함.
 *
 */
@Slf4j
@Configuration
public class FilterConfig {

  /**
   * 기본 설정
   */
  @Bean
  public FilterRegistrationBean getFilterRegistrationBean() {
    org.springframework.boot.web.servlet.FilterRegistrationBean<DefaultFilterBean> bean = new org.springframework.boot.web.servlet.FilterRegistrationBean<>(new DefaultFilterBean());
    bean.addUrlPatterns("/*");
//        log.debug("releasePageFilter order number ::: " + bean.getOrder()); // default: 2147483647 (가장 낮은 우선 순위 값입니다.)
    return bean;
  }

  /**
   * CROS 필터
   */
  @Bean
  public FilterRegistrationBean customCorsFilter() {

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin(CorsConfiguration.ALL);
    config.addAllowedHeader("*");
    config.addAllowedMethod(HttpMethod.OPTIONS);
    config.addAllowedMethod(HttpMethod.GET);
    config.addAllowedMethod(HttpMethod.POST);
    config.addAllowedMethod(HttpMethod.PUT);
    config.addAllowedMethod(HttpMethod.PATCH);
    config.addAllowedMethod(HttpMethod.DELETE);
    config.setMaxAge(0L);

    source.registerCorsConfiguration("/api/**", config);

    final FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 필터들중 가장먼저 필터되게 설정됨. (int의 최소값) 다른필터가 먼저 적용이 되어야 하면 숫자를 좀 더 높게 설정하면 됨.
    return bean;
  }

  @Bean
  public FilterRegistrationBean deviceResolverRequestFilter() {
    return new FilterRegistrationBean<>(new DeviceResolverRequestFilter());
  }

//  @Bean
//  public FilterRegistrationBean xssEscapeServletFilter() {
//    FilterRegistrationBean<XssEscapeServletFilter> registrationBean = new FilterRegistrationBean<>(new XssEscapeServletFilter());
//    registrationBean.setOrder(1);
//    registrationBean.addUrlPatterns("/api/*");
//    return registrationBean;
//  }
}
