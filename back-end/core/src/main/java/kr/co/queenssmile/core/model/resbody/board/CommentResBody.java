package kr.co.queenssmile.core.model.resbody.board;

import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "comment", collectionRelation = "comments")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResBody extends BaseResponseBody {
  private static final long serialVersionUID = 4928024495184674123L;

  private Long id;
}
