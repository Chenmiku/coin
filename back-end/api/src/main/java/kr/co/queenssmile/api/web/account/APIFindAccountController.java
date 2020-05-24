package kr.co.queenssmile.api.web.account;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.queenssmile.api.service.user.UserService;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.model.reqbody.user.FindAccountReqBody;
import kr.co.queenssmile.core.model.reqbody.user.FindPasswordReqBody;
import kr.co.queenssmile.core.utils.ValidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class APIFindAccountController {

  @Autowired
  private UserService userService;

  @Operation(summary = "[user-fact-1-2] Find Email - [1]Send SMS authentication number (이메일 찾기 - [1]모바일 인증번호 발송)", description = "[1]Send SMS authentication number, [2]Check authentication number, [3]Get a list of ID(email)s. ([1]SMS 인증번호 발송, [2]인증번호확인, [3]ID(이메일) 항목 가져오기)")
  @PostMapping(value = "find/account/mobile", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<?> findAccountMobile(HttpServletRequest request,
                                             @RequestBody FindAccountReqBody requestBody) {

    if (requestBody == null
        || StringUtils.isEmpty(requestBody.getMobile())) {
      throw new BadRequestException();
    }

    final String mobile = requestBody.getMobile();
    userService.findAccountByMobile(mobile, request);
    return ResponseEntity.ok().body("인증번호가 발송되었습니다.");
  }

  @Operation(summary = "[user-fact-2-2] Find Email - [2]Check authentication number, [3]Get a list of ID(email)s (이메일 찾기 - [2]인증번호확인, [3]아이디 확인 페이지 이동)", description = "[1]Send SMS authentication number, [2]Check authentication number, [3]Get a list of ID(email)s. ([1]SMS 인증번호 발송, [2]인증번호확인, [3]ID(이메일) 항목 가져오기)")
  @PostMapping(value = "find/account/mobile/confirm", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity findAccountMobileConfirm(@RequestBody FindAccountReqBody requestBody) {

    if (requestBody == null
        || StringUtils.isEmpty(requestBody.getCode())
        || StringUtils.isEmpty(requestBody.getMobile())) {
      throw new BadRequestException();
    }

    return ResponseEntity.ok(userService.findAccountByMobileConfirm(requestBody));
  }

  @Operation(summary = "[user-reset-pwd-1] Reset password - Send authentication email (비밀번호 재설정 - 인증 메일 발송)", description = "Go to password reset page (비밀번호 재설정 페이지로 이동)")
  @PostMapping(value = "/reset/password", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<?> findPWEmail(HttpServletRequest request,
                                       @RequestBody FindPasswordReqBody requestBody) {

    if (requestBody == null
        || StringUtils.isEmpty(requestBody.getEmail())
        || !ValidUtils.isEmailPattern(requestBody.getEmail())) {
      throw new BadRequestException();
    }

    final String email = requestBody.getEmail();
    userService.findPasswordByEmail(email, request);
    return ResponseEntity.ok("인증 메일이 발송되었습니다.");
  }
}
