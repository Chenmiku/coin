package kr.co.queenssmile.admin.service.user;

import com.google.common.collect.Lists;
import kr.co.queenssmile.core.config.database.PwdEncConfig;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.user.*;
import kr.co.queenssmile.core.model.Filter;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserAdServiceImpl implements UserAdService {

  @Value("${core.privacy.expired.password}")
  private Integer pwdExpiredPeriod;

  @Autowired
  private UserRepository userRepository;

//  @Autowired
//  private UserService userService;

  @Autowired
  private AuthorityRepository authorityRepository;

  @Autowired
  private PwdEncConfig pwdEncConfig;

  @Override
  @Transactional
  public User create(User user) {

    if (user == null) {
      throw new BadRequestException("User가 입력되지 않았습니다.");
    }

    if (StringUtils.isEmpty(user.getPassword())) {
      throw new BadRequestException("비밀번호가 입력되지 않았습니다.");
    }

    if (StringUtils.isEmpty(user.getImage())) {
      user.setImage(User.DEFAULT_PROFILE_IMAGE);
    }
    user.setPassword(pwdEncConfig.getPasswordEncoder().encode(user.getPassword()));

    // verification
    user.setVerification(new Verification());
    user.getVerification().setEmail(true);
    user.getVerification().setMobile(true);

    // add role
    user.setUserAuthorities(authorityRepository); // 사용자 권한 주기

    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User update(User user) {

    if (user.getId() == null) {
      throw new BadRequestException();
    }

    return userRepository.findById(user.getId())
        .map(ori -> {
          user.setUserAuthorities(authorityRepository); // 사용자 권한 주기
          user.keepEmbeddedInformation(ori);

          BeanUtils.copyProperties(user, ori, User.IGNORE_PROPERTIES);
          return userRepository.save(ori);
        }).orElseThrow(BadRequestException::new);
  }


  @Override
  @Transactional(readOnly = true)
  public User get(Long id) {
    return userRepository.findById(id)
        .map(user -> {
          user.lazy();
          return user;
        }).orElse(null);
  }

  @Override
  @Transactional
  @PreAuthorize("hasRole('ROLE_SUPER')")
  public void delete(Long id) {
    userRepository.findById(id)
        .ifPresent(user -> userRepository.delete(user));
  }

  @Override
  @Transactional
  @PreAuthorize("hasRole('ROLE_SUPER')")
  public void leave(Long id) {
//    userService.leave(id, "관리자에의해 탈퇴처리됨");
  }

  @Override
  @Transactional
  @PreAuthorize("hasRole('ROLE_SUPER')")
  public void removePrivacy(Long id) {
//    userService.removePrivacy(id);
  }

  @Override
  @Transactional
  public void restoreLeave(Long id) {
    userRepository.findById(id)
        .ifPresent(user -> {
          user.getUserDetailsMeta().setEnabled(true);
          user.getLeaveMeta().setLeave(false);
          user.getLeaveMeta().setLeaveTime(null);
          user.getLeaveMeta().setLeaveReason(null);
        });
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> list(Filter filter) {

    return Lists.newArrayList(userRepository.findAll(
        UserPredicate.getInstance()
            .startDate(filter.getStartDate())
            .endDate(filter.getEndDate())
            .values()
    ));
  }

  @Override
  @Transactional(readOnly = true)
  public Page<User> page(Filter filter, Authority.Role role) {

    return userRepository.findAll(
        UserPredicate.getInstance()
            .search(filter.getQuery())
            .startDate(filter.getStartDate())
            .endDate(filter.getEndDate())
            .role(role)
            .roles(role == null ? Lists.newArrayList(Authority.Role.USER) : null)
            .values()
        ,
        filter.getPageable());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<User> pageByManager(Filter filter, Authority.Role role) {

    return userRepository.findAll(
        UserPredicate.getInstance()
            .search(filter.getQuery())
            .startDate(filter.getStartDate())
            .endDate(filter.getEndDate())
            .roles(role)
            .roles(role == null ? Lists.newArrayList(Authority.Role.SUPER, Authority.Role.ADMIN) : null)
            .values()
        ,
        filter.getPageable());
  }

  @Override
  @Transactional(readOnly = true)
  public Authority authority(Authority.Role role) {
    return authorityRepository.findByRole(role);
  }


  @Override
  public boolean isDuplicateEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public boolean isDuplicateMobile(String mobile) {
    return userRepository.existsByMobile(mobile);
  }

  @Override
  @Transactional
  public void resetPassword(Long id, String password) {
    userRepository.findById(id)
        .ifPresent(user -> {
          user.setPassword(pwdEncConfig.getPasswordEncoder().encode(password));
          user.getUserDetailsMeta().setUpdatedPasswordDateTime(LocalDateTime.now());
        });
  }
}
