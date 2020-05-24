package kr.co.queenssmile.core.domain.board.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeSerializer;
import kr.co.queenssmile.core.domain.AbstractEntityInternational;
import kr.co.queenssmile.core.domain.International.InterText;
import kr.co.queenssmile.core.domain.RestEntityThumbBody;
import kr.co.queenssmile.core.domain.board.comment.Comment;
import kr.co.queenssmile.core.domain.board.post.category.PCategory;
import kr.co.queenssmile.core.domain.embedd.SEO;
import kr.co.queenssmile.core.domain.file.FileEntity;
import kr.co.queenssmile.core.model.resbody.board.PostResBody;
import kr.co.queenssmile.core.model.resbody.board.PostThumbResBody;
import kr.co.queenssmile.core.utils.DateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * 게시판
 */
@Slf4j
@Entity(name = "Post")
@Getter
@Setter
@ToString(exclude = {"categories", "comments", "files"})
@NoArgsConstructor
public class Post extends AbstractEntityInternational<Long> implements RestEntityThumbBody<PostResBody, PostThumbResBody> {

  private static final long serialVersionUID = 950674970843219381L;

  public static String[] IGNORE_PROPERTIES = {
      "id",
      "type",
      "pageView",
      "comments"
  };

  @Getter
  public enum Type {
    NOTICE("공지사항"),
    CONTENTS("콘텐츠");

    final String value;

    Type(final String value) {
      this.value = value;
    }
  }

  @PrePersist
  public void prePersist() {
    if (this.type == null) {
      this.type = Type.NOTICE;
    }
    if (this.regDate == null) {
      this.regDate = LocalDateTime.now();
    }
  }

  @PreUpdate
  public void PreUpdate() {

  }

  public Post(Type type) {
    this.type = type;
  }

  @Id
  @GeneratedValue
  @Column(unique = true)
  private Long id;

  @Column(columnDefinition = "TINYINT(1) default 0")
  private Type type; // 게시판 유형

  @Column
  private String thumbnail; // 썸네일 이미지

  @Embedded
  @AttributeOverrides({        // 임베디드 타입에 정의한 매핑정보를 재정의
      @AttributeOverride(name = "textKoKr", column = @Column(name = "titleKoKr", length = 200)),
      @AttributeOverride(name = "textEnUs", column = @Column(name = "titleEnUs", length = 200)),
      @AttributeOverride(name = "textJaJp", column = @Column(name = "titleJaJp", length = 200)),
      @AttributeOverride(name = "textZhCn", column = @Column(name = "titleZhCn", length = 200)),
      @AttributeOverride(name = "textZhTw", column = @Column(name = "titleZhTw", length = 200))
  })
  private InterText title; // 제목

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "textKoKr", column = @Column(name = "contentKoKr", columnDefinition = "LONGTEXT")),
      @AttributeOverride(name = "textEnUs", column = @Column(name = "contentEnUs", columnDefinition = "LONGTEXT")),
      @AttributeOverride(name = "textJaJp", column = @Column(name = "contentJaJp", columnDefinition = "LONGTEXT")),
      @AttributeOverride(name = "textZhCn", column = @Column(name = "contentZhCn", columnDefinition = "LONGTEXT")),
      @AttributeOverride(name = "textZhTw", column = @Column(name = "contentZhTw", columnDefinition = "LONGTEXT"))
  })
  private InterText content; // 내용


  @Column(columnDefinition = "BIGINT(20) default 0")
  private long pageView; // 조회수

  @Column(columnDefinition = "BIT(1) default 0")
  private boolean top; // 상단고정

  @Column(columnDefinition = "BIT(1) default 1")
  private boolean active; // 활성/비활성

  @Column(columnDefinition = "BIGINT(20) default 0")
  private long orderDescending; // 순서, 내림차순

  public void changeOrder(Post target) {

    Post source = this;

    long sourceOrderDescending = source.getOrderDescending();
    long targetOrderDescending = target.getOrderDescending();

    target.setOrderDescending(sourceOrderDescending);
    source.setOrderDescending(targetOrderDescending);
  }

  @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_TIME_UNIT_BAR)
  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
  private LocalDateTime regDate; // 등록일 , 내림차순

  @Embedded
  private SEO seo; // 검색 엔진 최적화


  @JsonIgnore
  @OrderBy("orderAscending ASC")
  @ManyToMany
  @JoinTable(name = "PostToCategory",
      joinColumns = @JoinColumn(name = "idPost", nullable = false, foreignKey = @ForeignKey(name = "FK_Post_For_Post_To_Cate")),
      inverseJoinColumns = @JoinColumn(name = "idCategory", nullable = false, foreignKey = @ForeignKey(name = "FK_Category_For_Post_To_Cate"))
  )
  private List<PCategory> categories = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "relativePost", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();


  @JsonIgnore
  @OrderBy("orderAscending ASC")
  @ManyToMany(cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE
  })
  @JoinTable(name = "PostToFile",
      joinColumns = @JoinColumn(name = "idPost", nullable = false, foreignKey = @ForeignKey(name = "FK_Post_For_Post_To_File")),
      inverseJoinColumns = @JoinColumn(name = "idFile", nullable = false, foreignKey = @ForeignKey(name = "FK_File_For_Post_To_File"))
  )
  private List<FileEntity> files = new ArrayList<>();


  public void uploadFiles() {
    if (this.files != null) {
      this.files.removeIf(file -> StringUtils.isEmpty(file.getUrl()));
    }
  }

  @Override
  public void setLocale(Locale locale) {
    super.setLocale(locale);
    if (this.title != null)
      this.title.setLocale(this.locale);
    if (this.content != null)
      this.content.setLocale(this.locale);
    if (this.categories != null) {
      this.categories.forEach(category -> {
        category.setLocale(locale);
      });
    }
  }

  @Override
  public void delete() {

  }

  @Override
  public void lazy() {
    if (this.categories != null) {
      this.categories.size();
    }
    if (this.comments != null) {
      this.comments.size();
    }
    if (this.files != null) {
      this.files.size();
    }
  }


  @Override
  public PostResBody toBody(Locale locale) {
    this.setLocale(locale);
    return PostResBody.builder()
        .id(this.id)
        .type(this.type)
        .title(this.title.getValue())
        .content(this.content.getValue())
        .thumbnail(this.thumbnail)
        .pageView(this.pageView)
        .regDate(this.regDate)
        .seo(this.seo)
        .pin(this.top)
        .build();
  }

  @Override
  public PostThumbResBody toThumbBody(Locale locale) {
    this.setLocale(locale);
    return PostThumbResBody.builder()
        .id(this.id)
        .type(this.type)
        .title(this.title.getValue())
        .thumbnail(this.thumbnail)
        .pageView(this.pageView)
        .regDate(this.regDate)
        .pin(this.top)
        .build();
  }
}
