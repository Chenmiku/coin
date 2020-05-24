package kr.co.queenssmile.core.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.config.security.CryptoAESConverter;
import kr.co.queenssmile.core.config.security.CurrentUser;
import kr.co.queenssmile.core.domain.AbstractEntity;
import kr.co.queenssmile.core.domain.RestEntityBody;
import kr.co.queenssmile.core.model.resbody.user.MeResBody;
import kr.co.queenssmile.core.utils.DateUtils;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * 사용회원
 */
@Slf4j
@Entity
@Getter
@Setter
@ToString(exclude = {
    "authorities"
})
public class User extends AbstractEntity<Long> implements RestEntityBody<MeResBody> {

  private static final long serialVersionUID = -9041281596933538392L;

  public static final String DEFAULT_PROFILE_IMAGE = "/assets/images/default-profile.png";

  public static String[] IGNORE_PROPERTIES = {
      "id",
      "password",
      "qnas"
  };

  @PrePersist
  public void prePersist() {

    if (this.socialId == null) {
      this.socialId = new SocialId();
    }
    if (this.userDetailsMeta == null) {
      this.userDetailsMeta = new UserDetailsMeta();
    }
    if (this.termsAgree == null) {
      this.termsAgree = new TermsAgree();
    }
    if (this.verification == null) {
      this.verification = new Verification();
    }
    if (this.leaveMeta == null) {
      this.leaveMeta = new LeaveMeta();
    }
    if (this.dormancyMeta == null) {
      this.dormancyMeta = new DormancyMeta();
    }
    if (this.image == null) {
      this.image = DEFAULT_PROFILE_IMAGE;
    }
    if (this.password == null) {
      this.password = "";
    }

  }

  @PreUpdate
  public void PreUpdate() {

  }

  /**
   * 데이터를 수정할때 BeanUtils.copyProperties 메소드를 사용할 경우 변경되지 않아야 할 필드는 ignoreProperties 인자값으로 처리하는데,
   *
   * @Embedded 필드(객체타입필드)는 적용되지 않는 문제를 해결 하기 위한 메소드이다.
   * - 객체를 copy 하기 전에 이 메소드를 먼저 수행해야 한다.
   */
  public void keepEmbeddedInformation(User oriUser) {
    this.setVerification(oriUser.getVerification());
    this.setSocialId(oriUser.getSocialId());
    this.setLeaveMeta(oriUser.getLeaveMeta());
  }

  @Id
  @GeneratedValue
  @Column(unique = true)
  private Long id;  // PK

  @Column(nullable = false)
  @Convert(converter = CryptoAESConverter.class)
  private String email; // ID

  @Column
  @Convert(converter = CryptoAESConverter.class)
  private String fullName;

  @JsonIgnore
  @Column
  private String password;

  @JsonIgnore
  @Transient
  private String passwordConfirm;

  @Column
  private String image;

  @Column
  @Enumerated(EnumType.ORDINAL)
  private Gender gender;

  @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_UNIT_BAR)
  private LocalDate birthDate;

  @Column()
  @Convert(converter = CryptoAESConverter.class)
  private String mobile;

  @JsonIgnore
  @Embedded
  private TermsAgree termsAgree;

  @JsonIgnore
  @Embedded
  private Verification verification;

  @JsonIgnore
  @Embedded
  private SocialId socialId;

  //== 인증 메타 정보
  @JsonIgnore
  @Embedded
  private UserDetailsMeta userDetailsMeta;

  //== 탈퇴 정보
  @JsonIgnore
  @Embedded
  private LeaveMeta leaveMeta;

  //== 휴면 정보
  @JsonIgnore
  @Embedded
  private DormancyMeta dormancyMeta;

  @Transient
  private String verificationCode;

  @JsonIgnore
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "UserToAuthority",
      joinColumns = {@JoinColumn(name = "idUser", foreignKey = @ForeignKey(name = "FK_User_For_User_To_Auth"))},
      inverseJoinColumns = @JoinColumn(name = "idAuthority", foreignKey = @ForeignKey(name = "FK_Authority_For_User_To_Auth")))
  private Set<Authority> authorities = new HashSet<>();

  @Transient
  private Authority.Role role;

  public Authority.Role getRoleTopLevel() {
    if (this.authorities != null && this.authorities.size() > 0) {
      return authorities.stream().reduce((first, second) -> first.getLevel() == Integer.max(first.getLevel(), second.getLevel()) ? first : second).get().getRole();
    }
    return this.role;
  }

  @JsonIgnore
  public List<String> getRoles() {
    List<String> roles = new ArrayList<>();
    for (Authority authority : this.authorities) {
      roles.add(authority.getRole().getRole());
    }
    return roles;
  }

  //== GrantedAuthorities 객체 권한 본제 메소드
  @JsonIgnore
  public Set<GrantedAuthority> getGrantedAuthorities() {
    Set<GrantedAuthority> authorities = new LinkedHashSet<>();
    this.authorities.forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.getRole().getRole())));
    return authorities;
  }

  //== UserDetails 객체 복제 메소드
  @JsonIgnore
  public CurrentUser getUserDetails() throws NullPointerException {
    return new CurrentUser(
        this.id,
        this.email,
        this.getFullName(),
        this.mobile,
        this.image,
        this.password,
        this.userDetailsMeta.isEnabled(),
        this.userDetailsMeta.isAccountNonExpired(),
        this.userDetailsMeta.isCredentialsNonExpired(),
        this.userDetailsMeta.isAccountNonLocked(),
        getGrantedAuthorities());
  }

  @Override
  public void delete() {

  }

  @Override
  public void lazy() {

  }

  // Utils
  public void setUserAuthorities(AuthorityRepository authorityRepository) {
    Authority.Role role = this.role;
    if (role == null) {
      throw new RuntimeException("user's role is null!!!");
    }
    Set<Authority> authorities = new HashSet<>();
    switch (role) {
      case SUPER:
        authorities.add(authorityRepository.findByRole(Authority.Role.SUPER));
        break;
      case ADMIN:
        authorities.add(authorityRepository.findByRole(Authority.Role.ADMIN));
        break;
      case WRITER:
        authorities.add(authorityRepository.findByRole(Authority.Role.WRITER));
        break;
      case AGENCY:
        authorities.add(authorityRepository.findByRole(Authority.Role.AGENCY));
        break;
      case House_Hold:
        authorities.add(authorityRepository.findByRole(Authority.Role.House_Hold));
        break;
      case USER:
        authorities.add(authorityRepository.findByRole(Authority.Role.USER));
        break;
    }
    this.setAuthorities(authorities);
  }

  @Override
  public MeResBody toBody(Locale locale) {
    return MeResBody.builder()
        .email(this.email)
        .fullName(this.fullName)
        .mobile(this.mobile)
        .roles(this.getAuthorities().stream().map(Authority::getRole).collect(toList()))
        .image(this.image)
        .termsAgree(this.termsAgree)
        .verification(this.verification)
        .hasPassword(StringUtils.isNotEmpty(this.password))
        .dormancy(this.dormancyMeta.isDormancy())
        .build();
  }
}
