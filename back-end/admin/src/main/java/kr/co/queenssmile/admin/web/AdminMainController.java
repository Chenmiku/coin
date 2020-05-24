package kr.co.queenssmile.admin.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminMainController {

  @GetMapping
  public ModelAndView main(HttpServletRequest httpServletRequest, Model model) {
    return new ModelAndView("redirect:/admin/user");
//        return new ModelAndView("redirect:/admin/order");
  }
}
