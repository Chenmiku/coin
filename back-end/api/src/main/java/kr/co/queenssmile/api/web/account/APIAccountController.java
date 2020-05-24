package kr.co.queenssmile.api.web.account;

import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.queenssmile.api.service.user.CertService;
import kr.co.queenssmile.api.service.user.UserService;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.config.exception.ConflictException;
import kr.co.queenssmile.core.config.exception.ResponseErrorCode;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.domain.user.UserRepository;
import kr.co.queenssmile.core.model.reqbody.user.CertReqBody;
import kr.co.queenssmile.core.model.reqbody.user.LoginReqBody;
import kr.co.queenssmile.core.model.reqbody.user.SignUpReqBody;
import kr.co.queenssmile.core.model.reqbody.user.ValidEmailReqBody;
import kr.co.queenssmile.core.utils.ValidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class APIAccountController {

  @Value("${spring.application.name}")
  private String appName;

  @Autowired
  private UserService userService;

  @Autowired
  private CertService certService;

  @Autowired
  private UserRepository userRepository;


//  @Autowired
//  private EmailService emailService;

  /**
   * [user-login-1] 로그인
   */
  @Operation(summary = "[user-login-1] Login (로그인)", description = "Login API")
  @PostMapping(value = "/login", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity restLogin(HttpServletRequest request,
                                  @RequestBody LoginReqBody requestBody) {

    if (requestBody == null
        || StringUtils.isEmpty(requestBody.getEmail())
        || StringUtils.isEmpty(requestBody.getPassword())) {
      throw new BadRequestException();
    }
    return userService.authAccessToken(requestBody, request);
  }


  /**
   * [user-signup-1] 회원가입
   */
  @Operation(summary = "[user-signup-1] Sign Up (회원가입)", description = "Sign Up API")
  @PostMapping(value = "/signup", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity signup(@RequestBody SignUpReqBody requestBody,
                               HttpServletRequest request) {

    if (requestBody == null
        || StringUtils.isEmpty(requestBody.getEmail())
        || StringUtils.isEmpty(requestBody.getFullName())
        || requestBody.getTermsAgree() == null
        || requestBody.getVerification() == null
//        || StringUtils.isEmpty(requestBody.getUsername())
//                || requestBody.getBirth() == null (### 필요시 추가)
    ) {
      throw new BadRequestException();
    }

    // 이용약관 및 개인정보취급방침에 동의하지 안함
    if (!requestBody.getTermsAgree().isTaService()
        || !requestBody.getTermsAgree().isTaPrivacy()
//        || !requestBody.getTermsAgree().isTaYouth()
    ) {
      throw new BadRequestException("필수항목에 동의하여야 가입할 수 있습니다.");
    }

    if (requestBody.getSocialId() == null || requestBody.getSocialId().isEmpty()) {
      if (!ValidUtils.isPasswordPattern(requestBody.getPassword())) {
        throw new BadRequestException(ValidUtils.MESSAGE_PASSWORD_NEW);
      }
    }

    User user = userService.join(requestBody);

    // 가입 환영 이메일
    String subject = String.format("[%s] 맴버가 되신걸 환영합니다. THANK YOU FOR SIGNING UP!", appName);
    String email = user.getEmail();

    java.util.Map<String, Object> model = new HashMap<>();
    model.put("subject", subject);
    model.put("fullname", user.getFullName());
    model.put("email", email);
    model.put("createdDate", user.getCreatedDate());

    model.put("appName", appName);
    model.put("nowDate", LocalDate.now());

//    emailService.send(email, subject, model, "email/sign-up.ftl");

    try {
      CertReqBody cert = new CertReqBody();
      cert.setMode(CertReqBody.Mode.CERT_EMAIL);
      cert.setEmail(email);
      String code = certService.cert(cert, request);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return ResponseEntity.ok(
        ImmutableMap
            .builder()
            .put("access_token", userService.getJWT(user))
            .build()
    );
  }

  /**
   * [user-signup-2] 이메일 유효성 검사 (탈퇴한 이메일인지, 이미 존재하는 이메일인지 체크)
   */
  @Operation(summary = "[user-signup-2] Email validation (이메일 유효성 검사)", description = "Check whether it is a withdrawn email or an existing email (탈퇴한 이메일인지, 이미 존재하는 이메일인지 체크)")
  @PostMapping(value = "valid-email", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity validEmail(@RequestBody ValidEmailReqBody requestBody) {

    if (requestBody == null
        || StringUtils.isEmpty(requestBody.getEmail())) {
      throw new BadRequestException();
    }

    User user = userRepository.findByEmail(requestBody.getEmail());

    if (user != null) {
      if (!user.getUserDetailsMeta().isEnabled()
          || user.getLeaveMeta().isLeave()) {
        throw new BadRequestException(ResponseErrorCode.LEAVED_USER.getMessage());
      }
      throw new ConflictException(ResponseErrorCode.ALREADY_EXIST_EMAIL.getMessage());
    }
    return ResponseEntity.ok("가입정보가 없는 이메일주소입니다.");
  }
}
