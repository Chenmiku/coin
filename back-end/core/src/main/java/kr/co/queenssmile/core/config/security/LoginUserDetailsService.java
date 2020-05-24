package kr.co.queenssmile.core.config.security;

import kr.co.queenssmile.core.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class LoginUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public LoginUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    try {
      log.debug(">>>> loadUserByUsername ::: {}", email);

      return userRepository.findByEmail(email).getUserDetails();
    } catch (NullPointerException e) {
      throw new UsernameNotFoundException(null);
    }
  }
}