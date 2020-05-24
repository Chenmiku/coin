package kr.co.queenssmile.core.model.resbody.user;

import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "user")
@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResBody extends BaseResponseBody {

  private static final long serialVersionUID = -699347882292694010L;

  private Long id;
  private String fullName;
  private String email;
  private String mobile;
}
