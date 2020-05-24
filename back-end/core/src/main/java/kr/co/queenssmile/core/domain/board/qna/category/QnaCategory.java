package kr.co.queenssmile.core.domain.board.qna.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.domain.AbstractEntityCategory;
import kr.co.queenssmile.core.domain.RestEntityBody;
import kr.co.queenssmile.core.domain.board.qna.Qna;
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

@Entity(name = "QnaCategory")
@Getter
@Setter
@ToString(exclude = {"qnas"})
@NoArgsConstructor
public class QnaCategory extends AbstractEntityCategory<Long> implements RestEntityBody<CategoryResBody> {

  private static final long serialVersionUID = -7827384715707862993L;

  public static String[] IGNORE_PROPERTIES = {
      "id",
      "orderAscending",
      "qnas"
  };

  @Id
  @GeneratedValue
  @Column(unique = true)
  private Long id;

  @JsonIgnore
  @OrderBy("orderAscending ASC")
  @ManyToMany(mappedBy = "categories")
  private List<Qna> qnas = new ArrayList<>();

  @Override
  public void delete() {
    if (this.qnas != null) {
      this.qnas.forEach(qna -> {
        qna.getCategories().removeIf(category -> Objects.equals(category.getId(), this.id));
      });
    }
  }

  @Override
  public void lazy() {
    if (this.qnas != null) {
      this.qnas.size();
    }
  }

  @Override
  public CategoryResBody toBody(Locale locale) {
    this.setLocale(locale);

    return CategoryResBody.builder()
        .id(this.id)
        .name(this.name.getValue())
        .build();
  }
}
