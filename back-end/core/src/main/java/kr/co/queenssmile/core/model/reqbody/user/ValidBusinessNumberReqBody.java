package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ValidBusinessNumberReqBody extends BaseRequestBody {

    private static final long serialVersionUID = -450922911058716510L;
    private String businessNumber;
}
