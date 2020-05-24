package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CertReqBody extends BaseRequestBody {

    private static final long serialVersionUID = 5843409952798725635L;

    @Getter
    public enum Mode {
        CERT_MOBILE("휴대폰 인증 및 수정")// 회원가입, 수정
        , CERT_EMAIL("이메일 존재 유무") // 회원가입,
        , FIND_PASSWORD_EMAIL("바말번호 찾기") // 이메일
        , FIND_PASSWORD_MOBILE("바말번호 찾기") // 모바일
        , FIND_ACCOUNT_MOBILE("계졍 찾기") // 모바일
        ;

        private final String value;

        Mode(final String value) {
            this.value = value;
        }
    }

    private String mobile;
    private Mode mode;
    private String email;
}
