package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.domain.file.EmbeddedFile;
import kr.co.queenssmile.core.domain.user.*;
import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpReqBody extends BaseRequestBody {

  private static final long serialVersionUID = -2212393886807589144L;

  private String email; // ID
  private String password; // 비밀번호
  private String fullName; // 성명
  private String mobile; // 휴대전화
  private String companyName;
  private String businessNumber;
  private String businessEmail;


  private TermsAgree termsAgree; // 동의
  private SocialId socialId; // SNS 아이디
  private Verification verification; // 인증
  private EmbeddedFile bcFile;


  private String image;
  private String imageBase64;

  public User toUser() {

    User user = new User();

    user.setEmail(this.getEmail().trim());
    user.setPassword(this.getPassword().trim());
    user.setFullName(this.getFullName().trim());
    user.setMobile(this.getMobile().trim());

    user.setRole(Authority.Role.USER);
    user.setTermsAgree(this.getTermsAgree());
    user.setVerification(this.getVerification());
    user.setImage(this.image);

    return user;
  }
}
