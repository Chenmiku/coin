package kr.co.queenssmile.core.model.resbody.board;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeDeserializer;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeSerializer;
import kr.co.queenssmile.core.domain.board.post.Post;
import kr.co.queenssmile.core.model.BaseResponseThumbBody;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(value = "post", collectionRelation = "posts")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostThumbResBody extends BaseResponseThumbBody {

  private static final long serialVersionUID = -7322479157527620703L;
  private Long id;
  private Post.Type type; // 게시판 유형
  private String title; // 제목
  private String thumbnail; // 썸네일
  private long pageView; // 조회수

  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
  @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
  private LocalDateTime regDate; // 등록일 , 내림차순

  private String categories;

  private boolean pin;
}
