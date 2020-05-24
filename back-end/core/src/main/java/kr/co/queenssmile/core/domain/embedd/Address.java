package kr.co.queenssmile.core.domain.embedd;

import kr.co.queenssmile.core.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Setter
@Getter
@Embeddable
public class Address implements java.io.Serializable {

  private static final long serialVersionUID = -624670171718146604L;


  // 주소
  @Column(length = 10)
  private String postalCode; // 우편번호

  @Column(length = 100)
  private String address1; // 기본 주소

  @Column(length = 100)
  private String address2; // 상세 주소

  @Embedded
  private GPS gps; // 위도/경도


  @Override
  public String toString() {
    return "(" + postalCode + ") " + address1 + " " + address2;
  }

  public String getValue() {
    return (StringUtils.isNotEmpty(this.address1) ? this.address1 : "") + (StringUtils.isNotEmpty(this.address2) ? (" " + this.address2) : "");
  }
}
