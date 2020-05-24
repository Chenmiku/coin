package kr.co.queenssmile.core.domain.board.qna.category;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

@NoArgsConstructor(staticName = "getInstance")
public class QnaCategoryPredicate {

  private static final QQnaCategory Q_QNA_CATEGORY = QQnaCategory.qnaCategory;

  private BooleanBuilder builder = new BooleanBuilder();

  public Predicate values() {
    return builder.getValue() == null ? builder.and(Q_QNA_CATEGORY.id.isNotNull()) : builder.getValue();
  }

  public QnaCategoryPredicate startDate(final LocalDateTime startDate) {

    if (startDate != null) {
      builder.and(Q_QNA_CATEGORY.createdDate.goe(startDate));
    }
    return this;
  }

  public QnaCategoryPredicate endDate(final LocalDateTime endDate) {

    if (endDate != null) {
      builder.and(Q_QNA_CATEGORY.createdDate.loe(endDate));
    }
    return this;
  }

  public QnaCategoryPredicate search(String value) {

    if (!StringUtils.isEmpty(value)) {
      value = value.trim();

      builder.and(Q_QNA_CATEGORY.name.textZhTw.containsIgnoreCase(value)
          .or(Q_QNA_CATEGORY.name.textZhCn.containsIgnoreCase(value))
          .or(Q_QNA_CATEGORY.name.textKoKr.containsIgnoreCase(value))
          .or(Q_QNA_CATEGORY.name.textJaJp.containsIgnoreCase(value))
          .or(Q_QNA_CATEGORY.name.textEnUs.containsIgnoreCase(value)));
    }
    return this;
  }

  public QnaCategoryPredicate active(final Boolean isActive) {

    if (isActive != null) {
      builder.and(Q_QNA_CATEGORY.active.eq(isActive));
    }
    return this;
  }

  public QnaCategoryPredicate locale(Locale locale, final Locale defaultLocale) {

    if (!Objects.equals(locale, Locale.KOREA)
        && !Objects.equals(locale, Locale.US)
        && !Objects.equals(locale, Locale.CHINA)
        && !Objects.equals(locale, Locale.TAIWAN)
        && !Objects.equals(locale, Locale.JAPAN)) {
      locale = defaultLocale;
    }

    if (Objects.equals(locale, Locale.KOREA)) {
      builder.and(Q_QNA_CATEGORY.internationalMode.koKr.eq(true));
    } else if (Objects.equals(locale, Locale.US)) {
      builder.and(Q_QNA_CATEGORY.internationalMode.enUs.eq(true));
    } else if (Objects.equals(locale, Locale.CHINA)) {
      builder.and(Q_QNA_CATEGORY.internationalMode.zhCn.eq(true));
    } else if (Objects.equals(locale, Locale.TAIWAN)) {
      builder.and(Q_QNA_CATEGORY.internationalMode.zhTw.eq(true));
    } else if (Objects.equals(locale, Locale.JAPAN)) {
      builder.and(Q_QNA_CATEGORY.internationalMode.jaJp.eq(true));
    }
    return this;
  }
}
