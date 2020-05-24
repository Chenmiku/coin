package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordReqBody extends BaseRequestBody {

    private static final long serialVersionUID = -3469914382919401016L;

    private String password;
    private String newPassword;
}
