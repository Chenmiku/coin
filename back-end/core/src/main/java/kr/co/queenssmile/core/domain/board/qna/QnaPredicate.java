package kr.co.queenssmile.core.domain.board.qna;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@NoArgsConstructor(staticName = "getInstance")
public class QnaPredicate {

  private static final QQna Q_QNA = QQna.qna;

  private BooleanBuilder builder = new BooleanBuilder();

  public Predicate values() {
    return builder.getValue() == null ? builder.and(Q_QNA.id.isNotNull()) : builder.getValue();
  }

  public QnaPredicate startDate(final LocalDateTime startDate) {

    if (startDate != null) {
      builder.and(Q_QNA.createdDate.goe(startDate));
    }
    return this;
  }

  public QnaPredicate endDate(final LocalDateTime endDate) {

    if (endDate != null) {
      builder.and(Q_QNA.createdDate.loe(endDate));
    }
    return this;
  }

  public QnaPredicate search(String value) {

    if (!StringUtils.isEmpty(value)) {
      value = value.trim();

      builder.and(Q_QNA.title.containsIgnoreCase(value)
          .or(Q_QNA.content.containsIgnoreCase(value)));
    }
    return this;
  }

  public QnaPredicate category(final Long idCategory) {

    if (idCategory != null && idCategory != 0L) {
      builder.and(Q_QNA.categories.any().id.eq(idCategory));
    }
    return this;
  }

  public QnaPredicate active(final Boolean isActive) {

    if (isActive != null) {
      builder.and(Q_QNA.active.eq(isActive));
    }
    return this;
  }

  public QnaPredicate email(final String email) {

    if (!StringUtils.isEmpty(email)) {
      builder.and(Q_QNA.relativeUser.email.eq(email));
    }
    return this;
  }
}
