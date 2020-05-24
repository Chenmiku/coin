package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ValidMobileReqBody extends BaseRequestBody {

    private static final long serialVersionUID = -2372629375344184256L;
    String mobile;
}
