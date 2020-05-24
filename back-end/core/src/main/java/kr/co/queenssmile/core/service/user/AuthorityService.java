package kr.co.queenssmile.core.service.user;

import kr.co.queenssmile.core.domain.user.Authority;

import java.util.List;

public interface AuthorityService {

  List<Authority> list();

  List<Authority> list(Authority.Role... roles);
}
