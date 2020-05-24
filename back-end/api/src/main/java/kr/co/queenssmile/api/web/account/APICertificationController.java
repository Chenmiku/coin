package kr.co.queenssmile.api.web.account;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.queenssmile.api.service.user.CertService;
import kr.co.queenssmile.api.service.user.CertServiceImpl;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.config.exception.ConflictException;
import kr.co.queenssmile.core.config.exception.ResponseErrorCode;
import kr.co.queenssmile.core.model.AuthentiCodeResBody;
import kr.co.queenssmile.core.model.reqbody.user.CertConfirmReqBody;
import kr.co.queenssmile.core.model.reqbody.user.CertReqBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/cert")
public class APICertificationController {

  @Autowired
  private CertService certService;

  /**
   * [user-signup-3] 회원가입 모바일인증
   * [user-cert-1] 모바일인증
   */
  @Operation(summary = "[user-signup-3] Mobile authentication (회원가입 모바일인증)", description = "Mobile authentication API when user registers. (사용자 회원가입시 모바일 인증)")
  @PostMapping
  public ResponseEntity<?> cert(@RequestBody CertReqBody cert, HttpServletRequest request) {

    if (cert.getMode() == null) {
      throw new BadRequestException();
    }

    // 모바일 채킹
    if (cert.getMode() == CertReqBody.Mode.CERT_MOBILE) {
      throw new BadRequestException(ResponseErrorCode.NOT_ENTERED_MOBILE.name());
    }

    // 이메일 채킹
    if ((cert.getMode() == CertReqBody.Mode.CERT_EMAIL)
        && StringUtils.isEmpty(cert.getEmail())) {
      throw new BadRequestException(ResponseErrorCode.NOT_ENTERED_EMAIL.name());
    }

    String code = certService.cert(cert, request);

    if (StringUtils.isEmpty(code)) {
      throw new ConflictException(ResponseErrorCode.FAILED_GENERATE_VERIFICATION_CODE.name());
    } else {
      return ResponseEntity.ok("인증번호가 발송되었습니다.");
    }
  }

  /**
   * [user-signup-4] 회원가입 모바일인증 확인
   * [user-cert-2] 모바일인증 확인
   */
  @Operation(summary = "[user-signup-4] Check mobile authentication. (회원가입 모바일인증 확인)", description = "Check mobile authentication API when user registers. (사용자 회원가입시 모바일 인증)")
  @PostMapping("/confirm")
  public ResponseEntity<?> confirmCert(@RequestBody CertConfirmReqBody confirm) {

    if (confirm == null
        || StringUtils.isEmpty(confirm.getCode())
        || confirm.getMode() == null) {
      throw new BadRequestException();
    }

    // 모바일 채킹
    if (confirm.getMode() == CertReqBody.Mode.CERT_MOBILE
        && StringUtils.isEmpty(confirm.getMobile())) {
      throw new BadRequestException();
    }

    AuthentiCodeResBody result = certService.confirm(confirm);

    if (result.isSuccess()) {
      return ResponseEntity.ok(CertServiceImpl.confirmResult(result.getResultCode()));
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(CertServiceImpl.confirmResult(result.getResultCode()));
    }
  }
}
