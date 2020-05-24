package kr.co.queenssmile.core.model.resbody.board;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeDeserializer;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeSerializer;
import kr.co.queenssmile.core.domain.board.qna.Answer;
import kr.co.queenssmile.core.model.BaseResponseBody;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(value = "qna", collectionRelation = "qnas")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaResBody extends BaseResponseBody {

  private static final long serialVersionUID = -8865272994721416070L;

  private Long id;
  private String title;
  private String content;
  private Answer answer;

  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
  @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
  private LocalDateTime regDate;

  private String fullname; // 작성자명
  private String categories;

  public boolean getHasAnswer() {
    return !StringUtils.isEmpty(this.answer);
  }
}
