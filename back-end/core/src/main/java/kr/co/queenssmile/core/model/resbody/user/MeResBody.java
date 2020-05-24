package kr.co.queenssmile.core.model.resbody.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.domain.user.Authority;
import kr.co.queenssmile.core.domain.user.TermsAgree;
import kr.co.queenssmile.core.domain.user.Verification;
import kr.co.queenssmile.core.domain.user.embed.StoreScope;
import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Relation(value = "me")
@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeResBody extends BaseResponseBody {

  private static final long serialVersionUID = 4536454965313232901L;

  private String email;
  private String fullName;
  private String mobile;

  private List<Authority.Role> roles;

  @JsonIgnore
  private String image; // 이미지

  private TermsAgree termsAgree; // SMS 수신동의 or 이메일 수신동의

  private Verification verification; // 이메일 전화번호 인증 [인증 채크 별개]

  private boolean expiredPassword; // 비밀번호 변경 만료 상태
  private boolean hasPassword; // 비밀번호가 설정되어 있는지 상태값
  private boolean dormancy; // 휴면계정

  private StoreScope storeScope;
}
