package kr.co.queenssmile.core.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;

@Slf4j
public class HttpUtils {

  public static URI createUriForParameter(String url, org.springframework.util.MultiValueMap<String, String> params) {
    return UriComponentsBuilder
        .fromUriString(url)
        .queryParams(params)
        .build()
        .encode()
        .toUri();
  }

  public static void debug(HttpServletRequest request) {

    if (!request.getRequestURI().startsWith("/assets")
        && !request.getRequestURI().startsWith("/plugin")
        && !request.getRequestURI().startsWith("/favicon.ico")
        && !request.getRequestURI().startsWith("/dist")) {

      log.info("url: " + request.getRequestURL() + ", method: " + request.getMethod());

      log.info("######### request  ##########");  // http://www.domain.com:8080/premium?param=test
      log.info("scheme ::: " + request.getScheme());              // http
      log.info("server name ::: " + request.getServerName());     // www.domain.com
      log.info("server port ::: " + request.getServerPort());     // 8080
      log.info("request uri ::: " + request.getRequestURI());     // /premium
      log.info("query string ::: " + request.getQueryString());   // param=test
      log.info("request url ::: " + request.getRequestURL());     // http://www.domain.com:8080/premium
      log.info("servlet path ::: " + request.getServletPath());   // /premium
      log.info("path info ::: " + request.getPathInfo());         // null
      log.info("auth type ::: " + request.getAuthType());         // null
      log.info("locale ::: " + request.getLocale());              // ko_KR
      log.info("character encoding ::: " + request.getCharacterEncoding());       // UTF-8
      log.info("method ::: " + request.getMethod());              // GET
      log.info("protocol ::: " + request.getProtocol());          // HTTP/1.1
      log.info("local addr ::: " + request.getLocalAddr());       // 127.0.0.1    (로컬 ip주소)
      log.info("local name ::: " + request.getLocalName());       // localhost    (로컬 호스트네임)
      log.info("remote host ::: " + request.getRemoteHost());     // 127.0.0.1    (접속자 ip주소)
      log.info("remote user ::: " + request.getRemoteUser());     // null         (접속자 로그인 아이디)
      log.info("remote addr ::: " + request.getRemoteAddr());     // 127.0.0.1    (접속자 ip주소)
      log.info("remote port ::: " + request.getRemotePort());     // 50517        (접속자 port번호)

      Enumeration<String> headerNames = request.getHeaderNames();

      while (headerNames.hasMoreElements()) {
        String name = headerNames.nextElement();
        String value = request.getHeader(name);

        log.info(String.format("headers { %s : %s }", name, value));
      }

      Enumeration<String> enumeration = request.getParameterNames();

      while (enumeration.hasMoreElements()) {
        String name = enumeration.nextElement();
        String value = request.getParameter(name);

        log.info(String.format("params { %s : %s }", name, value));
      }

      URL url = null;
      try {
        url = new URL(request.getRequestURL().toString());
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }

      if (url == null) {
        log.error("######### URL is null");
      } else {
        log.info("######### URL ##########");                   // http://www.domain.com:8080/premium?param=test
        log.info("protocol ::: " + url.getProtocol());          // http
        log.info("host ::: " + url.getHost());                  // www.domain.com
        log.info("port ::: " + url.getPort());                  // 8080
        log.info("path ::: " + url.getPath());                  // /premium
        log.info("query ::: " + url.getQuery());                // null
        log.info("ref ::: " + url.getRef());                    // null
        log.info("authority ::: " + url.getAuthority());        // www.domain.com:8080
        log.info("file ::: " + url.getFile());                  // /premium
        log.info("default port ::: " + url.getDefaultPort());   // 80
        log.info("user info ::: " + url.getUserInfo());         // null
      }
    }
  }

  public static String convertLineSeparatorToBrTag(String content) {
    if (content == null) {
      return null;
    }
    return content
        .replaceAll("\r\n", "<br/>")
        .replaceAll(System.getProperty("line.separator"), "<br/>")
        .replaceAll("\n", "<br/>");
  }
}
