package kr.co.queenssmile.core.domain.user;

public interface AuthorityRepositoryCustom {

  java.util.List<Authority> listByRoles(Authority.Role... roles);
}
