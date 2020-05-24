package kr.co.queenssmile.admin.web.user;

import kr.co.queenssmile.admin.config.freemarker.PageableModel;
import kr.co.queenssmile.admin.service.user.UserAdService;
import kr.co.queenssmile.core.domain.setting.AppSetting;
import kr.co.queenssmile.core.domain.user.Authority;
import kr.co.queenssmile.core.domain.user.TermsAgree;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.model.Filter;
import kr.co.queenssmile.core.service.user.AuthorityService;
import kr.co.queenssmile.core.utils.DataBinderUtils;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/admin/manager")
public class AdminManagerController {

  @Autowired
  private UserAdService userAdService;


  @Autowired
  private AuthorityService authorityService;

  @GetMapping
  public ModelAndView page(Model model,
                           @ModelAttribute("setting") AppSetting setting,
                           @PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                           @RequestParam(required = false, defaultValue = "") String query,
                           @RequestParam(required = false) String startDate,
                           @RequestParam(required = false) String endDate,
                           @RequestParam(required = false) Authority.Role role) {

    Filter filter = new Filter(pageable, query, startDate, endDate);
    Page<User> page = userAdService.page(filter, role);

    // Default
    model.addAttribute("data", PageableModel.of(page, pageable, query).toModel());
    model.addAttribute("page", pageable.getPageNumber());

    model.addAttribute("roles", Arrays.asList(Authority.Role.SUPER, Authority.Role.ADMIN));
    model.addAttribute("role", role);

    // Search filter
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);

    return new ModelAndView("admin/admin/list.ftl");
  }


  @GetMapping("create")
  public ModelAndView create(Model model,
                             @RequestParam(required = false) Long idBusinessUser,
                             @ModelAttribute("setting") AppSetting setting) {
    User user = new User();
    TermsAgree ta = TermsAgree.builder()
        .taService(true)
        .taPrivacy(true)
        .taLocation(true)
        .taThirdParty(true)
        .build();
    ta.setTaMarketing(true);
    user.setTermsAgree(ta);

    user.setRole(Authority.Role.ADMIN);
    model.addAttribute("user", user);
    model.addAttribute("authorities", authorityService.list(Authority.Role.ADMIN, Authority.Role.SUPER));

    return new ModelAndView("admin/admin/create.ftl");
  }

  @GetMapping("update/{idUser}")
  public ModelAndView update(@PathVariable Long idUser,
                             @ModelAttribute("setting") AppSetting setting,
                             Model model) {

    User user = userAdService.get(idUser);

//    List<Authority> authorities = authorityService.list(Authority.Role.USER);
    List<Authority> authorities = authorityService.list(Authority.Role.ADMIN, Authority.Role.SUPER);

    // ERCategory Checked
    if (user.getAuthorities() != null && user.getAuthorities().size() > 0) {
      authorities.forEach(authority -> {
        user.getAuthorities().forEach(_authority -> {
          if (Objects.equals(authority.getId(), _authority.getId())) {
            authority.setChecked(true);
          }
        });
      });
    }

    model.addAttribute("user", user);
    model.addAttribute("authorities", authorities);
    return new ModelAndView("admin/admin/update.ftl");
  }

  @PostMapping("create")
  public ModelAndView createSubmit(@Valid User user,
                                   @ModelAttribute("setting") AppSetting setting,
                                   BindingResult result,
                                   SessionStatus status) {

    DataBinderUtils.objectValidate(result);
    userAdService.create(user);
    status.setComplete();
    return new ModelAndView("redirect:/admin/manager");
  }

  @PostMapping("update")
  public ModelAndView updateSubmit(@Valid User user,
                                   @ModelAttribute("setting") AppSetting setting,
                                   BindingResult result,
                                   SessionStatus status) {

    DataBinderUtils.objectValidate(result);

    User updated = userAdService.update(user);
    status.setComplete();

    return new ModelAndView("redirect:/admin/manager/update/" + user.getId() + "?success");
  }

  @ResponseBody
  @PostMapping("update/password")
  public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> data) {

    Long idUser = StringUtils.isEmpty(data.get("id")) ? null : Long.valueOf(data.get("id"));
    String password = StringUtils.isEmpty(data.get("password")) ? null : data.get("password");

    if (idUser == null || password == null) {
      return ResponseEntity.badRequest().build();
    }

    userAdService.resetPassword(idUser, password);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasRole('ROLE_SUPER')")
  @PostMapping("delete")
  public ModelAndView delete(@RequestParam Long id) {

    userAdService.delete(id);
    return new ModelAndView("redirect:/admin/manager");
  }
}
