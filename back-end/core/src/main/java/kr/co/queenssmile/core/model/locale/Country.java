package kr.co.queenssmile.core.model.locale;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country implements java.io.Serializable {

  private static final long serialVersionUID = 6563127842660573382L;

  private String code;
  private String displayName;
}
