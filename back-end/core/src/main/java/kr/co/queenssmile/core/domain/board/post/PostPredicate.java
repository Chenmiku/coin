package kr.co.queenssmile.core.domain.board.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

@NoArgsConstructor(staticName = "getInstance")
public class PostPredicate {

  private static final QPost Q_POST = QPost.post;

  private BooleanBuilder builder = new BooleanBuilder();

  public Predicate values() {
    return builder.getValue() == null ? builder.and(Q_POST.id.isNotNull()) : builder.getValue();
  }

  public PostPredicate startDate(final LocalDateTime startDate) {

    if (startDate != null) {
      builder.and(Q_POST.createdDate.goe(startDate));
    }
    return this;
  }

  public PostPredicate endDate(final LocalDateTime endDate) {

    if (endDate != null) {
      builder.and(Q_POST.createdDate.loe(endDate));
    }
    return this;
  }

  public PostPredicate search(String value) {

    if (!StringUtils.isEmpty(value)) {
      value = value.trim();

      builder.and(Q_POST.title.textEnUs.containsIgnoreCase(value)
          .or(Q_POST.title.textJaJp.containsIgnoreCase(value))
          .or(Q_POST.title.textKoKr.containsIgnoreCase(value))
          .or(Q_POST.title.textZhCn.containsIgnoreCase(value))
          .or(Q_POST.title.textZhTw.containsIgnoreCase(value))
          .or(Q_POST.content.textEnUs.containsIgnoreCase(value))
          .or(Q_POST.content.textJaJp.containsIgnoreCase(value))
          .or(Q_POST.content.textKoKr.containsIgnoreCase(value))
          .or(Q_POST.content.textZhCn.containsIgnoreCase(value))
          .or(Q_POST.content.textZhTw.containsIgnoreCase(value)));
    }
    return this;
  }

  public PostPredicate type(final Post.Type type) {

    if (type != null) {
      builder.and(Q_POST.type.eq(type));
    }
    return this;
  }

  public PostPredicate category(final Long idCategory) {

    if (idCategory != null && idCategory != 0L) {
      builder.and(Q_POST.categories.any().id.eq(idCategory));
    }
    return this;
  }

  public PostPredicate active(final Boolean isActive) {

    if (isActive != null) {
      builder.and(Q_POST.active.eq(isActive));
    }
    return this;
  }

  public PostPredicate locale(Locale locale, final Locale defaultLocale) {

    if (!Objects.equals(locale, Locale.KOREA)
        && !Objects.equals(locale, Locale.US)
        && !Objects.equals(locale, Locale.CHINA)
        && !Objects.equals(locale, Locale.TAIWAN)
        && !Objects.equals(locale, Locale.JAPAN)) {
      locale = defaultLocale;
    }

    if (Objects.equals(locale, Locale.KOREA)) {
      builder.and(Q_POST.internationalMode.koKr.eq(true));
    } else if (Objects.equals(locale, Locale.US)) {
      builder.and(Q_POST.internationalMode.enUs.eq(true));
    } else if (Objects.equals(locale, Locale.CHINA)) {
      builder.and(Q_POST.internationalMode.zhCn.eq(true));
    } else if (Objects.equals(locale, Locale.TAIWAN)) {
      builder.and(Q_POST.internationalMode.zhTw.eq(true));
    } else if (Objects.equals(locale, Locale.JAPAN)) {
      builder.and(Q_POST.internationalMode.jaJp.eq(true));
    }

    return this;
  }
}
