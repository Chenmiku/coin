package kr.co.queenssmile.admin.web.user;

import kr.co.queenssmile.admin.config.freemarker.PageableModel;
import kr.co.queenssmile.admin.service.user.UserAdService;
import kr.co.queenssmile.core.domain.setting.AppSetting;
import kr.co.queenssmile.core.domain.user.Authority;
import kr.co.queenssmile.core.domain.user.Gender;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

  @Autowired
  private UserAdService userAdService;

  @Autowired
  private AuthorityService authorityService;

  @GetMapping
  public ModelAndView page(Model model,
                           @ModelAttribute("setting") AppSetting setting,
                           @PageableDefault(size = 20, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable,
                           @RequestParam(required = false, defaultValue = "") String query,
                           @RequestParam(required = false) String startDate,
                           @RequestParam(required = false) String endDate,
                           @RequestParam(required = false) Authority.Role role) {

    Filter filter = new Filter(pageable, query, startDate, endDate);
    Page<User> page = userAdService.page(filter, role);

    // Default
    model.addAttribute("data", PageableModel.of(page, pageable, query).toModel());
    model.addAttribute("page", pageable.getPageNumber());

    model.addAttribute("roles", Authority.Role.values());
    model.addAttribute("role", role);

    // Search filter
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);

    return new ModelAndView("admin/user/list.ftl");
  }

//  @PostMapping("/excel")
//  public ModelAndView excel(Model model,
//                            @ModelAttribute("setting") AppSetting setting,
//                            @PageableDefault(size = 20, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable,
//                            @RequestParam(required = false, defaultValue = "all") String mode,
//                            @RequestParam(required = false, defaultValue = "") String query,
//                            @RequestParam(required = false) String startDate,
//                            @RequestParam(required = false) String endDate) {
//
//    Filter filter = new Filter(pageable, query, startDate, endDate);
//
//    List<User> list = userAdService.list(filter);
//
//    if (list == null || list.size() == 0) {
//      return new ModelAndView("redirect:/admin/user");
//    }
//    model.addAttribute("list", list);
//    model.addAttribute("sheetName", "USER");
////            model.addAttribute("fileName", "파일명");
//
//    return new ModelAndView(new ExcelView());
//  }

  @GetMapping("create")
  public ModelAndView create(Model model,
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

    user.setRole(Authority.Role.USER);
    model.addAttribute("user", user);
    model.addAttribute("genders", Gender.values());

    model.addAttribute("authorities", authorityService.list(Authority.Role.USER));

    model.addAttribute("locales", Arrays.asList(Locale.getAvailableLocales()));

    return new ModelAndView("admin/user/create.ftl");
  }

  @GetMapping("update/{idUser}")
  public ModelAndView update(@PathVariable Long idUser,
                             @ModelAttribute("setting") AppSetting setting,
                             Model model) {

    User user = userAdService.get(idUser);

    List<Authority> authorities = authorityService.list(Authority.Role.USER);

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
    model.addAttribute("genders", Gender.values());
    model.addAttribute("authorities", authorities);
    model.addAttribute("locales", Arrays.asList(Locale.getAvailableLocales()));

    return new ModelAndView("admin/user/update.ftl");
  }

  @PostMapping("create")
  public ModelAndView createSubmit(@Valid User user,
                                   @ModelAttribute("setting") AppSetting setting,
                                   org.springframework.validation.BindingResult result,
                                   SessionStatus status) {

    DataBinderUtils.objectValidate(result);

    userAdService.create(user);
    status.setComplete();
    return new ModelAndView("redirect:/admin/user");
  }

  @PostMapping("update")
  public ModelAndView updateSubmit(@Valid User user,
                                   @ModelAttribute("setting") AppSetting setting,
                                   org.springframework.validation.BindingResult result,
                                   SessionStatus status) {

    DataBinderUtils.objectValidate(result);

    userAdService.update(user);
    status.setComplete();
    return new ModelAndView("redirect:/admin/user/update/" + user.getId() + "?success");
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
  @PostMapping("leave")
  public ModelAndView leave(HttpServletRequest request,
                            @RequestParam Long id) {
    userAdService.leave(id);
    return new ModelAndView("redirect:" + request.getHeader("referer"));
  }

  @PreAuthorize("hasRole('ROLE_SUPER')")
  @PostMapping("remove-privacy")
  public ModelAndView removePrivacy(HttpServletRequest request,
                                    @RequestParam Long id) {
    userAdService.removePrivacy(id);
    return new ModelAndView("redirect:" + request.getHeader("referer"));
  }

  @PreAuthorize("hasRole('ROLE_SUPER')")
  @PostMapping("restore-leave")
  public ModelAndView restoreLeave(HttpServletRequest request,
                                   @RequestParam Long id) {
    userAdService.restoreLeave(id);
    return new ModelAndView("redirect:" + request.getHeader("referer"));
  }

  @PreAuthorize("hasRole('ROLE_SUPER')")
  @PostMapping("delete")
  public ModelAndView delete(@RequestParam Long id) {

    userAdService.delete(id);
    return new ModelAndView("redirect:/admin/user");
  }

  @PostMapping(value = "/duplicate/{type}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> duplicate(@RequestBody MultiValueMap<String, String> body,
                                     @PathVariable String type) {

    Map<String, Object> result = new HashMap<>();

    if (body == null || body.getFirst(type) == null) {
      return ResponseEntity.badRequest().build();
    }

    String value = body.getFirst(type);

    if (type.equals("email")) {
      if (userAdService.isDuplicateEmail(value)) {
        result.put("result", "duplicate");
      } else {
        result.put("result", "success");
      }

    } else if (type.equals("mobile")) {
      if (userAdService.isDuplicateMobile(value)) {
        result.put("result", "duplicate");
      } else {
        result.put("result", "success");
      }
    }

    return ResponseEntity.ok(result);
  }

  @PostMapping(value = "/{idUser}/duplicate/{type}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> duplicateUpdate(@RequestBody MultiValueMap<String, String> body,
                                           @PathVariable Long idUser,
                                           @PathVariable String type) {

    Map<String, Object> result = new HashMap<>();

    if (body == null || body.getFirst(type) == null) {
      return ResponseEntity.badRequest().build();
    }

    String value = body.getFirst(type);

    User user = userAdService.get(idUser);

    if (type.equals("email")) {
      String email = user.getEmail();
      if (value.equals(email)) {
        result.put("result", "success");
      } else if (userAdService.isDuplicateEmail(value)) {
        result.put("result", "duplicate");
      } else {
        result.put("result", "success");
      }

    } else if (type.equals("mobile")) {
      String mobile = user.getMobile();
      if (value.equals(mobile)) {
        result.put("result", "success");
      } else if (userAdService.isDuplicateMobile(value)) {
        result.put("result", "duplicate");
      } else {
        result.put("result", "success");
      }
    }

    return ResponseEntity.ok(result);
  }
  
}
