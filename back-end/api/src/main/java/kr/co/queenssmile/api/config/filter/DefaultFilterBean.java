package kr.co.queenssmile.api.config.filter;

import kr.co.queenssmile.core.config.property.ProcessEnv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 필터 기본 설정
 */
@Slf4j
public class DefaultFilterBean implements Filter {

  private ProcessEnv processEnv;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.processEnv = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean(ProcessEnv.class);
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

    if (servletRequest instanceof HttpServletRequest
        && servletResponse instanceof HttpServletResponse) {

      HttpServletRequest request = (HttpServletRequest) servletRequest;
      HttpServletResponse response = (HttpServletResponse) servletResponse;

      log.debug("> request:Method ::: {}", request.getMethod());
      log.debug("> request:RequestURL ::: {}", request.getRequestURL());
      log.debug("> request:RequestURI ::: {}", request.getRequestURI());

      /* Spring Data Rest 에서 href 의 https scheme 문제 해결 */
      final HttpServletRequestWrapper wrapped = new HttpServletRequestWrapper(request) {
        @Override
        public StringBuffer getRequestURL() {
          final StringBuffer originalUrl = ((HttpServletRequest) getRequest()).getRequestURL();
//                    final String updatedUrl = processEnv.isProduction() ? originalUrl.toString().replace("http://", "https://") : originalUrl.toString();
          final String updatedUrl = originalUrl.toString();
          return new StringBuffer(updatedUrl);
        }
      };

      /* 폰트 캐시 */
      if (request.getRequestURI().contains("assets/fonts")) {
        response.setHeader("Cache-Control", "public max-age=3600");
        response.setHeader("Pragma", "cache");
      }

      chain.doFilter(wrapped, response);
    } else {
      chain.doFilter(servletRequest, servletResponse);
    }
  }

  @Override
  public void destroy() {

  }
}
