package kr.co.queenssmile.api.web.account;

import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kr.co.queenssmile.api.service.user.CertService;
import kr.co.queenssmile.api.service.user.UserService;
import kr.co.queenssmile.core.config.database.PwdEncConfig;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.config.exception.ResponseErrorCode;
import kr.co.queenssmile.core.config.exception.UnAuthorizedException;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.domain.user.UserRepository;
import kr.co.queenssmile.core.model.file.FileMeta;
import kr.co.queenssmile.core.model.reqbody.user.*;
import kr.co.queenssmile.core.utils.ValidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/me")
public class APIMyPageController {

  @Value("${spring.application.name}")
  private String appName;

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CertService certService;

  @Autowired
  private PwdEncConfig pwdEncConfig;

  /**
   * [user-me-1] 내 정보 (access_token)
   */
  @Operation(summary = "[user-me-1] My Account Information (내 정보)", description = "has access_token", security = {@SecurityRequirement(name = "bearerAuth")})
  @Parameters(value = {
      @Parameter(in = ParameterIn.HEADER, name = "Accept-Language", description = "Language Code", example = "ko_KR")
  })
  @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<?> me(Locale locale,
                              @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    if (oAuth2Authentication == null || StringUtils.isEmpty(oAuth2Authentication.getPrincipal())) {
      throw new UnAuthorizedException();
    }

    Object principal = oAuth2Authentication.getPrincipal();
    return ResponseEntity.ok(userService.profile(principal.toString(), locale));
  }

  /**
   * [user-myp-1] 비밀번호 수정 (access_token)
   */
  @Operation(summary = "[user-myp-1] Modify password (비밀번호 수정)", description = "has access_token")
  @PutMapping(value = "/password", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity changePassword(@RequestBody ChangePasswordReqBody changePassword,
                                       @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);
    final String email = oAuth2Authentication.getPrincipal().toString();

    if (changePassword == null
        || StringUtils.isEmpty(changePassword.getPassword())
        || StringUtils.isEmpty(changePassword.getNewPassword())) {
      throw new BadRequestException();
    }

    if (!ValidUtils.isPasswordPattern(changePassword.getNewPassword())) {
      throw new BadRequestException(ValidUtils.MESSAGE_PASSWORD_NEW);
    }

    userService.changePassword(changePassword, email);
    return ResponseEntity.ok("비밀번호가 변경되었습니다.");
  }

  /**
   * [user-myp-2] 휴대폰번호 수정 1 (cert, access_token)
   */
  @Operation(summary = "[user-myp-2] Modify phone number (휴대폰번호 수정)", description = "has access_token")
  @PutMapping(value = "mobile", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity changeMobile(@RequestBody CertConfirmReqBody confirm,
                                     @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);
    final String email = oAuth2Authentication.getPrincipal().toString();

    if (confirm == null
        || StringUtils.isEmpty(confirm.getCode())
        || StringUtils.isEmpty(confirm.getMobile())
        || !Objects.equals(confirm.getMode(), CertReqBody.Mode.CERT_MOBILE)) {
      throw new BadRequestException();
    }

    userService.changeMobile(confirm, email);
    return ResponseEntity.ok("휴대폰번호가 변경되었습니다.");
  }

  /**
   * [user-myp-3] 이미지 변경
   */
  @Operation(summary = "[user-myp-3] Modify profile image (이미지 수정)", description = "has access_token")
  @PutMapping(value = "/image", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity changeImage(@RequestParam("file") MultipartFile file,
                                    @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);
    final String email = oAuth2Authentication.getPrincipal().toString();

    FileMeta fileMeta = userService.updateProfileImage(file, email);

    return ResponseEntity.ok(fileMeta);
  }


  /**
   * [user-myp-4] 이름 변경
   */
  @Operation(summary = "[user-myp-4] Modify full name (성명 수정)", description = "has access_token")
  @PutMapping(value = "full-name", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity changeLastName(@RequestBody ChangeFullNameReqBody reqBody,
                                       @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);
    final String email = oAuth2Authentication.getPrincipal().toString();

    if (reqBody == null
        || StringUtils.isEmpty(reqBody.getFullName())) {
      throw new BadRequestException();
    }

    userService.changeFullName(reqBody.getFullName(), email);
    return ResponseEntity.ok("이름이 변경되었습니다.");
  }

