package kr.co.queenssmile.api.web.board.faq;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.queenssmile.api.service.board.faq.FAQAPIService;
import kr.co.queenssmile.api.service.board.faq.FAQCategoryAPIService;
import kr.co.queenssmile.core.model.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api/v1/faq")
public class APIFAQController {
    @Autowired
    private FAQCategoryAPIService faqCategoryAPIService;

    @Autowired
    private FAQAPIService faqapiService;

    /**
     * [faq-category-1] 카테고리
     */
    @Operation(summary = "[faq-category-1] Get list faq category (카테고리)", description = "get list category faq")
    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFaqCategoryAll(Locale locale,
                                            HttpServletRequest request) {

        return ResponseEntity.ok(faqapiService.categories(locale, request));
    }

    /**
     * [faq-list-2] 리스트
     */
    @Operation(summary = "[faq-list-2] Get list faq (리스트)", description = "get list all faq")
    @GetMapping
    public ResponseEntity<?> faqs(@PageableDefault(size = 10, sort = {"orderAscending"}, direction = Sort.Direction.ASC) Pageable pageable,
                                  @RequestParam(required = false, defaultValue = "") String query,
                                  @RequestParam(required = false) String startDate,
                                  @RequestParam(required = false) String endDate,
                                  @RequestParam(required = false) Long idCategory,
                                  HttpServletRequest request,
                                  Locale locale) {

        Filter filter = new Filter(pageable, query, startDate, endDate);
        return ResponseEntity.ok(faqapiService.pagedResources(locale, request, filter, idCategory));
    }
}
