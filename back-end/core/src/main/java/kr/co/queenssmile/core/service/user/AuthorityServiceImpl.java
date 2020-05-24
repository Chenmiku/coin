package kr.co.queenssmile.core.service.user;

import kr.co.queenssmile.core.domain.user.Authority;
import kr.co.queenssmile.core.domain.user.AuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthorityServiceImpl implements AuthorityService {

  @Autowired
  private AuthorityRepository authorityRepository;

  @Override
  public List<Authority> list() {
    java.util.List<Authority> authorities = authorityRepository.findAll();
    return authorities.stream().sorted(Authority::compareTo).collect(Collectors.toList());
  }

  @Override
  public List<Authority> list(Authority.Role... roles) {
    return authorityRepository.listByRoles(roles);
  }
}