  /**
   * [user-myp-5] SNS 계졍연동 토글 (access_token)
   */
  @Operation(summary = "[user-myp-5] SNS account linkage - Toggle way (SNS 계정 연동 - 토글방식)", description = "has access_token")
  @PutMapping(value = "/sns", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity changeSns(@RequestBody ChangeSnsReqBody requestBody,
                                  @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);
    final String email = oAuth2Authentication.getPrincipal().toString();

    if (requestBody == null
        || requestBody.getSnsType() == null) {
      throw new BadRequestException();
    }
    userService.changeSns(requestBody, email);
    return ResponseEntity.ok("");
  }


  /**
   * [user-myp-6] Native SNS 계졍연동 토글 (access_token)
   */
  @Operation(summary = "[user-myp-6] Native App SNS account linkage - Toggle way (Native 어플용 SNS 계정연동 - 토글방식)", description = "has access_token")
  @PutMapping(value = "/sns/native", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity changeSnsNative(@RequestBody ChangeSnsNativeReqBody requestBody,
                                        @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);
    final String email = oAuth2Authentication.getPrincipal().toString();

    if (requestBody == null) {
      throw new BadRequestException();
    }
    userService.changeSnsNative(requestBody, email);
    return ResponseEntity.ok("");
  }

  /**
   * [user-myp-7] 회원 탈퇴 (access_token)
   */
  @Operation(summary = "[user-myp-7] 회원 탈퇴 (회원 탈퇴)", description = "has access_token")
  @PostMapping(value = "leave", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<?> leave(@RequestBody LeaveUserReqBody requestBody,
                                 @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);

    if (requestBody == null
        || StringUtils.isEmpty(requestBody.getPassword())
        || StringUtils.isEmpty(requestBody.getLeaveReason())
        || !requestBody.isLeaveTerms()) {
      throw new BadRequestException();
    }

    Object principal = oAuth2Authentication.getPrincipal();
    User user = userService.get(principal.toString());

    if (user == null) {
      throw new BadRequestException(ResponseErrorCode.NOT_EXIST_USER.name());
    }

    if (pwdEncConfig.getPasswordEncoder().matches(requestBody.getPassword(), user.getPassword())) {
      userService.leave(user.getId(), requestBody.getLeaveReason());
      return ResponseEntity.ok().build();
    } else {
      throw new BadRequestException(ResponseErrorCode.INVALID_ENTERED_PASSWORD.name());
    }
  }

  /**
   * [user-myp-8] 비밀번호 일치 확인
   */
  @Operation(summary = "[user-myp-8] Confirm password match (비밀번호 일치 확인)", description = "has access_token")
  @PostMapping(value = "match-password", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity matchPassword(@RequestBody MatchingPasswordReqBody requestBody,
                                      @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);

    if (requestBody == null
        || StringUtils.isEmpty(requestBody.getPassword())) {
      throw new BadRequestException("이메일 또는 비밀번호 값이 없습니다.");
    }

    String email = oAuth2Authentication.getPrincipal().toString();
    String password = requestBody.getPassword();

    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new BadRequestException(ResponseErrorCode.NOT_EXIST_USER.getMessage());
    }

    boolean matched = userService.matchPassword(user, password);

