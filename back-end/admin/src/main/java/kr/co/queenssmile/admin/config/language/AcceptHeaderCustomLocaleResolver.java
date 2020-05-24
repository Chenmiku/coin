package kr.co.queenssmile.admin.config.language;

import kr.co.queenssmile.core.domain.International.InternationalMode;
import kr.co.queenssmile.core.service.setting.AppSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * %% 주의 %%
 * Controller 에서 HttpServletRequest 의 .getLocale() 사용하면 안된다. (실제 ACCEPT-LANGUAGE 값)
 * Locale locale 매개변수로 받아와야한다. (설정에 필터된 값)
 */
@Slf4j
@Component
public class AcceptHeaderCustomLocaleResolver extends AcceptHeaderLocaleResolver {

  @Autowired
  private AppSettingService settingService;

  @Override
  public Locale getDefaultLocale() {
    return settingService.getDefaultLocale();
  }

  // 국문/영문/중문(간체)/중문(번체)/일문 설정
  @Override
  public Locale resolveLocale(HttpServletRequest request) {

    Locale locale = super.resolveLocale(request);
    return this.resolveLocale(locale);
  }

  private Locale resolveLocale(Locale locale) {
    final InternationalMode im = settingService.getInternationalMode();
    final Locale defaultLocale = this.getDefaultLocale();

    if (locale.getLanguage().equals("ko")) {  // 1. 국문
      if (!im.isKoKr()) {
        locale = defaultLocale;
      } else {
        locale = Locale.KOREA;
      }
    } else if (locale.getLanguage().equals("en")) {  // 2. 영문
      if (!im.isEnUs()) {
        locale = defaultLocale;
      } else {
        locale = Locale.US;
      }
    } else if (locale.getLanguage().equals("zh") && locale.getCountry().equals("CN")) {  // 3. 중문 (간체)
      if (!im.isZhCn()) {
        locale = defaultLocale;
      }
    } else if (locale.getLanguage().equals("zh") && locale.getCountry().equals("TW")) {  // 4. 중문 (번체)
      if (!im.isZhTw()) {
        locale = defaultLocale;
      }
    } else if (locale.getLanguage().equals("zh")) { // 3-2. 중문 -> 중문 (간체)
      locale = Locale.CHINA;
    } else if (locale.getLanguage().equals("ja")) {  // 5. 일어
      if (!im.isJaJp()) {
        locale = defaultLocale;
      } else {
        locale = Locale.JAPAN;
      }
    } else {
      locale = defaultLocale;
    }
//        log.debug("### Filtered Locale ::: {}", locale);
    return locale;
  }

}
