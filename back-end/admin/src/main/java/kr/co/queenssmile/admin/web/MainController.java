package kr.co.queenssmile.admin.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping
public class MainController {

  @GetMapping
  public ModelAndView helloWorld() {
    return new ModelAndView("/view/index.ftl");
  }
}
