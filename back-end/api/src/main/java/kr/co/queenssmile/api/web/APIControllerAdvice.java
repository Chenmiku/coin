//package kr.co.queenssmile.api.web.web;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJacksonValue;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.regex.Pattern;
//
//@Slf4j
//@ControllerAdvice
//public class APIControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {
//
//  public static final Pattern CALLBACK_PARAM_PATTERN = Pattern.compile("[0-9A-Za-z_\\.]*");
//  public static final MediaType JSONP_MEDIA_TYPE = new MediaType("application", "javascript");
//  public static final String[] JSONP_QUERY_PARAMETER_NAMES = {"jsonp", "callback"};
//
//  @Override
//  protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
//    HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
//
//    for (String jsonpQueryParameterName : JSONP_QUERY_PARAMETER_NAMES) {
//
//      log.debug("jsonpQueryParameterName ::: {}", jsonpQueryParameterName);
//      String jsonpFunctionName = servletRequest.getParameter(jsonpQueryParameterName);
//      if (StringUtils.isNotBlank(jsonpFunctionName) == true) {
//        if (CALLBACK_PARAM_PATTERN.matcher(jsonpFunctionName).matches() == true) { // JSONP용 MediaType 설정
//
//          log.debug("# JSONP");
//
//          response.getHeaders().setContentType(JSONP_MEDIA_TYPE);
//          // Jackson에 의해서 JSONP로 처리할 수 있는 JsonpWrappingObject 인스턴스 생성
//          JsonpWrappingObject jsonpWrappingObject = JsonpWrappingObject.builder()
//              .jsonpFunctionName(jsonpFunctionName).value(bodyContainer.getValue())
//              .build(); // MappingJacksonValue에 JsonpWrappingObject 값 설정
//          bodyContainer.setValue(jsonpWrappingObject);
//          break;
//        }
//      }
//    }
//  }
//}
