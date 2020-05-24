package kr.co.queenssmile.core.model.resbody.board;

import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "contact", collectionRelation = "contacts")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactResBody extends BaseResponseBody {

  private static final long serialVersionUID = 1165933905543623563L;
  private Long id;
}
