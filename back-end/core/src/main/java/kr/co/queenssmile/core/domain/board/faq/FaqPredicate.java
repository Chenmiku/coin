package kr.co.queenssmile.core.domain.board.faq;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

@NoArgsConstructor(staticName = "getInstance")
public class FaqPredicate {

  private static final QFaq Q_FAQ = QFaq.faq;

  private BooleanBuilder builder = new BooleanBuilder();

  public Predicate values() {
    return builder.getValue() == null ? builder.and(Q_FAQ.id.isNotNull()) : builder.getValue();
  }

  public FaqPredicate startDate(final LocalDateTime startDate) {

    if (startDate != null) {
      builder.and(Q_FAQ.createdDate.goe(startDate));
    }
    return this;
  }

  public FaqPredicate endDate(final LocalDateTime endDate) {

    if (endDate != null) {
      builder.and(Q_FAQ.createdDate.loe(endDate));
    }
    return this;
  }

  public FaqPredicate search(String value) {

    if (!StringUtils.isEmpty(value)) {
      value = value.trim();

      builder.and(Q_FAQ.question.textEnUs.containsIgnoreCase(value)
          .or(Q_FAQ.question.textJaJp.containsIgnoreCase(value))
          .or(Q_FAQ.question.textKoKr.containsIgnoreCase(value))
          .or(Q_FAQ.question.textZhCn.containsIgnoreCase(value))
          .or(Q_FAQ.question.textZhTw.containsIgnoreCase(value))
          .or(Q_FAQ.answer.textEnUs.containsIgnoreCase(value))
          .or(Q_FAQ.answer.textJaJp.containsIgnoreCase(value))
          .or(Q_FAQ.answer.textKoKr.containsIgnoreCase(value))
          .or(Q_FAQ.answer.textZhCn.containsIgnoreCase(value))
          .or(Q_FAQ.answer.textZhTw.containsIgnoreCase(value)));
    }
    return this;
  }

  public FaqPredicate id(final Long id) {

    if (id != null && id != 0L) {
      builder.and(Q_FAQ.id.eq(id));
    }
    return this;
  }

  public FaqPredicate active(final Boolean isActive) {

    if (isActive != null) {
      builder.and(Q_FAQ.active.eq(isActive));
    }
    return this;
  }

  public FaqPredicate category(final Long idCategory) {

    if (idCategory != null && idCategory != 0L) {
      builder.and(Q_FAQ.categories.any().id.eq(idCategory));
    }
    return this;
  }

  public FaqPredicate locale(Locale locale, final Locale defaultLocale) {

    if (!Objects.equals(locale, Locale.KOREA)
        && !Objects.equals(locale, Locale.US)
        && !Objects.equals(locale, Locale.CHINA)
        && !Objects.equals(locale, Locale.TAIWAN)
        && !Objects.equals(locale, Locale.JAPAN)) {
      locale = defaultLocale;
    }

    if (Objects.equals(locale, Locale.KOREA)) {
      builder.and(Q_FAQ.internationalMode.koKr.eq(true));
    } else if (Objects.equals(locale, Locale.US)) {
      builder.and(Q_FAQ.internationalMode.enUs.eq(true));
    } else if (Objects.equals(locale, Locale.CHINA)) {
      builder.and(Q_FAQ.internationalMode.zhCn.eq(true));
    } else if (Objects.equals(locale, Locale.TAIWAN)) {
      builder.and(Q_FAQ.internationalMode.zhTw.eq(true));
    } else if (Objects.equals(locale, Locale.JAPAN)) {
      builder.and(Q_FAQ.internationalMode.jaJp.eq(true));
    }

    return this;
  }
}