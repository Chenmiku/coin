package kr.co.queenssmile.admin.config.property.sidebar;

import kr.co.queenssmile.core.domain.user.Authority;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SubSection implements java.io.Serializable {

  private static final long serialVersionUID = -886486761144808783L;

  private String title;

  private List<Authority.Role> roles;

  private List<Element> elements;
}
