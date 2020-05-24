package kr.co.queenssmile.admin.web;

import kr.co.queenssmile.core.domain.setting.AppSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/admin/test")
public class AdminTestController {

  @GetMapping
  public ModelAndView test(Model model,
                           @ModelAttribute("setting") AppSetting setting) {
    log.debug("setting ::: {}", setting);
    return new ModelAndView("/view/index.ftl");
  }
}
