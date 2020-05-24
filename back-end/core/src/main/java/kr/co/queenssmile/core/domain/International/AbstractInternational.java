package kr.co.queenssmile.core.domain.International;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) // 모든 필드에 json 적용
@JsonInclude(value = JsonInclude.Include.ALWAYS) // 모든 데이터에 json 적용
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractInternational implements java.io.Serializable {

  private static final long serialVersionUID = 2675739473613443087L;

  public abstract String getTextKoKr(); // 국문

  public abstract String getTextEnUs(); // 영문

  public abstract String getTextJaJp(); // 일문

  public abstract String getTextZhCn(); // 중문(간체)

  public abstract String getTextZhTw(); // 중문(번체)

  @Setter
  @Getter
  @JsonIgnore
  @Transient
  private java.util.Locale locale; // 언어

  public abstract String getValue();

  public abstract String toString();
}