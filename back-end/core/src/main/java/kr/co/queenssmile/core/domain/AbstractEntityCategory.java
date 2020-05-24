package kr.co.queenssmile.core.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.queenssmile.core.domain.International.InterText;
import kr.co.queenssmile.core.utils.LocaleUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@MappedSuperclass
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) // 모든 필드에 json 적용
@JsonInclude(value = JsonInclude.Include.ALWAYS) // 모든 데이터에 json 적용
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntityCategory<K extends java.io.Serializable> extends AbstractEntityInternational<K> {

  private static final long serialVersionUID = 238442337429742354L;

  @Getter
  @Setter
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "textKoKr", column = @Column(name = "nameKoKr")),
      @AttributeOverride(name = "textEnUs", column = @Column(name = "nameEnUs")),
      @AttributeOverride(name = "textJaJp", column = @Column(name = "nameJaJp")),
      @AttributeOverride(name = "textZhCn", column = @Column(name = "nameZhCn")),
      @AttributeOverride(name = "textZhTw", column = @Column(name = "nameZhTw"))
  })
  protected InterText name; // 카테고리 명

  @Getter
  @Setter
  @Column(columnDefinition = "BIT(1) default 1")
  protected boolean active; // 활성/비활성

  @JsonIgnore
  @Transient
  @Getter
  @Setter
  protected boolean checked;

  @Column(columnDefinition = "BIGINT(20) default 0")
  @Getter
  @Setter
  protected long orderAscending; // 순서, 오름차순

  public void changeOrder(AbstractEntityCategory target) {

    AbstractEntityCategory source = this;

    long sourceOrderAscending = source.getOrderAscending();
    long targetOrderAscending = target.getOrderAscending();

    target.setOrderAscending(sourceOrderAscending);
    source.setOrderAscending(targetOrderAscending);
  }

  @Override
  public void setLocale(java.util.Locale locale) {
    if (locale == null) {
      locale = LocaleUtils.init();
    }
    super.setLocale(locale);
    if (this.name != null)
      this.name.setLocale(this.locale);
  }

}