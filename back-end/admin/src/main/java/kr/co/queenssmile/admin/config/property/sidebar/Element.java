package kr.co.queenssmile.admin.config.property.sidebar;

import kr.co.queenssmile.core.domain.user.Authority;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Element implements java.io.Serializable {

  private static final long serialVersionUID = -7569225699046750373L;

  private String type; // ONE, MULTI

  private String title;

  private String description;

  private String label; // label-default, label-primary, label-success, label-info, label-warning, label-danger, label-green, label-purple, label-pink, label-green, label-inverse

  private String count;

  // ONE
  private String url;

  private List<Authority.Role> roles;

  private String icon;

  // MULTI
  private String id;

  private SubSection section;
}
