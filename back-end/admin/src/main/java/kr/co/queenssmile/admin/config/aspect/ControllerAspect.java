package kr.co.queenssmile.admin.config.aspect;

import kr.co.queenssmile.core.config.property.ProcessEnv;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class ControllerAspect {

  @Autowired
  private ProcessEnv processEnv;

  @Autowired
  private UserRepository userRepository;

  @Value("${spring.application.name}")
  private String appName;

  @Pointcut("within(@org.springframework.stereotype.Controller *) "
    + "|| within(@org.springframework.web.bind.annotation.RestController *)")
  public void controllerBean() {
  }

  @Before("controllerBean()")
  public void beforeMethod(JoinPoint joinPoint) {

    for (Object obj : joinPoint.getArgs()) {

      if (obj instanceof org.springframework.ui.Model && processEnv != null) {
        Model model = (org.springframework.ui.Model) obj;
        model.addAttribute("production", processEnv.isProduction());
        model.addAttribute("appName", appName);
      }

      if (obj instanceof OAuth2Authentication) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) obj;

        if (!StringUtils.isEmpty(oAuth2Authentication.getPrincipal())) {

          User user = userRepository.findByEmail(oAuth2Authentication.getPrincipal().toString());
          if (user != null) {
            UserDetails userDetails = user.getUserDetails();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), user.getGrantedAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        }
      }
    }
  }

  @Around("controllerBean()")
  public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {

    String methodName = pjp.getSignature().getName();

    for (Object obj : pjp.getArgs()) {
      if (obj instanceof HttpServletRequest || obj instanceof MultipartHttpServletRequest) {
        HttpServletRequest request = (HttpServletRequest) obj;
        //                HttpUtils.debug(request); // 디버그 로그
      }
    }

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    Object result = pjp.proceed();

    stopWatch.stop();

    String logStr = String.format("[%s 메소드 수행시간: %s(s)]", methodName, stopWatch.getTotalTimeSeconds());

    if (stopWatch.getTotalTimeSeconds() < 0.5) {
      if (log.isDebugEnabled()) {
        log.info(logStr);
      }
    } else if (stopWatch.getTotalTimeSeconds() >= 0.5 || stopWatch.getTotalTimeSeconds() < 3) {
      log.warn(logStr);
    } else {
      log.error(logStr);
    }
    return result;
  }

  @After("controllerBean()")
  public void afterFinally(JoinPoint joinPoint) {

    for (Object obj : joinPoint.getArgs()) {
      if (obj instanceof OAuth2Authentication) {
        OAuth2Authentication authentication = (OAuth2Authentication) obj;
        SecurityContextHolder.clearContext();
      }
    }
  }
}

