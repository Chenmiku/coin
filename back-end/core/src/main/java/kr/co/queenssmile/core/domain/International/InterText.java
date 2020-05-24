package kr.co.queenssmile.core.domain.International;

import kr.co.queenssmile.core.utils.LocaleUtils;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Embeddable
public class InterText extends AbstractInternational {

  private static final long serialVersionUID = -3980715491104883923L;

  @Column
  private String textKoKr; // 국문

  @Column
  private String textEnUs; // 영문

  @Column
  private String textZhCn; // 중문(간체)

  @Column
  private String textZhTw; // 중문(번체)

  @Column
  private String textJaJp; // 일문

  @Override
  public String getValue() {
    return LocaleUtils.toValue(this);
  }

  public boolean isEmpty() {
    return StringUtils.isEmpty(this.getValue());
  }
}
