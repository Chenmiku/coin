package kr.co.queenssmile.core.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtils {

  public static CurrentUser getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      throw new AccessDeniedException("User not found in security session.");
    }

    AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
    if (!resolver.isAnonymous(auth) && auth.getPrincipal() instanceof CurrentUser) {
      return (CurrentUser) auth.getPrincipal();
    }
    return null;
  }

  public static Long getCurrentUserId() {
    return getCurrentUser() != null ? getCurrentUser().getId() : null;
  }

  public static String getCurrentUsername() {
    return getCurrentUser() != null ? getCurrentUser().getUsername() : null;
  }
}
