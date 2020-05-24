package kr.co.queenssmile.api.service.user;

import kr.co.queenssmile.core.model.AuthentiCodeResBody;
import kr.co.queenssmile.core.model.reqbody.user.CertConfirmReqBody;
import kr.co.queenssmile.core.model.reqbody.user.CertReqBody;

import javax.servlet.http.HttpServletRequest;

public interface CertService {

  /**
   * 모바일/이메일 인증
   *
   * @param certReqBody 인증 객체
   * @return 인증 코드
   */
  String cert(CertReqBody certReqBody, HttpServletRequest request);

  /**
   * 모바일/이메일 인증 확인
   *
   * @param confirm 확인 코드 객체
   * @return 확인
   */
  AuthentiCodeResBody confirm(CertConfirmReqBody confirm);

  String createCertificateNumber(CertReqBody certReqBody);
}
