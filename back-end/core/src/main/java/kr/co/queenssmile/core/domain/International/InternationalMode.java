package kr.co.queenssmile.core.domain.International;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;

@Setter
@Getter
@ToString
@Embeddable
public class InternationalMode implements java.io.Serializable {

  private static final long serialVersionUID = -2813294660981338007L;

  public InternationalMode() {
    this.koKr = true;
    this.enUs = true;
    this.zhCn = true;
    this.zhTw = true;
    this.jaJp = true;
  }

  public void toFalseMode() {
    this.koKr = false;
    this.enUs = false;
    this.zhCn = false;
    this.zhTw = false;
    this.jaJp = false;
  }

  @PrePersist
  public void prePersist() {
  }

  @Column(columnDefinition = "BIT(1) default 1")
  private boolean koKr;

  @Column(columnDefinition = "BIT(1) default 1")
  private boolean enUs;

  @Column(columnDefinition = "BIT(1) default 1")
  private boolean zhCn;

  @Column(columnDefinition = "BIT(1) default 1")
  private boolean zhTw;

  @Column(columnDefinition = "BIT(1) default 1")
  private boolean jaJp;

}
