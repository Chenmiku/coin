package kr.co.queenssmile.admin.web;

import kr.co.queenssmile.core.domain.setting.AppSetting;
import kr.co.queenssmile.core.service.setting.AppSettingService;
import kr.co.queenssmile.core.utils.DataBinderUtils;
import kr.co.queenssmile.core.utils.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/admin/setting")
public class AdminSettingController {

  @Autowired
  private AppSettingService settingService;

  @GetMapping
  public ModelAndView setting(Model model) {

    model.addAttribute("setting", settingService.getSetting());
    model.addAttribute("locales", LocaleUtils.defaultLocales());

    return new ModelAndView("/admin/setting/update.ftl");
  }

  @PostMapping
  public ModelAndView postSetting(@Valid AppSetting setting,
                                  BindingResult result,
                                  SessionStatus status) {

    DataBinderUtils.objectValidate(result);

    log.debug("setting ::: {}", setting);
    settingService.setting(setting);
    status.setComplete();
    return new ModelAndView("redirect:/admin/setting?success");
  }
}
