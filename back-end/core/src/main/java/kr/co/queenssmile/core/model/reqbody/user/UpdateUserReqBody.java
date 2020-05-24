package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserReqBody extends BaseRequestBody {

  private static final long serialVersionUID = 8777629174501833252L;

  private String fullName; // 이름

  private boolean smsRcv; // SMS 수신동의
  private boolean emailRcv; // 이메일 수신동의
  private boolean kakaoRcv; // 이메일 수신동의

}
