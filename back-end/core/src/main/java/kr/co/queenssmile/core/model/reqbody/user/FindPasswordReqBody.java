package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindPasswordReqBody extends BaseRequestBody {

    private static final long serialVersionUID = 357681494224833031L;

    private String email;
    private String mobile;
    private String fullName;

    private String code;
}
