package kr.co.queenssmile.core.domain.board.qna;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeDeserializer;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Embeddable
public class Answer implements java.io.Serializable {

  private static final long serialVersionUID = -1794195323338176770L;

  @Column(name = "answer", columnDefinition = "TEXT")
  private String content;

  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
  @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
  private LocalDateTime regDate;
}
