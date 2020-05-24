package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DormantReqBody extends BaseRequestBody {

    private String mobile;
    private String code; // 확인용
    private String companyName;
    private String fullName;
}