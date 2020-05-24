package kr.co.queenssmile.core.domain.board.qna;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.domain.AbstractEntity;
import kr.co.queenssmile.core.domain.RestEntityBody;
import kr.co.queenssmile.core.domain.board.qna.category.QnaCategory;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.model.resbody.board.QnaResBody;
import kr.co.queenssmile.core.utils.HtmlUtils;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 1:1 문의하기
 */
@Entity(name = "QuestionsAndAnswers")
@Getter
@Setter
@ToString(exclude = {
    "relativeUser",
    "categories"
})
public class Qna extends AbstractEntity<Long> implements RestEntityBody<QnaResBody> {

  private static final long serialVersionUID = 6328325247234411688L;

  public static String[] IGNORE_PROPERTIES = {
      "id",
      "relativeUser",
      "qnaNoMember"};

  @PrePersist
  public void prePersist() {

    if (StringUtils.isNotEmpty(this.content)) {
      this.content = HtmlUtils.convertLineSeparatorToBrTag(this.content);
    }
  }

  @PreUpdate
  public void preUpdate() {

    if (StringUtils.isNotEmpty(this.content)) {
      this.content = HtmlUtils.convertLineSeparatorToBrTag(this.content);
    }
  }

  @Id
  @GeneratedValue
  @Column(unique = true)
  private Long id;

  @Column(length = 100)
  private String title;

  @Lob
  @Column(columnDefinition = "TEXT", length = 65535)
  private String content;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "idUser", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_User_For_Qna")) // Column name, 참조하는 ID(pk) name
  private User relativeUser; // 작성자


  @Column(name = "qMember", columnDefinition = "BIT(1) default 0")
  private boolean member; // 회원 여부

  @Column(columnDefinition = "BIT(1) default 1")
  private boolean active; // 활성/비활성

  @Embedded
  private Answer answer;

  @Embedded
  private QnaNoMember qnaNoMember;

  @JsonIgnore
  @OrderBy("orderAscending ASC")
  @ManyToMany
  @JoinTable(name = "QnaToCategory",
      joinColumns = @JoinColumn(name = "idQna", nullable = false, foreignKey = @ForeignKey(name = "FK_Qna_For_Qna_To_Cate")),
      inverseJoinColumns = @JoinColumn(name = "idCategory", nullable = false, foreignKey = @ForeignKey(name = "FK_Category_For_Qna_To_Cate"))
  )
  private List<QnaCategory> categories = new ArrayList<>(); // 카테고리

  @Transient
  private Long idCategory;

  public void setLocale(Locale locale) {
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
    if (this.relativeUser != null) {
      this.relativeUser.getId();
    }
    if (this.categories != null) {
      this.categories.size();
    }
  }

  @Override
  public QnaResBody toBody(Locale locale) {
    return null;
  }
}
