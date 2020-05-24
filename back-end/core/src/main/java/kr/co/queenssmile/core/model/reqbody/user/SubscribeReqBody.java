package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SubscribeReqBody extends BaseRequestBody {
  private static final long serialVersionUID = -6369426921362956820L;

  public enum Type {
    EMAIL, SMS, KAKAO
  }

  private Boolean active;
}
