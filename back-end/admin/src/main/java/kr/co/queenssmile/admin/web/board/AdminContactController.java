package kr.co.queenssmile.admin.web.board;


import kr.co.queenssmile.admin.config.freemarker.PageableModel;
import kr.co.queenssmile.admin.service.board.contact.ContactAdService;
import kr.co.queenssmile.core.domain.board.contact.Contact;
import kr.co.queenssmile.core.model.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/admin/contact")
public class AdminContactController {

  @Autowired
  private ContactAdService contactService;

  @GetMapping
  public ModelAndView page(Model model,
                           @PageableDefault(size = 20, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable,
                           @RequestParam(required = false, defaultValue = "") String query,
                           @RequestParam(required = false) String startDate,
                           @RequestParam(required = false) String endDate) {

    Filter filter = new Filter(pageable, query, startDate, endDate);
    Page<Contact> page = contactService.page(filter);

    model.addAttribute("data", PageableModel.of(page, pageable, query).toModel());
    model.addAttribute("page", pageable.getPageNumber());

    // Search filter
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);

    return new ModelAndView("admin/contact/list.ftl");
  }

  @GetMapping("/create")
  public ModelAndView create(Model model) {

    Contact contact = new Contact();

    model.addAttribute("contact", contact);
    return new ModelAndView("admin/contact/create.ftl");
  }

  @GetMapping("update/{id}")
  public ModelAndView update(@PathVariable Long id,
                             Model model) {

    Contact contact = contactService.get(id);

    model.addAttribute("contact", contact);
    return new ModelAndView("/admin/contact/update.ftl");
  }

  @PostMapping("create")
  public ModelAndView contactCreate(@Valid Contact contact,
                                    org.springframework.validation.BindingResult result,
                                    SessionStatus status) {

    if (result.hasErrors()) {
      result.getAllErrors().forEach(objectError -> {
        log.error("name -> " + objectError.getObjectName() + ", msg -> " + objectError.getDefaultMessage());
      });
      return new ModelAndView("/admin/contact/create.ftl");
    }
    contactService.create(contact);
    status.setComplete();

    return new ModelAndView("redirect:/admin/contact");
  }

  @PostMapping("update")
  public ModelAndView contactUpdate(@Valid Contact contact,
                                    org.springframework.validation.BindingResult result,
                                    SessionStatus status) {

    if (result.hasErrors()) {
      result.getAllErrors().forEach(objectError -> {
        log.error("name -> " + objectError.getObjectName() + ", msg -> " + objectError.getDefaultMessage());
      });
      return new ModelAndView("/admin/contact/update.ftl");
    }
    contactService.update(contact);
    status.setComplete();

    return new ModelAndView("redirect:/admin/contact/update/" + contact.getId() + "?success");
  }

  @PostMapping("/delete")
  public ModelAndView delete(@RequestParam Long id) {

    contactService.delete(id);
    return new ModelAndView("redirect:/admin/contact");
  }
}
