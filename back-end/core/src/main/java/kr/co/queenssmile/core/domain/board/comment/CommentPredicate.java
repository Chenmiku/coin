package kr.co.queenssmile.core.domain.board.comment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@NoArgsConstructor(staticName = "getInstance")
public class CommentPredicate {

  private static final QComment Q_COMMENT = QComment.comment;

  private BooleanBuilder builder = new BooleanBuilder();

  public Predicate values() {
    return builder.getValue() == null ? builder.and(Q_COMMENT.id.isNotNull()) : builder.getValue();
  }

  public CommentPredicate startDate(final LocalDateTime startDate) {

    if (startDate != null) {
      builder.and(Q_COMMENT.createdDate.goe(startDate));
    }
    return this;
  }

  public CommentPredicate endDate(final LocalDateTime endDate) {

    if (endDate != null) {
      builder.and(Q_COMMENT.createdDate.loe(endDate));
    }
    return this;
  }

  public CommentPredicate search(String value) {

    if (!StringUtils.isEmpty(value)) {
      value = value.trim();

      builder.and(Q_COMMENT.content.containsIgnoreCase(value));
    }
    return this;
  }


  public CommentPredicate hasEvent() {
    builder.and(Q_COMMENT.relativeEvent.isNotNull());
    return this;
  }

  public CommentPredicate hasPost() {
    builder.and(Q_COMMENT.relativePost.isNotNull());
    return this;
  }
}
