package kr.co.queenssmile.admin.web.board;

import kr.co.queenssmile.admin.service.board.post.PCategoryAdService;
import kr.co.queenssmile.core.domain.board.post.Post;
import kr.co.queenssmile.core.domain.board.post.category.PCategory;
import kr.co.queenssmile.core.domain.setting.AppSetting;
import kr.co.queenssmile.core.utils.DataBinderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/admin/category-notice")
public class AdminPostCategoryNoticeController {

  private static Post.Type TYPE = Post.Type.NOTICE;

  @Autowired
  private PCategoryAdService categoryService;

  @GetMapping
  public ModelAndView list(Model model,
                           @ModelAttribute("setting") AppSetting setting) {

    model.addAttribute("list", categoryService.list(setting.getDefaultLocale(), TYPE));
    return new ModelAndView("admin/post-category/list.ftl");
  }

  @GetMapping("/create")
  public ModelAndView create(Model model) {
    PCategory category = new PCategory();
    category.setType(TYPE);
    category.setActive(true);

    model.addAttribute("category", category);
    return new ModelAndView("admin/post-category/create.ftl");
  }

  @GetMapping("update/{idCategory}")
  public ModelAndView update(Model model,
                             @PathVariable Long idCategory,
                             @ModelAttribute("setting") AppSetting setting) {

    model.addAttribute("category", categoryService.get(setting.getDefaultLocale(), idCategory));

    return new ModelAndView("/admin/post-category/update.ftl");
  }

  @PostMapping("create")
  public ModelAndView postCreate(@Valid PCategory category,
                                 org.springframework.validation.BindingResult result,
                                 SessionStatus status,
                                 @ModelAttribute("setting") AppSetting setting) {

    DataBinderUtils.objectValidate(result);

    categoryService.create(category);
    status.setComplete();
    return new ModelAndView("redirect:/admin/category-notice");
  }

  @PostMapping("update")
  public ModelAndView postUpdate(@Valid PCategory category,
                                 org.springframework.validation.BindingResult result,
                                 SessionStatus status) {

    DataBinderUtils.objectValidate(result);

    categoryService.update(category);
    status.setComplete();
    return new ModelAndView("redirect:/admin/category-notice/update/" + category.getId() + "?success");
  }

  @PostMapping("/delete")
  public ModelAndView delete(@RequestParam Long id) {
    categoryService.delete(id);
    return new ModelAndView("redirect:/admin/category-notice");
  }

  @PostMapping("/order")
  public ModelAndView changeOrder(HttpServletRequest request,
                                  @RequestParam Long id,
                                  @RequestParam String mode) {
    categoryService.changeOrder(id, mode);
    return new ModelAndView("redirect:" + request.getHeader("referer"));
  }

}
