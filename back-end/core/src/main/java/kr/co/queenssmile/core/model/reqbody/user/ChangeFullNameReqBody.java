package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChangeFullNameReqBody extends BaseRequestBody {

  private static final long serialVersionUID = -4086252111150897191L;
  private String fullName;
}
