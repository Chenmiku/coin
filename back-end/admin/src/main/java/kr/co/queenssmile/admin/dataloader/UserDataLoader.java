package kr.co.queenssmile.admin.dataloader;

import kr.co.queenssmile.admin.service.user.UserAdService;
import kr.co.queenssmile.core.domain.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 초기 유저 등록 세팅 값
 */
@Component
@Slf4j
@Order(1) // 낮을수록 먼저
public class UserDataLoader implements CommandLineRunner {

  @Autowired
  private AuthorityRepository authorityRepository;

  @Autowired
  private UserAdService userAdService;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void run(String... args) throws Exception {
    log.debug("UserDataLoader ::: {}", authorityRepository.count());

    // 권한이 없으면 처리
    if (authorityRepository.count() == 0) {
      authorityRepository.save(Authority.of(Authority.Role.SUPER));
      authorityRepository.save(Authority.of(Authority.Role.ADMIN));
      authorityRepository.save(Authority.of(Authority.Role.WRITER));
      authorityRepository.save(Authority.of(Authority.Role.AGENCY));
      authorityRepository.save(Authority.of(Authority.Role.USER));
    }

    if (userRepository.findByEmail("vuhongthaihy@gmail.com") == null) {
      createUser("vuhongthaihy@gmail.com", "1234", "Vũ Hồng Thái", "0342662668", Authority.Role.SUPER);
    }

  }

  private User createUser(String email, String password, String fullName, String mobile, Authority.Role role) {
    User createUser = new User();

    createUser.setEmail(email);
    createUser.setPassword(password);
    createUser.setFullName(fullName);

    createUser.setMobile(mobile);

    createUser.setTermsAgree(new TermsAgree());
    createUser.getTermsAgree().setTaService(true);
    createUser.getTermsAgree().setTaPrivacy(true);
    createUser.getTermsAgree().setTaMarketing(true);

    createUser.setRole(role);
    return userAdService.create(createUser);
  }
}
