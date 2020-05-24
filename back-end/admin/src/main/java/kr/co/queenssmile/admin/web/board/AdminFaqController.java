package kr.co.queenssmile.admin.web.board;

import com.google.common.collect.Lists;
import kr.co.queenssmile.admin.config.freemarker.PageableModel;
import kr.co.queenssmile.admin.service.board.faq.FaqAdService;
import kr.co.queenssmile.core.domain.board.faq.Faq;
import kr.co.queenssmile.core.domain.board.faq.category.FaqCategory;
import kr.co.queenssmile.core.domain.board.faq.category.FaqCategoryRepository;
import kr.co.queenssmile.core.domain.setting.AppSetting;
import kr.co.queenssmile.core.model.Filter;
import kr.co.queenssmile.core.utils.DataBinderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/admin/faq")
public class AdminFaqController {

  @Autowired
  private FaqAdService faqService;

  @Autowired
  private FaqCategoryRepository categoryRepository;

  @GetMapping
  public ModelAndView page(Model model,
                           @ModelAttribute("setting") AppSetting setting,
                           @PageableDefault(size = 20, sort = {"orderAscending"}, direction = Sort.Direction.ASC) Pageable pageable,
                           @RequestParam(required = false, defaultValue = "") String query,
                           @RequestParam(required = false) String startDate,
                           @RequestParam(required = false) String endDate) {

    Filter filter = new Filter(pageable, query, startDate, endDate);
    Page<Faq> page = faqService.page(setting.getDefaultLocale(), filter, null, null);

    model.addAttribute("data", PageableModel.of(page, pageable, query).toModel());
    model.addAttribute("page", pageable.getPageNumber());

    // Search filter
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);

    return new ModelAndView("admin/faq/list.ftl");
  }

  @GetMapping("/create")
  public ModelAndView create(Model model) {
    Faq faq = new Faq();
    faq.setActive(true);

    model.addAttribute("faq", faq);
    model.addAttribute("categories", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "orderAscending")));

    return new ModelAndView("admin/faq/create.ftl");
  }

  @GetMapping("update/{idFaq}")
  public ModelAndView update(@PathVariable Long idFaq,
                             @ModelAttribute("setting") AppSetting setting,
                             Model model) {

    Faq faq = faqService.get(setting.getDefaultLocale(), idFaq);

    List<FaqCategory> categories = Lists.newArrayList(categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "orderAscending")));

    // ERCategory Checked
    if (faq.getCategories() != null && faq.getCategories().size() > 0) {
      categories.forEach(category -> {
        faq.getCategories().forEach(_category -> {
          if (Objects.equals(category.getId(), _category.getId())) {
            category.setChecked(true);
          }
        });
      });
    }

    model.addAttribute("faq", faq);
    model.addAttribute("categories", categories);

    return new ModelAndView("/admin/faq/update.ftl");
  }

  @PostMapping("create")
  public ModelAndView faqCreate(@Valid Faq faq,
                                BindingResult result,
                                SessionStatus status,
                                @ModelAttribute("setting") AppSetting setting) {

    DataBinderUtils.objectValidate(result);

    faqService.create(faq);
    status.setComplete();
    return new ModelAndView("redirect:/admin/faq");
  }

  @PostMapping("update")
  public ModelAndView faqUpdate(@Valid Faq faq,
                                BindingResult result,
                                SessionStatus status) {

    DataBinderUtils.objectValidate(result);

    faqService.update(faq);
    status.setComplete();
    return new ModelAndView("redirect:/admin/faq/update/" + faq.getId() + "?success");
  }

  @PostMapping("/delete")
  public ModelAndView delete(@RequestParam Long id) {
    faqService.delete(id);
    return new ModelAndView("redirect:/admin/faq");
  }

  @PostMapping("/order")
  public ModelAndView changeOrder(HttpServletRequest request,
                                  @RequestParam Long id,
                                  @RequestParam String mode) {
    faqService.changeOrder(id, mode);
    return new ModelAndView("redirect:" + request.getHeader("referer"));
  }
}
