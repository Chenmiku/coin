package kr.co.queenssmile.core.domain.board.post.category;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.co.queenssmile.core.domain.board.post.Post;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

@NoArgsConstructor(staticName = "getInstance")
public class PCategoryPredicate {

  private static final QPCategory QP_CATEGORY = QPCategory.pCategory;

  private BooleanBuilder builder = new BooleanBuilder();

  public Predicate values() {
    return builder.getValue() == null ? builder.and(QP_CATEGORY.id.isNotNull()) : builder.getValue();
  }

  public PCategoryPredicate startDate(final LocalDateTime startDate) {

    if (startDate != null) {
      builder.and(QP_CATEGORY.createdDate.goe(startDate));
    }
    return this;
  }

  public PCategoryPredicate endDate(final LocalDateTime endDate) {

    if (endDate != null) {
      builder.and(QP_CATEGORY.createdDate.loe(endDate));
    }
    return this;
  }

  public PCategoryPredicate search(String value) {

    if (!StringUtils.isEmpty(value)) {
      value = value.trim();

      builder.and(QP_CATEGORY.name.textZhTw.containsIgnoreCase(value)
          .or(QP_CATEGORY.name.textZhCn.containsIgnoreCase(value))
          .or(QP_CATEGORY.name.textKoKr.containsIgnoreCase(value))
          .or(QP_CATEGORY.name.textJaJp.containsIgnoreCase(value))
          .or(QP_CATEGORY.name.textEnUs.containsIgnoreCase(value)));
    }
    return this;
  }

  public PCategoryPredicate name(String name) {

    if (!StringUtils.isEmpty(name)) {
      name = name.trim();
      builder.and(QP_CATEGORY.name.textZhTw.eq(name)
          .or(QP_CATEGORY.name.textZhCn.eq(name))
          .or(QP_CATEGORY.name.textKoKr.eq(name))
          .or(QP_CATEGORY.name.textJaJp.eq(name))
          .or(QP_CATEGORY.name.textEnUs.eq(name)));
    }

    return this;
  }

  public PCategoryPredicate type(final Post.Type type) {

    if (type != null) {
      builder.and(QP_CATEGORY.type.eq(type));
    }
    return this;
  }

  public PCategoryPredicate active(final Boolean isActive) {

    if (isActive != null) {
      builder.and(QP_CATEGORY.active.eq(isActive));
    }
    return this;
  }

  public PCategoryPredicate locale(Locale locale, final Locale defaultLocale) {

    if (!Objects.equals(locale, Locale.KOREA)
        && !Objects.equals(locale, Locale.US)
        && !Objects.equals(locale, Locale.CHINA)
        && !Objects.equals(locale, Locale.TAIWAN)
        && !Objects.equals(locale, Locale.JAPAN)) {
      locale = defaultLocale;
    }

    if (Objects.equals(locale, Locale.KOREA)) {
      builder.and(QP_CATEGORY.internationalMode.koKr.eq(true));
    } else if (Objects.equals(locale, Locale.US)) {
      builder.and(QP_CATEGORY.internationalMode.enUs.eq(true));
    } else if (Objects.equals(locale, Locale.CHINA)) {
      builder.and(QP_CATEGORY.internationalMode.zhCn.eq(true));
    } else if (Objects.equals(locale, Locale.TAIWAN)) {
      builder.and(QP_CATEGORY.internationalMode.zhTw.eq(true));
    } else if (Objects.equals(locale, Locale.JAPAN)) {
      builder.and(QP_CATEGORY.internationalMode.jaJp.eq(true));
    }
    return this;
  }
}
