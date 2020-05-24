package kr.co.queenssmile.core.model.resbody.board;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeDeserializer;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeSerializer;
import kr.co.queenssmile.core.domain.board.post.Post;
import kr.co.queenssmile.core.domain.embedd.SEO;
import kr.co.queenssmile.core.model.BaseResponseBody;
import kr.co.queenssmile.core.model.file.FileMeta;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.List;

@Relation(value = "post", collectionRelation = "posts")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResBody extends BaseResponseBody {

  private static final long serialVersionUID = -7676420592670217510L;

  private Long id;
  private Post.Type type; // 게시판 유형
  private String title; // 제목
  private String content;
  private String thumbnail; // 썸네일
  private long pageView; // 조회수

  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
  @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
  private LocalDateTime regDate; // 등록일 , 내림차순

  private SEO seo;
  private String categories;

  private boolean hasPrevious;
  private Long idPrevious;
  private boolean hasNext;
  private Long idNext;


  private List<FileMeta> files;
  private boolean pin;
}
