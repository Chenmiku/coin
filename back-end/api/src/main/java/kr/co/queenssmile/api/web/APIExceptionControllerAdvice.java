package kr.co.queenssmile.api.web;

import kr.co.queenssmile.api.model.error.ErrorBody;
import kr.co.queenssmile.core.config.exception.*;
import kr.co.queenssmile.core.config.exception.account.AlreadyVerificationException;
import kr.co.queenssmile.core.config.exception.shop.AlreadyBuyingException;
import kr.co.queenssmile.core.config.exception.shop.PaymentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice(annotations = {RestController.class, ResponseBody.class})
public class APIExceptionControllerAdvice {

//  @Autowired
//  private org.springframework.context.MessageSource messageSource;
//
//  @Autowired
//  private LocaleResolver localeResolver;

  //== 204
  @ExceptionHandler({NoContentException.class})
  public ResponseEntity<?> noContent(HttpServletRequest request, Throwable e) {
    if (log.isDebugEnabled())
      e.printStackTrace();
    return responseEntity(HttpStatus.NO_CONTENT, request, e.getMessage());
  }

  //== 400
  @ExceptionHandler({BadRequestException.class, HttpMessageNotReadableException.class, NullPointerException.class})
  public ResponseEntity<?> badRequest(HttpServletRequest request, Throwable e) {
//        log.info("BadRequestException");
    if (log.isDebugEnabled())
      e.printStackTrace();
    return responseEntity(HttpStatus.BAD_REQUEST, request, e.getMessage());
  }

  // == 401
  @ExceptionHandler(UnAuthorizedException.class)
  public ResponseEntity<?> handleUnAuthorizedException(HttpServletRequest request, Throwable e) {
//        log.info("UnAuthorizedException");
    if (log.isDebugEnabled())
      e.printStackTrace();
    return responseEntity(HttpStatus.UNAUTHORIZED, request, e.getMessage());
  }

  //== 403
  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<?> forbidden(HttpServletRequest request, Throwable e) {
//        log.info("ForbiddenException");
    if (log.isDebugEnabled())
      e.printStackTrace();
    return responseEntity(HttpStatus.FORBIDDEN, request, e.getMessage());
  }

  //== 404
  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<?> notFound(HttpServletRequest request, Throwable e) {
//        log.info("NotFoundException");
    if (log.isDebugEnabled())
      e.printStackTrace();
    return responseEntity(HttpStatus.NOT_FOUND, request, e.getMessage());
  }

  //== 405
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<?> methodNotSupport(HttpServletRequest request, Throwable e) {
//        log.info("HttpRequestMethodNotSupportedException");
    if (log.isDebugEnabled())
      e.printStackTrace();
    return responseEntity(HttpStatus.METHOD_NOT_ALLOWED, request, "Method Not Allowed");
  }

  // == 409
  @ExceptionHandler({ConflictException.class, AlreadyBuyingException.class, AlreadyVerificationException.class})
  public ResponseEntity<?> handleAlreadyExistMobileException(HttpServletRequest request, Throwable e) {
    if (log.isDebugEnabled())
      e.printStackTrace();
//        log.info("ConflictException");
    return responseEntity(HttpStatus.CONFLICT, request, e.getLocalizedMessage());
  }

  // == 412
  @ExceptionHandler(ETagException.class)
  public ResponseEntity<?> handleETagException(HttpServletRequest request, Throwable e) {
    if (log.isDebugEnabled())
      e.printStackTrace();
//        log.info("ETagException");
    return responseEntity(HttpStatus.PRECONDITION_FAILED, request, e.getMessage());
  }

  // == 413
  @ExceptionHandler(PayloadTooLargeException.class)
  public ResponseEntity<?> handlePayloadTooLargeException(HttpServletRequest request, Throwable e) {
    if (log.isDebugEnabled())
      e.printStackTrace();
//        log.info("PayloadTooLargeException");
    return responseEntity(HttpStatus.PAYLOAD_TOO_LARGE, request, e.getMessage());
  }

  // == 415
  @ExceptionHandler(UnsupportedMediaTypeException.class)
  public ResponseEntity<?> handleUnsupportedMediaTypeException(HttpServletRequest request, Throwable e) {
    if (log.isDebugEnabled())
      e.printStackTrace();
//        log.info("UnsupportedMediaTypeException");
    return responseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE, request, e.getMessage());
  }

  //== 500
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> runtime(HttpServletRequest request, Throwable e) {
//        log.info("RuntimeException");
    log.error("RuntimeException", e);
    e.printStackTrace();
    return this.responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, request, "INTERNAL_SERVER_ERROR");
  }


  // == 500 - 결제 에러
  @ExceptionHandler(PaymentException.class)
  public ResponseEntity<?> handlePaymentException(HttpServletRequest request, Throwable e) {
//        log.info("PaymentException");
    log.error("PaymentException", e);
    e.printStackTrace();
    return responseEntity(HttpStatus.INTERNAL_SERVER_ERROR, request, e.getMessage());
  }

  //== Error Value 응답
  private ResponseEntity<?> responseEntity(HttpStatus status, HttpServletRequest request, String code) {
//    log.info("responseEntity({}, {}, {})", status, request, code);

    String localeMessage = "";

//    if (code != null && code.startsWith("error.")) {
//      messageSource.getMessage(code, null, "no surch", localeResolver.resolveLocale(request)); //현재 설정된 위치 기준의 메시지 요청
//    } else {
//    }
      localeMessage = code;
//        ;
//        ErrorTypeVO result = ErrorTypeVO.of(status.value(), localeMessage);
    ErrorBody errorBody = new ErrorBody();
    errorBody.setCode("" + status.value());
    errorBody.setMessage(localeMessage);
    return new ResponseEntity<>(errorBody, new HttpHeaders(), status);
  }
}
