package kr.co.queenssmile.core.model.resbody.board;

import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "faq", collectionRelation = "faqs")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaqResBody extends BaseResponseBody {


  private static final long serialVersionUID = 8780396155649692018L;
  private Long id;
  private String question;
  private String answer;

  private String categories;
}
