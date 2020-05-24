package kr.co.queenssmile.core.domain.board.faq.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.domain.AbstractEntityCategory;
import kr.co.queenssmile.core.domain.RestEntityBody;
import kr.co.queenssmile.core.domain.board.faq.Faq;
import kr.co.queenssmile.core.model.resbody.board.CategoryResBody;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Entity(name = "FaqCategory")
@Getter
@Setter
@ToString(exclude = {"faqs"})
@NoArgsConstructor
public class FaqCategory extends AbstractEntityCategory<Long> implements RestEntityBody<CategoryResBody> {

  private static final long serialVersionUID = 3863266271477643490L;

  public static String[] IGNORE_PROPERTIES = {
      "id",
      "orderAscending",
      "faqs"
  };

  @PrePersist
  public void prePersist() {

  }

  @PreUpdate
  public void PreUpdate() {

  }

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String image; // icon 이미지

  @JsonIgnore
  @OrderBy("orderAscending ASC")
  @ManyToMany(mappedBy = "categories")
  private List<Faq> faqs = new ArrayList<>();

  @Override
  public void delete() {

    if (this.faqs != null) {
      this.faqs.forEach(faq -> {
        faq.getCategories().removeIf(category -> Objects.equals(category.getId(), this.id));
      });
    }
  }

  @Override
  public void lazy() {
    if (this.faqs != null) {
      this.faqs.size();
    }
  }


  @Override
  public CategoryResBody toBody(Locale locale) {
    this.setLocale(locale);

    return CategoryResBody.builder()
        .id(this.id)
        .name(this.name.getValue())
        .image(this.image)
        .build();
  }
}
