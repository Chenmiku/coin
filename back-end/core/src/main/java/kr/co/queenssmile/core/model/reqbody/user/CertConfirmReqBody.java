package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@ToString
public class CertConfirmReqBody extends BaseResponseBody {

    private static final long serialVersionUID = -707347076146318685L;

    private String code;
    private String mobile;
    private CertReqBody.Mode mode;
}
