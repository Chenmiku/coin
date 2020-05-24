package kr.co.queenssmile.core.model.resbody.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeDeserializer;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeSerializer;
import kr.co.queenssmile.core.domain.user.Authority;
import kr.co.queenssmile.core.model.BaseResponseThumbBody;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(value = "user", collectionRelation = "users")
@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserThumbResBody extends BaseResponseThumbBody {

  private static final long serialVersionUID = -699347882292694010L;

  private Long id;
  private String fullName;
  private String email;
  private String mobile;
  private Authority.Role role;

  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
  @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
  private LocalDateTime regdate;

}
