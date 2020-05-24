package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.domain.user.sns.SNSType;
import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangeSnsReqBody extends BaseRequestBody {

  private static final long serialVersionUID = 5738785088672460354L;

  private SNSType snsType; // sns 유형
  private String code;
  private String redirectUri;
  private String state;
}
