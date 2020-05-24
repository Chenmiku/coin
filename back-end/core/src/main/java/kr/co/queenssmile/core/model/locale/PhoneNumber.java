package kr.co.queenssmile.core.model.locale;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumber implements java.io.Serializable {

  private static final long serialVersionUID = -8786072652430332567L;

  private int code;
  private String displayName;
}