    if (matched) {
      return ResponseEntity.ok("비밀번호가 일치합니다.");
    } else {
      return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
    }
  }

  /**
   * [user-myp-9] 이메일 수정 (cert, access_token)
   */
  @Operation(summary = "[user-myp-9] Modify Email(ID) (이메일 인증)", description = "has access_token / has cert")
  @PutMapping(value = "email", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity changeEmail(@RequestBody CertReqBody cert,
                                    HttpServletRequest request,
                                    @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);
    final String email = oAuth2Authentication.getPrincipal().toString();

    if (cert == null
        || StringUtils.isEmpty(cert.getEmail())
        || !ValidUtils.isEmailPattern(cert.getEmail())
        || !Objects.equals(cert.getMode(), CertReqBody.Mode.CERT_EMAIL)) {
      throw new BadRequestException();
    }

    userService.changeEmail(cert, email, request);
    certService.cert(cert, request);
    log.debug("email ::: {}", email);
    User user = userRepository.findByEmail(cert.getEmail());

    // 계정이 존재하는지 체크
    if (user == null) {
      throw new BadRequestException(ResponseErrorCode.ENTERED_INVALID.getMessage());
    }

    return ResponseEntity.ok(
        ImmutableMap
            .builder()
            .put("access_token", userService.getJWT(user))
            .build()
    );
  }

  /**
   * [user-myp-10] 이메일 구독 활성/비활성
   */
  @Operation(summary = "[user-myp-10] Email subscription - Toggle way (이메일 구독 - 토글)", description = "has access_token")
  @PutMapping(value = "/subscribe/email", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<?> subscribeEmail(HttpServletRequest request,
                                          @RequestBody SubscribeReqBody requestBody,
                                          @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);
    final String email = oAuth2Authentication.getPrincipal().toString();

    if (requestBody == null
        || requestBody.getActive() == null) {
      throw new BadRequestException();
    }

    return ResponseEntity.ok(userService.subscribeByEmail(requestBody.getActive(), email));
//    return ResponseEntity.ok().body(SuccessResponseBody.of(true, requestBody.getActive() ? "이메일 수신이 활성되었습니다." : "이메일 수신이 비활성되었습니다."));
  }

  /**
   * [user-myp-11] SMS 구독 활성/비활성
   */
  @Operation(summary = "[user-myp-11] SNS subscription - Toggle way (SNS 구독 - 토글)", description = "has access_token")
  @PutMapping(value = "/subscribe/sms", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<?> subscribeSMS(HttpServletRequest request,
                                        @RequestBody SubscribeReqBody requestBody,
                                        @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);
    final String email = oAuth2Authentication.getPrincipal().toString();

    if (requestBody == null
        || requestBody.getActive() == null) {
      throw new BadRequestException();
    }

    return ResponseEntity.ok(userService.subscribeBySMS(requestBody.getActive(), email));
//    return ResponseEntity.ok().body(SuccessResponseBody.of(true, requestBody.getActive() ? "문자 수신이 활성되었습니다." : "문자 수신이 비활성되었습니다."));
  }

  /**
   * [user-myp-12] 카카오톡 구독 활성/비활성
   */
  @Operation(summary = "[user-myp-12] Kakaotalk subscription - Toggle way (카카오톡 구독 - 토글)", description = "has access_token")
  @PutMapping(value = "/subscribe/kakao", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<?> subscribeKakao(HttpServletRequest request,
                                          @RequestBody SubscribeReqBody requestBody,
                                          @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);
    final String email = oAuth2Authentication.getPrincipal().toString();

    if (requestBody == null
        || requestBody.getActive() == null) {
      throw new BadRequestException();
    }

    return ResponseEntity.ok(userService.subscribeByKakao(requestBody.getActive(), email));
//    return ResponseEntity.ok().body(SuccessResponseBody.of(true, requestBody.getActive() ? "카카오톡 수신이 활성되었습니다." : "카카오톡 수신이 비활성되었습니다."));
  }

  /**
   * [user-myp-13] 이메일 인증 재발송
   */
  @Operation(summary = "[user-myp-13] Resend email authentication. (이메일 인증 재발송)", description = "has access_token")
  @PutMapping(value = "/resend/email", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity resendEmail(OAuth2Authentication oAuth2Authentication,
                                    HttpServletRequest request,
                                    Locale locale) {

    ValidUtils.isForbidden(oAuth2Authentication);
    final String email = oAuth2Authentication.getPrincipal().toString();

    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new BadRequestException("존재하지 않는 회원입니다.");
    }

    if (user.getVerification() != null && user.getVerification().isEmail()) {
      throw new BadRequestException("이미 인증된 회원입니다.");
    }

    CertReqBody cert = new CertReqBody();
    certService.cert(cert, request);

    return ResponseEntity.ok("인증 이메일이 발송되었습니다.");
  }

  /**
   * [user-myp-14] 회원정보 수정
   */
  @Operation(summary = "[user-myp-14] Update user information. (회원정보 수정)", description = "has access_token")
  @PutMapping(value = "/update_info", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updateUserInfo(@RequestBody UpdateUserInfoReqBody requestBody,
                                    @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);
    final String email = oAuth2Authentication.getPrincipal().toString();

    if (requestBody == null) {
      throw new BadRequestException();
    }

    userService.updateUserInfo(email, requestBody);

    return ResponseEntity.ok("Update user information successful");
  }

  /**
   * [user-myp-15] 회원 탈퇴
   */
  @Operation(summary = "[user-myp-15] delete member 회원 탈퇴 (회원 탈퇴)", description = "has access_token")
  @PostMapping(value = "delete_member", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<?> deleteMember(@RequestBody DeleteMemberReqBody requestBody,
                                 @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);

    if (requestBody == null
            || !requestBody.isLeaveTerms()) {
      throw new BadRequestException();
    }

    User user = userService.get(oAuth2Authentication.getPrincipal().toString());

    if (user == null) {
      throw new BadRequestException(ResponseErrorCode.NOT_EXIST_USER.name());
    }

    userService.deleteMember(user.getId());
    return ResponseEntity.ok().build();
  }

}
