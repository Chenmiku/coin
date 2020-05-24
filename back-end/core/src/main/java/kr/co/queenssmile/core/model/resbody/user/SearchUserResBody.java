package kr.co.queenssmile.core.model.resbody.user;

import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "searchUser")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchUserResBody extends BaseResponseBody {

  private static final long serialVersionUID = -638180650304533843L;

  private Long id;
  private String email;
  private String fullName;
}
