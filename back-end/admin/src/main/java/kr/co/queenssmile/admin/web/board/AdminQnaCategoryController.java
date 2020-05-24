package kr.co.queenssmile.admin.web.board;

import kr.co.queenssmile.admin.service.board.qna.QnaCategoryAdService;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.board.qna.category.QnaCategory;
import kr.co.queenssmile.core.domain.setting.AppSetting;
import kr.co.queenssmile.core.utils.LocaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/category-qna")
public class AdminQnaCategoryController {

  @Autowired
  private QnaCategoryAdService categoryService;

  @GetMapping
  public ModelAndView list(Model model,
                           @ModelAttribute("setting") AppSetting setting) {

    model.addAttribute("list", categoryService.list(setting.getDefaultLocale()));
    return new ModelAndView("admin/qna-category/list.ftl");
  }

  @GetMapping("/create")
  public ModelAndView create(Model model) {
    QnaCategory category = new QnaCategory();

    category.setActive(true);

    model.addAttribute("category", category);
    return new ModelAndView("admin/qna-category/create.ftl");
  }

  @GetMapping("update/{id}")
  public ModelAndView update(@PathVariable Long id,
                             @ModelAttribute("setting") AppSetting setting,
                             Model model) {

    model.addAttribute("category", categoryService.get(setting.getDefaultLocale(), id));
    return new ModelAndView("/admin/qna-category/update.ftl");
  }

  @PostMapping("create")
  public ModelAndView postCreate(@Valid QnaCategory category,
                                 org.springframework.validation.BindingResult result,
                                 SessionStatus status) {

    if (result.hasErrors()) {
      result.getAllErrors().forEach(objectError -> {
        log.error("name -> " + objectError.getObjectName() + ", msg -> " + objectError.getDefaultMessage());
      });
      throw new BadRequestException();
    }

    categoryService.create(category);
    status.setComplete();
    return new ModelAndView("redirect:/admin/category-qna");
  }

  @PostMapping("update")
  public ModelAndView postUpdate(@Valid QnaCategory category,
                                 org.springframework.validation.BindingResult result,
                                 SessionStatus status) {

    if (result.hasErrors()) {
      result.getAllErrors().forEach(objectError -> {
        log.error("name -> " + objectError.getObjectName() + ", msg -> " + objectError.getDefaultMessage());
      });
      throw new BadRequestException();
    }

    categoryService.update(category);
    status.setComplete();
    return new ModelAndView("redirect:/admin/category-qna/update/" + category.getId() + "?success");
  }

  @PostMapping("/delete")
  public ModelAndView delete(@RequestParam Long id) {

    categoryService.delete(id);
    return new ModelAndView("redirect:/admin/category-qna");
  }

  @PostMapping("/order")
  public ModelAndView changeOrder(HttpServletRequest request,
                                  @RequestParam Long id,
                                  @RequestParam String mode) {
    categoryService.changeOrder(id, mode);

    return new ModelAndView("redirect:" + request.getHeader("referer"));
  }

  // DUPLICATE
  @ResponseBody
  @PostMapping(value = "/duplicate/{language}", consumes = org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> duplicate(@RequestBody org.springframework.util.MultiValueMap<String, String> body,
                                     @PathVariable Locale language) {

    final String fieldName = LocaleUtils.getFieldNameByCategory(language);
    Map<String, Object> result = new HashMap<>();

    if (body == null || body.getFirst(fieldName) == null) {
      throw new BadRequestException();
    }

    String value = body.getFirst(fieldName);

    if (StringUtils.isEmpty(value)) {
      throw new BadRequestException();
    }

    if (categoryService.isDuplicate(language, value)) {
      result.put("result", "duplicate");
    } else {
      result.put("result", "success");
    }
    return ResponseEntity.ok(result);
  }

  @ResponseBody
  @PostMapping(value = "/{id}/duplicate/{language}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> duplicateUpdate(@RequestBody org.springframework.util.MultiValueMap<String, String> body,
                                           @PathVariable Long id,
                                           @PathVariable Locale language) {

    final String fieldName = LocaleUtils.getFieldNameByCategory(language);
    Map<String, Object> result = new HashMap<>();

    if (body == null || body.getFirst(fieldName) == null) {
      return ResponseEntity.badRequest().build();
    }

    String value = body.getFirst(fieldName);

    QnaCategory category = categoryService.get(language, id);
    final String name = category.getName().getValue();

    log.debug("language ::: {}", language);
    log.debug("fieldName ::: {}", fieldName);
    log.debug("value ::: {}", value);
    log.debug("name ::: {}", name);

    if (value.equals(name)) {
      result.put("result", "success");
    } else if (categoryService.isDuplicate(language, value)) {
      result.put("result", "duplicate");
    } else {
      result.put("result", "success");
    }
    return ResponseEntity.ok(result);
  }
}
