package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ValidEmailReqBody extends BaseRequestBody {
  private static final long serialVersionUID = 3624673557127238712L;

  private String email;
}
