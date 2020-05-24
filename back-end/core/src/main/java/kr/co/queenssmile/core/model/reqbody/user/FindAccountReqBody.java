package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindAccountReqBody extends BaseRequestBody {

    private static final long serialVersionUID = -7035713498913747875L;

    private String mobile;
    private String code; // 확인용
}
