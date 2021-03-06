package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ValidBusinessNameReqBody extends BaseRequestBody {

    private static final long serialVersionUID = -8257888208741352866L;

    private String businessNumber;
    private String businessName;
}