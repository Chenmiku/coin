package kr.co.queenssmile.core.config.security;


import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

@Slf4j
@Getter
@ToString
public class CurrentUser extends org.springframework.security.core.userdetails.User implements java.io.Serializable {

  private static final long serialVersionUID = 203752717146185232L;

  private Long id;
  private String email;
  private String fullName;
  private String mobile;
  private String image;

  public CurrentUser(Long id,
                     String email,
                     String fullName,
                     String mobile,
                     String image,
                     String password,
                     boolean enabled,
                     boolean accountNonExpired,
                     boolean credentialsNonExpired,
                     boolean accountNonLocked,
                     java.util.Set<GrantedAuthority> authorities) {

    super(email, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.id = id;
    this.email = email;
    this.fullName = fullName;
    this.mobile = mobile;
    this.image = image;
  }

  public boolean isSuper() {
    if (getAuthorities() != null) {
      java.util.List<GrantedAuthority> list = Lists.newArrayList(getAuthorities());
      return list.stream().anyMatch(authority -> {
        log.debug("authority.getAuthority() ::: {}", authority.getAuthority());
        return authority.getAuthority().equals("ROLE_SUPER");
      });
    }
    return false;
  }
}
