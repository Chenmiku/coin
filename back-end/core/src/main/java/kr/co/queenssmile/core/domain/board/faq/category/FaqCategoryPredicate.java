package kr.co.queenssmile.core.domain.board.faq.category;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

@NoArgsConstructor(staticName = "getInstance")
public class FaqCategoryPredicate {

  private static final QFaqCategory Q_FAQ_CATEGORY = QFaqCategory.faqCategory;

  private BooleanBuilder builder = new BooleanBuilder();

  public Predicate values() {
    return builder.getValue() == null ? builder.and(Q_FAQ_CATEGORY.id.isNotNull()) : builder.getValue();
  }

  public FaqCategoryPredicate startDate(final LocalDateTime startDate) {

    if (startDate != null) {
      builder.and(Q_FAQ_CATEGORY.createdDate.goe(startDate));
    }
    return this;
  }

  public FaqCategoryPredicate endDate(final LocalDateTime endDate) {

    if (endDate != null) {
      builder.and(Q_FAQ_CATEGORY.createdDate.loe(endDate));
    }
    return this;
  }

  public FaqCategoryPredicate search(String value) {

    if (!StringUtils.isEmpty(value)) {
      value = value.trim();

      builder.and(Q_FAQ_CATEGORY.name.textZhTw.containsIgnoreCase(value)
          .or(Q_FAQ_CATEGORY.name.textZhCn.containsIgnoreCase(value))
          .or(Q_FAQ_CATEGORY.name.textKoKr.containsIgnoreCase(value))
          .or(Q_FAQ_CATEGORY.name.textJaJp.containsIgnoreCase(value))
          .or(Q_FAQ_CATEGORY.name.textEnUs.containsIgnoreCase(value)));
    }
    return this;
  }


  public FaqCategoryPredicate name(String name) {

    if (!StringUtils.isEmpty(name)) {
      name = name.trim();

      builder.and(Q_FAQ_CATEGORY.name.textZhTw.containsIgnoreCase(name)
          .or(Q_FAQ_CATEGORY.name.textZhCn.containsIgnoreCase(name))
          .or(Q_FAQ_CATEGORY.name.textKoKr.containsIgnoreCase(name))
          .or(Q_FAQ_CATEGORY.name.textJaJp.containsIgnoreCase(name))
          .or(Q_FAQ_CATEGORY.name.textEnUs.containsIgnoreCase(name)));
    }
    return this;
  }

  public FaqCategoryPredicate active(final Boolean isActive) {

    if (isActive != null) {
      builder.and(Q_FAQ_CATEGORY.active.eq(isActive));
    }
    return this;
  }

  public FaqCategoryPredicate locale(Locale locale, final Locale defaultLocale) {

    if (!Objects.equals(locale, Locale.KOREA)
        && !Objects.equals(locale, Locale.US)
        && !Objects.equals(locale, Locale.CHINA)
        && !Objects.equals(locale, Locale.TAIWAN)
        && !Objects.equals(locale, Locale.JAPAN)) {
      locale = defaultLocale;
    }

    if (Objects.equals(locale, Locale.KOREA)) {
      builder.and(Q_FAQ_CATEGORY.internationalMode.koKr.eq(true));
    } else if (Objects.equals(locale, Locale.US)) {
      builder.and(Q_FAQ_CATEGORY.internationalMode.enUs.eq(true));
    } else if (Objects.equals(locale, Locale.CHINA)) {
      builder.and(Q_FAQ_CATEGORY.internationalMode.zhCn.eq(true));
    } else if (Objects.equals(locale, Locale.TAIWAN)) {
      builder.and(Q_FAQ_CATEGORY.internationalMode.zhTw.eq(true));
    } else if (Objects.equals(locale, Locale.JAPAN)) {
      builder.and(Q_FAQ_CATEGORY.internationalMode.jaJp.eq(true));
    }
    return this;
  }
}
