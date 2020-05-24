package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginReqBody extends BaseRequestBody {

    private static final long serialVersionUID = -1459552177432102277L;

    private String email;
    private String password;
}
