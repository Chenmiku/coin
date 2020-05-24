package kr.co.queenssmile.admin.web.board;

import com.google.common.collect.Lists;
import kr.co.queenssmile.admin.config.freemarker.PageableModel;
import kr.co.queenssmile.admin.service.board.qna.QnaAdService;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.board.qna.Qna;
import kr.co.queenssmile.core.domain.board.qna.QnaNoMember;
import kr.co.queenssmile.core.domain.board.qna.category.QnaCategory;
import kr.co.queenssmile.core.domain.board.qna.category.QnaCategoryRepository;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/admin/qna")
public class AdminQnaController {

    @Autowired
    private QnaAdService qnaService;

    @Autowired
    private QnaCategoryRepository categoryRepository;

    @GetMapping
    public ModelAndView page(Model model,
                             @PageableDefault(size = 20, sort = {"createdDate"}, direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestParam(required = false, defaultValue = "") String query,
                             @RequestParam(required = false) String startDate,
                             @RequestParam(required = false) String endDate) {

        Filter filter = new Filter(pageable, query, startDate, endDate);
        Page<Qna> page = qnaService.page(filter, null);

        model.addAttribute("data", PageableModel.of(page, pageable, query).toModel());
        model.addAttribute("page", pageable.getPageNumber());

        // Search filter
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return new ModelAndView("admin/qna/list.ftl");
    }

    @GetMapping("/create")
    public ModelAndView create(Model model) {
        Qna qna = new Qna();
        qna.setActive(true);
        QnaNoMember qnaNoMember = new QnaNoMember();
        qna.setQnaNoMember(qnaNoMember);

        model.addAttribute("qna", qna);
        model.addAttribute("categories", categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "orderAscending")));
        model.addAttribute("locales", Arrays.asList(Locale.getAvailableLocales()));

        return new ModelAndView("admin/qna/create.ftl");
    }

    @GetMapping("update/{id}")
    public ModelAndView update(@PathVariable Long id,
                               Model model) {

        Qna qna = qnaService.get(id);

        List<QnaCategory> categories = Lists.newArrayList(categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "orderAscending")));

        // ERCategory Checked
        if (qna.getCategories() != null && qna.getCategories().size() > 0) {
            categories.forEach(category -> {
                qna.getCategories().forEach(_category -> {
                    if (Objects.equals(category.getId(), _category.getId())) {
                        category.setChecked(true);
                    }
                });
            });
        }

        model.addAttribute("categories", categories);
        model.addAttribute("qna", qna);
        model.addAttribute("locales", Arrays.asList(Locale.getAvailableLocales()));

        return new ModelAndView("/admin/qna/update.ftl");
    }

    @PostMapping("create")
    public ModelAndView qnaCreate(@Valid Qna qna,
                                  org.springframework.validation.BindingResult result,
                                  SessionStatus status) {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.error("name -> " + objectError.getObjectName() + ", msg -> " + objectError.getDefaultMessage());
            });
            throw new BadRequestException(result.getObjectName());
        }

        log.debug("qna ::: {}", qna);

        qnaService.create(qna);
        status.setComplete();
        return new ModelAndView("redirect:/admin/qna");
    }

    @PostMapping("update")
    public ModelAndView qnaUpdate(@Valid Qna qna,
                                  org.springframework.validation.BindingResult result,
                                  SessionStatus status,
                                  HttpServletRequest request) {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.error("name -> " + objectError.getObjectName() + ", msg -> " + objectError.getDefaultMessage());
            });
            throw new BadRequestException(result.getObjectName());
        }
        qnaService.update(qna, request);
        status.setComplete();
        return new ModelAndView("redirect:/admin/qna/update/" + qna.getId() + "?success");
    }

    @PostMapping("/delete")
    public ModelAndView delete(@RequestParam Long id) {
        qnaService.delete(id);
        return new ModelAndView("redirect:/admin/qna");
    }
}
