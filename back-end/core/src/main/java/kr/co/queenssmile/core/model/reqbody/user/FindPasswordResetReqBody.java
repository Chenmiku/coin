package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindPasswordResetReqBody extends BaseRequestBody {

    private static final long serialVersionUID = 5393049311513922987L;

    private String email;
    private String password;
    private String passwordConfirm;

}
