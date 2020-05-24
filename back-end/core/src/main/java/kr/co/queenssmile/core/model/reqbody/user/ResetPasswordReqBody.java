package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResetPasswordReqBody extends BaseRequestBody {

  private static final long serialVersionUID = -5745026670782801228L;

  private String code;
  private String password;
  private String passwordConfirm;
}
