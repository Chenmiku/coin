package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MatchingPasswordReqBody extends BaseRequestBody {

  private static final long serialVersionUID = -967274294480844947L;

  private String password;
}
