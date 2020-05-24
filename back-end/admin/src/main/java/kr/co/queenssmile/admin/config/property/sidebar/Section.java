package kr.co.queenssmile.admin.config.property.sidebar;

import kr.co.queenssmile.core.domain.user.Authority;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Section implements java.io.Serializable {

  private static final long serialVersionUID = 7899998051900140182L;

  private String title;

  private List<Authority.Role> roles;

  private List<Element> elements;
}
