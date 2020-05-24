package kr.co.queenssmile.core.domain.board.faq;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.domain.AbstractEntityInternational;
import kr.co.queenssmile.core.domain.International.InterText;
import kr.co.queenssmile.core.domain.RestEntityBody;
import kr.co.queenssmile.core.domain.board.faq.category.FaqCategory;
import kr.co.queenssmile.core.model.resbody.board.FaqResBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * FAQ 테이블
 */
@Entity(name = "Faq")
@Getter
@Setter
@ToString(exclude = {"categories"})
public class Faq extends AbstractEntityInternational<Long> implements RestEntityBody<FaqResBody> {

  private static final long serialVersionUID = -4010020672252192170L;

  public static String[] IGNORE_PROPERTIES = {
      "id",
      "orderAscending"
  };

  @PrePersist
  public void prePersist() {

  }

  @PreUpdate
  public void PreUpdate() {

  }

  @Id
  @GeneratedValue
  @Column(unique = true)
  private Long id;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "textKoKr", column = @Column(name = "questionKoKr", length = 255)),
      @AttributeOverride(name = "textEnUs", column = @Column(name = "questionEnUs", length = 255)),
      @AttributeOverride(name = "textJaJp", column = @Column(name = "questionJaJp", length = 255)),
      @AttributeOverride(name = "textZhCn", column = @Column(name = "questionZhCn", length = 255)),
      @AttributeOverride(name = "textZhTw", column = @Column(name = "questionZhTw", length = 255))
  })
  private InterText question; // 질문

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "textKoKr", column = @Column(name = "answerKoKr", columnDefinition = "TEXT")),
      @AttributeOverride(name = "textEnUs", column = @Column(name = "answerEnUs", columnDefinition = "TEXT")),
      @AttributeOverride(name = "textJaJp", column = @Column(name = "answerJaJp", columnDefinition = "TEXT")),
      @AttributeOverride(name = "textZhCn", column = @Column(name = "answerZhCn", columnDefinition = "TEXT")),
      @AttributeOverride(name = "textZhTw", column = @Column(name = "answerZhTw", columnDefinition = "TEXT"))
  })
  private InterText answer; // 내용

  @Column(columnDefinition = "BIT(1) default 1")
  private boolean active; // 활성/비활성

  @JsonIgnore
  @OrderBy("orderAscending ASC")
  @ManyToMany
  @JoinTable(name = "FaqToCategory",
      joinColumns = @JoinColumn(name = "idFaq", nullable = false, foreignKey = @ForeignKey(name = "FK_Faq_For_Faq_To_Cate")),
      inverseJoinColumns = @JoinColumn(name = "idCategory", nullable = false, foreignKey = @ForeignKey(name = "FK_Category_For_Faq_To_Cate"))
  )
  private List<FaqCategory> categories = new ArrayList<>();

  @Column(columnDefinition = "BIGINT(20) default 0")
  @Getter
  @Setter
  private long orderAscending; // 순서, 오름차순

  public void changeOrder(Faq target) {

    Faq source = this;

    long sourceOrderAscending = source.getOrderAscending();
    long targetOrderAscending = target.getOrderAscending();

    target.setOrderAscending(sourceOrderAscending);
    source.setOrderAscending(targetOrderAscending);
  }

  @Override
  public void setLocale(Locale locale) {
    super.setLocale(locale);
    this.question.setLocale(this.locale);
    this.answer.setLocale(this.locale);
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
  }

  @Override
  public FaqResBody toBody(Locale locale) {
    return null;
  }
}
