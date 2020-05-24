package kr.co.queenssmile.api.service.user;

import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.config.exception.ResponseErrorCode;
import kr.co.queenssmile.core.config.property.ProcessEnv;
import kr.co.queenssmile.core.domain.authenticode.Authenticode;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.domain.user.UserRepository;
import kr.co.queenssmile.core.model.AuthentiCodeResBody;
import kr.co.queenssmile.core.model.reqbody.user.CertConfirmReqBody;
import kr.co.queenssmile.core.model.reqbody.user.CertReqBody;
import kr.co.queenssmile.core.service.authenticode.AuthenticodeService;
import kr.co.queenssmile.core.utils.DateUtils;
import kr.co.queenssmile.core.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class CertServiceImpl implements CertService {

  @Value("${spring.application.name}")
  private String appName;

  @Value("${app.api.host}")
  private String host;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticodeService authenticodeService;

//  @Autowired
//  private SMSService smsService;

//  @Autowired
//  private EmailService emailService;

  @Autowired
  private ProcessEnv processEnv;

  @Override
  public String cert(CertReqBody certReqBody, HttpServletRequest request) {

    CertReqBody.Mode mode = certReqBody.getMode();

    switch (mode) {
      case CERT_MOBILE:
        return this.certMobile(certReqBody);
      case CERT_EMAIL:
        return this.certEmail(certReqBody, request);
      case FIND_PASSWORD_EMAIL:
        return this.certFindPasswordEmail(certReqBody, request);
      case FIND_PASSWORD_MOBILE:
      case FIND_ACCOUNT_MOBILE:
        return this.certFindMobile(certReqBody);
    }
    return null;
  }

  private String certMobile(CertReqBody certReqBody) {

    final String mobile = certReqBody.getMobile();

    Authenticode authenticode = new Authenticode();
    Map<String, Object> result = new HashMap<>();
    result.put("mobile", mobile);
    authenticode.setValue(JsonUtils.toJson(result));
    authenticode.setExpireTime(LocalDateTime.now().plusSeconds(Authenticode.EXPIRE_TIME_MOBILE));
    String code = authenticodeService.tokenCode(authenticode);
    String dayTime = DateUtils.getFormatStrToLocalDateTime(DateUtils.getLocalDateTime(), DateUtils.FORMAT_DATE_TIME);

    //smsService.send(new SMS(mobile, String.format("[" + appName + " 본인확인] %s 인증번호를 입력해주세요.", code)));

    // 개발서버에서는 log 로 출력
    if (processEnv.isDevelopment()) {
      log.debug("### 인증번호 ###");
      log.debug("code ::: " + code);
      log.debug("mobile ::: " + mobile);
    }
    return code;
  }

  private String certEmail(CertReqBody certReqBody, HttpServletRequest request) {

    final String email = certReqBody.getEmail();
    User user = userRepository.findByEmail(email);

    if (user != null) {

      Authenticode authenticode = new Authenticode();
      Map<String, Object> result = new HashMap<>();
      result.put("email", email);
      authenticode.setValue(JsonUtils.toJson(result));
      authenticode.setExpireTime(LocalDateTime.now().plusSeconds(Authenticode.EXPIRE_TIME_EMAIL));
      String code = authenticodeService.tokenCode(authenticode);

      String url = host + "/cert/confirm/email";
      url += "?code=" + code;

      String subject = appName + " 이메일 인증";

      Map<String, Object> model = new HashMap<>();
      model.put("subject", subject);
      model.put("fullname", user.getFullName());
      model.put("url", url);
      model.put("nowDate", LocalDate.now());
      model.put("email", email);

//      emailService.send(email, subject, model, "email/cert-email.ftl");

      // 개발서버에서는 log 로 출력
      if (processEnv.isDevelopment()) {
        log.debug("code ::: " + code);
        log.debug("email ::: " + email);
      }
      return code;
    } else {
      throw new BadRequestException(ResponseErrorCode.NOT_EXIST_EMAIL.getMessage());
    }
  }

  private String certFindPasswordEmail(CertReqBody cert, HttpServletRequest request) {

    final String email = cert.getEmail();
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new BadRequestException(ResponseErrorCode.NOT_EXIST_EMAIL.getMessage());
    }

    if (!user.getVerification().isEmail()) {
      throw new BadRequestException("이메일 인증이 확인되지 않은 계정입니다.");
    }
    Authenticode authenticode = new Authenticode();
    Map<String, Object> result = new HashMap<>();
    result.put("email", email);
    authenticode.setValue(JsonUtils.toJson(result));
    authenticode.setExpireTime(LocalDateTime.now().plusSeconds(Authenticode.EXPIRE_TIME_EMAIL));
    String code = authenticodeService.tokenCode(authenticode);

    String url = host + "/account/reset-password/" + code;

    String subject = appName + " 새 비밀번호 설정";

    Map<String, Object> model = new HashMap<>();
    model.put("appName", appName);
    model.put("host", host);
    model.put("subject", subject);
    model.put("fullname", user.getFullName());
    model.put("url", url);

    log.debug("model ::: {}", model);

//    emailService.send(email, subject, model, "email/reset-password-email.ftl");

    // 개발서버에서는 log 로 출력
    if (processEnv.isDevelopment()) {
      log.debug("code ::: " + code);
      log.debug("email ::: " + email);
    }
    return code;

  }

  private String certFindMobile(CertReqBody certReqBody) {
    final String mobile = certReqBody.getMobile();

    Authenticode authenticode = new Authenticode();
    Map<String, Object> result = new HashMap<>();
    result.put("mobile", mobile);
    authenticode.setValue(JsonUtils.toJson(result));
    authenticode.setExpireTime(LocalDateTime.now().plusSeconds(Authenticode.EXPIRE_TIME_MOBILE));
    String code = authenticodeService.tokenCode(authenticode);
    String dayTime = DateUtils.getFormatStrToLocalDateTime(DateUtils.getLocalDateTime(), DateUtils.FORMAT_DATE_TIME);

//    smsService.send(new SMS(mobile, String.format("[" + appName + "] %s 인증번호를 입력해주세요.", code)));

    // 개발서버에서는 log 로 출력
    if (processEnv.isDevelopment()) {
      log.debug("code ::: " + code);
      log.debug("mobile ::: " + mobile);
    }
    return code;
  }

  @Override
  public AuthentiCodeResBody confirm(CertConfirmReqBody confirm) {

    CertReqBody.Mode mode = confirm.getMode();

    switch (mode) {
      case CERT_MOBILE:
      case FIND_PASSWORD_MOBILE:
      case FIND_ACCOUNT_MOBILE:
        return this.confirmMobile(confirm);
      case CERT_EMAIL:
      case FIND_PASSWORD_EMAIL:
        return this.confirmEmail(confirm);
    }
    return new AuthentiCodeResBody(Authenticode.RESULT_FAIL);
  }

  private AuthentiCodeResBody confirmMobile(CertConfirmReqBody confirm) {
    AuthentiCodeResBody result = authenticodeService.confirmByMobile(confirm.getCode(), confirm.getMobile());
    this.log(result.getResultCode());
    return result;
  }

  private AuthentiCodeResBody confirmEmail(CertConfirmReqBody confirm) {
    AuthentiCodeResBody result = authenticodeService.confirmByEmail(confirm.getCode());
    this.log(result.getResultCode());
    return result;
  }

  // Response Message
  public static String confirmResult(int result) {
    switch (result) {
      case Authenticode.RESULT_SUCCESS:
        return "성공";
      case Authenticode.RESULT_NOT_EXISTS:
        return "존재하지 않는 번호(이메일) 입니다.";
      case Authenticode.RESULT_EXPIRE:
        return "만료된 코드입니다.";
      case Authenticode.RESULT_FAIL:
      default:
        return "인증을 실패하였습니다.";
    }
  }

  // LOG
  private void log(int result) {
    switch (result) {
      case Authenticode.RESULT_SUCCESS:
        log.debug("성공");
        break;
      case Authenticode.RESULT_NOT_EXISTS:
        log.debug("존재하지 않음");
        break;
      case Authenticode.RESULT_EXPIRE:
        log.debug("만료");
        break;
      case Authenticode.RESULT_FAIL:
        log.debug("실패");
        break;
    }
  }

  @Override
  public String createCertificateNumber(CertReqBody certReqBody) {

    final String mobile = certReqBody.getMobile();

    Authenticode authenticode = new Authenticode();
    Map<String, Object> result = new HashMap<>();
    result.put("mobile", mobile);
    authenticode.setValue(JsonUtils.toJson(result));
    authenticode.setExpireTime(LocalDateTime.now().plusSeconds(Authenticode.EXPIRE_TIME_MOBILE));
    String code = authenticodeService.tokenCode(authenticode);

    // 개발서버에서는 log 로 출력
    if (processEnv.isDevelopment()) {
      log.debug("code ::: " + code);
      log.debug("mobile ::: " + mobile);
    }
    return code;
  }
}
