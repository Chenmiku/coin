package kr.co.queenssmile.core.domain.board.post.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.domain.AbstractEntityCategory;
import kr.co.queenssmile.core.domain.RestEntityBody;
import kr.co.queenssmile.core.domain.board.post.Post;
import kr.co.queenssmile.core.model.resbody.board.CategoryResBody;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Entity(name = "PostCategory")
@Getter
@Setter
@ToString(exclude = {"posts"})
@NoArgsConstructor
public class PCategory extends AbstractEntityCategory<Long> implements RestEntityBody<CategoryResBody> {

  private static final long serialVersionUID = -714603746092485437L;

  public static String[] IGNORE_PROPERTIES = {
      "id",
      "type",
      "orderAscending",
      "posts"
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

  @NonNull
  @Column(columnDefinition = "TINYINT(1) default 0", updatable = false)
  private Post.Type type; // 수정 못함

  @JsonIgnore
  @OrderBy("regDate DESC")
  @ManyToMany(mappedBy = "categories")
  private List<Post> posts = new ArrayList<>();

  @Override
  public void delete() {

    if (this.posts != null) {
      this.posts.forEach(post -> {
        post.getCategories().removeIf(category -> Objects.equals(category.getId(), this.id));
      });
    }
  }

  @Override
  public void lazy() {
    if (this.posts != null) {
      this.posts.size();
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
