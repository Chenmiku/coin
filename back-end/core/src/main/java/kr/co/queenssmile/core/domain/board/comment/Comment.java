package kr.co.queenssmile.core.domain.board.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.domain.AbstractEntity;
import kr.co.queenssmile.core.domain.RestEntityBody;
import kr.co.queenssmile.core.domain.board.post.Post;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.model.resbody.board.CommentResBody;
import kr.co.queenssmile.core.utils.HtmlUtils;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Locale;

/**
 * 댓글 테이블
 */
@Entity(name = "Comment")
@Getter
@Setter
@ToString(exclude = {"relativeUser", "relativePost"})
public class Comment extends AbstractEntity<Long> implements RestEntityBody<CommentResBody> {

  private static final long serialVersionUID = -4955881323202878008L;
  public static String[] IGNORE_PROPERTIES = {
      "id",
      "relativeUser",
      "relativePost",
      "relativeEvent"
  };

  @PrePersist
  public void prePersist() {
    if (StringUtils.isNotEmpty(this.content)) {
      this.content = HtmlUtils.convertLineSeparatorToBrTag(this.content);
    }
  }

  @PreUpdate
  public void preUpdate() {
    if (StringUtils.isNotEmpty(this.content)) {
      this.content = HtmlUtils.convertLineSeparatorToBrTag(this.content);
    }
  }

  @Id
  @GeneratedValue
  @Column(unique = true)
  private Long id;

  @Lob
  @Column(columnDefinition = "TEXT", length = 65535)
  private String content;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "idUser", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_User_For_Comment"))
  private User relativeUser;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "idPost", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_Post_For_Comment"))
  private Post relativePost = new Post();

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "idEvent", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_Event_For_Comment"))
  private Post relativeEvent = new Post();

  @Override
  public void delete() {
    if (this.relativeUser != null) {
      this.relativeUser = null;
    }
    if (this.relativePost != null) {
      this.relativePost = null;
    }
    if (this.relativeEvent != null) {
      this.relativeEvent = null;
    }
  }

  @Override
  public void lazy() {
    if (this.relativeUser != null) {
      this.relativeUser.getId();
    }
    if (this.relativePost != null) {
      this.relativePost.getId();
    }
    if (this.relativeEvent != null) {
      this.relativeEvent.getId();
    }
  }

  @Override
  public CommentResBody toBody(Locale locale) {
    return null;
  }
}
