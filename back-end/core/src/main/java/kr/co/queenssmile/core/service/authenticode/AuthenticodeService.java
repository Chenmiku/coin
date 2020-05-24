package kr.co.queenssmile.core.service.authenticode;

import kr.co.queenssmile.core.domain.authenticode.Authenticode;
import kr.co.queenssmile.core.model.AuthentiCodeResBody;

/**
 * 인증 토큰
 * (문자 인증, 이메일 인증 등 사용)
 */
public interface AuthenticodeService {

    String tokenCode(Authenticode tokenStorage);

    AuthentiCodeResBody confirmByMobile(String token, String mobile);

    AuthentiCodeResBody getAuthCode(String token);
    AuthentiCodeResBody confirmByEmail(String token);

    String generateToken();

    void deletePreviousValue(String mobile);
}
