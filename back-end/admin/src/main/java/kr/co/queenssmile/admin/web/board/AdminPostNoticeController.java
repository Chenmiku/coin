package kr.co.queenssmile.admin.web.board;

import kr.co.queenssmile.admin.config.freemarker.PageableModel;
import kr.co.queenssmile.admin.service.board.post.PCategoryAdService;
import kr.co.queenssmile.admin.service.board.post.PostAdService;
import kr.co.queenssmile.core.domain.board.post.Post;
import kr.co.queenssmile.core.domain.board.post.category.PCategory;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/admin/notice")
public class AdminPostNoticeController {

  private static final Post.Type TYPE = Post.Type.NOTICE;

  @Autowired
  private PostAdService postService;

  @Autowired
  private PCategoryAdService categoryService;


  @GetMapping
  public ModelAndView page(Model model,
                           @ModelAttribute("setting") AppSetting setting,
                           @PageableDefault(size = 20, sort = {"top", "regDate"}, direction = Sort.Direction.DESC) Pageable pageable,
                           @RequestParam(required = false, defaultValue = "") String query,
                           @RequestParam(required = false) String startDate,
                           @RequestParam(required = false) String endDate) {

    Filter filter = new Filter(pageable, query, startDate, endDate);
    Page<Post> page = postService.page(setting.getDefaultLocale(), filter, TYPE, null, null);

    model.addAttribute("data", PageableModel.of(page, pageable, query).toModel());
    model.addAttribute("page", pageable.getPageNumber());

    // Search filter
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);

    return new ModelAndView("admin/post-notice/list.ftl");
  }

  @GetMapping("/create")
  public ModelAndView create(Model model,
                             @ModelAttribute("setting") AppSetting setting) {
    Post post = new Post();
    post.setType(TYPE);
    post.setActive(true);
    post.setRegDate(LocalDateTime.now());

    model.addAttribute("post", post);
    model.addAttribute("categories", categoryService.list(setting.getDefaultLocale(), TYPE));

    return new ModelAndView("admin/post-notice/create.ftl");
  }

  @GetMapping("update/{id}")
  public ModelAndView update(@PathVariable Long id,
                             @ModelAttribute("setting") AppSetting setting,
                             Model model) {

    Post post = postService.get(setting.getDefaultLocale(), id);

    java.util.List<PCategory> categories = categoryService.list(setting.getDefaultLocale(), TYPE);

    // ERCategory Checked
    if (post.getCategories() != null && post.getCategories().size() > 0) {
      categories.forEach(category -> {
        post.getCategories().forEach(_category -> {
          if (Objects.equals(category.getId(), _category.getId())) {
            category.setChecked(true);
          }
        });
      });
    }

    model.addAttribute("categories", categories);
    model.addAttribute("post", post);

    return new ModelAndView("/admin/post-notice/update.ftl");
  }

  @PostMapping("create")
  public ModelAndView postCreate(@Valid Post post,
                                 org.springframework.validation.BindingResult result,
                                 SessionStatus status,
                                 @ModelAttribute("setting") AppSetting setting) {

    DataBinderUtils.objectValidate(result);

    log.debug("post ::: {}", post);

    postService.create(post);
    status.setComplete();
    return new ModelAndView("redirect:/admin/notice");
  }

  @PostMapping("update")
  public ModelAndView postUpdate(@Valid Post post,
                                 org.springframework.validation.BindingResult result,
                                 SessionStatus status) {

    DataBinderUtils.objectValidate(result);

    postService.update(post);
    status.setComplete();
    return new ModelAndView("redirect:/admin/notice/update/" + post.getId() + "?success");
  }

  @PostMapping("/delete")
  public ModelAndView delete(@RequestParam Long id) {
    postService.delete(id);
    return new ModelAndView("redirect:/admin/notice");
  }
}
