package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.domain.user.Gender;
import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserInfoReqBody extends BaseRequestBody {

  private static final long serialVersionUID = -1567352085715794401L;

  private String fullName; // 이름
  private Gender gender;
  private LocalDate birthDate; // 생일
}
