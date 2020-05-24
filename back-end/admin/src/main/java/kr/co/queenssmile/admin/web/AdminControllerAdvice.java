package kr.co.queenssmile.admin.web;

import kr.co.queenssmile.admin.config.property.meta.AdminMetaPlugin;
import kr.co.queenssmile.admin.config.property.sidebar.Section;
import kr.co.queenssmile.admin.config.property.sidebar.Sidebar;
import kr.co.queenssmile.admin.config.property.sidebar.SubSection;
import kr.co.queenssmile.core.config.security.CurrentUser;
import kr.co.queenssmile.core.domain.International.InternationalMode;
import kr.co.queenssmile.core.domain.setting.AppSetting;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.domain.user.UserRepository;
import kr.co.queenssmile.core.service.setting.AppSettingService;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@ControllerAdvice(basePackages = {"kr.co.queenssmile.admin.web"})
public class AdminControllerAdvice {

  @Autowired
  private Sidebar sidebar;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AdminMetaPlugin adminMetaPlugin;

  @Autowired
  private AppSettingService settingService;

  @Value("${spring.application.name}")
  private String appName;

  @ModelAttribute("appName")
  public String appName() {
    log.debug("appName ::: {}", appName);
    return this.appName;
  }

  @ModelAttribute("currentRole")
  public String getRole(@AuthenticationPrincipal CurrentUser currentUser) {
    log.debug("> role");

    if (currentUser != null) {
      Optional<User> userOpt = userRepository.findById(currentUser.getId());
      if (userOpt.isPresent()) {
        return userOpt.get().getRoleTopLevel().getRole();
      }
    }
    return null;
  }

  // 환경설정
  @ModelAttribute("metaPlugin")
  public AdminMetaPlugin metaPlugin() {
    return adminMetaPlugin;
  }

  // 회원등급 노출여부

  @ModelAttribute("setting")
  public AppSetting setting(HttpServletRequest request) {
    log.debug("> setting");
    log.debug(">>> URI ::: {}", request.getRequestURI());
    log.debug(">>> Method ::: {}", request.getMethod());
    return !request.getMethod().equals("HEAD") ? settingService.getSetting() : new AppSetting();
  }

  // 국제화 모드
  @ModelAttribute("international")
  public boolean international(HttpServletRequest request) {
    log.debug("> international");
    return this.setting(request) != null && this.setting(request).isInternational();
  }

  // 국제 언어 모드
  @ModelAttribute("im")
  public InternationalMode internationalMode(HttpServletRequest request) {
    log.debug("> internationalMode");

    AppSetting setting = this.setting(request);

    if (setting != null) {
      if (setting.isInternational()) {
        return setting.getInternationalMode();
      } else if (setting.getDefaultLocale() != null) {
        InternationalMode im = new InternationalMode();
        im.toFalseMode();
        final Locale defaultLocale = setting.getDefaultLocale();
        if (defaultLocale.equals(Locale.KOREA)) {
          im.setKoKr(true);
        }
        if (defaultLocale.equals(Locale.US)) {
          im.setEnUs(true);
        }
        if (defaultLocale.equals(Locale.CHINA)) {
          im.setZhCn(true);
        }
        if (defaultLocale.equals(Locale.TAIWAN)) {
          im.setZhTw(true);
        }
        if (defaultLocale.equals(Locale.JAPAN)) {
          im.setJaJp(true);
        }
        log.debug("defaultLocale ::: {}", defaultLocale);
        log.debug("IM ::: {}", im);
        return im;
      }
    }
    return new InternationalMode();
  }

  @ModelAttribute("sidebar")
  public Sidebar sidebar() {
    log.debug("> SIDEBAR");

    return sidebar;
  }

  @ModelAttribute("header")
  public Map<String, String> header(HttpServletRequest request) {

    log.debug("> HEADER");

    Map<String, String> result = new HashMap<>();
    String path = request.getServletPath();
    List<Section> sections = sidebar.getSections();
    log.debug("sections ::: {}", sections.size());

    if (sections != null) {
      sections.forEach(section -> {

        if (section != null && section.getElements() != null) {
          section.getElements().forEach(element -> {

            if (element != null) {
              if (element.getType().equals("ONE")) {

                if (containsDDLPath(path).contains(element.getUrl())) {
                  result.put("title", element.getTitle());
                  result.put("description", element.getDescription());
                  result.put("url", StringUtils.isEmpty(element.getUrl()) ? null : this.getUrlWithoutParameters(element.getUrl()));
                }
              } else if (element.getType().equals("MULTI")) {
                SubSection subSection = element.getSection();

                if (subSection != null && subSection.getElements() != null) {
                  subSection.getElements().forEach(subElement -> {

                    if (subElement != null) {

                      if (containsDDLPath(path).contains(subElement.getUrl())) {
                        result.put("title", subElement.getTitle());
                        result.put("description", subElement.getDescription());
                        result.put("url", StringUtils.isEmpty(subElement.getUrl()) ? null : this.getUrlWithoutParameters(subElement.getUrl()));
                      }
                    }
                  });
                }
              }
            }

          });
        }
      });
    }

    return result;
  }


  private String getUrlWithoutParameters(String url) {
//    log.debug("url ::: {}", url);
    String result = url.replaceFirst("\\?.*$", "");
//    log.debug("result ::: {}", result);
    return result;
  }

  /**
   * /create , /update 경로는 부로 경로로 변경
   *
   * @param path 경로
   * @return ddl 인 경우 부모 경로
   */
  private String containsDDLPath(String path) {

    String lastPath = "";

    int i = path.lastIndexOf('/');
    if (i > 0) {
      lastPath = path.substring(i);
    }
    return lastPath.equals("/create") || lastPath.equals("/update") ? path.replace(lastPath, "") : path;
  }
}
