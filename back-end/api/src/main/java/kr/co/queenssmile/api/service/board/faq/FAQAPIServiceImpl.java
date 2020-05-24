package kr.co.queenssmile.api.service.board.faq;

import com.google.common.collect.Lists;
import kr.co.queenssmile.core.domain.board.faq.Faq;
import kr.co.queenssmile.core.domain.board.faq.FaqPredicate;
import kr.co.queenssmile.core.domain.board.faq.FaqRepository;
import kr.co.queenssmile.core.domain.board.faq.category.FaqCategory;
import kr.co.queenssmile.core.domain.board.faq.category.FaqCategoryPredicate;
import kr.co.queenssmile.core.domain.board.faq.category.FaqCategoryRepository;
import kr.co.queenssmile.core.model.Filter;
import kr.co.queenssmile.core.model.resbody.board.CategoryResBody;
import kr.co.queenssmile.core.model.resbody.board.FaqResBody;
import kr.co.queenssmile.core.service.setting.AppSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FAQAPIServiceImpl implements FAQAPIService {
    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private AppSettingService appSettingService;

    @Autowired
    private FaqCategoryRepository faqCategoryRepository;

    @Autowired
    private PagedResourcesAssembler pagedResourcesAssembler;


    @Override
    @Transactional
    public PagedModel<?> pagedResources(Locale locale, HttpServletRequest request, Filter filter, Long idCategory) {

        Locale defaultLocale = appSettingService.getDefaultLocale();

        Page<Faq> faqPage = faqRepository.findAll(
                FaqPredicate.getInstance()
                        .search(filter.getQuery())
                        .startDate(filter.getStartDate())
                        .endDate(filter.getEndDate())
                        .active(true)
                        .category(idCategory)
                        .locale(locale, defaultLocale)
                        .values()
                ,
                filter.getPageable());

        List<FaqResBody> list = faqPage.getContent().stream()
                .map(faq -> {
                    faq.setLocale(locale);

                    FaqResBody restFaq = new FaqResBody();
                    restFaq.setId(faq.getId());
                    restFaq.setQuestion(faq.getQuestion().getValue());
                    restFaq.setAnswer(faq.getAnswer().getValue());

                    if (faq.getCategories() != null) {
                        restFaq.setCategories(faq.getCategories().stream()
                                .filter(category -> category.isLocale(locale, defaultLocale))
                                .map(category -> category.getName().getValue())
                                .collect(Collectors.joining(",")));
                    }

                    return restFaq;
                }).collect(Collectors.toList());

        Page<FaqResBody> page = new PageImpl<>(list, filter.getPageable(), faqPage.getTotalElements());

        return pagedResourcesAssembler.toModel(page);
    }



    @Override
    public CollectionModel<EntityModel<CategoryResBody>> categories(Locale locale, HttpServletRequest request) {

        Locale defaultLocale = appSettingService.getDefaultLocale();

        List<FaqCategory> categories = Lists.newArrayList(faqCategoryRepository.findAll(
                FaqCategoryPredicate.getInstance()
                        .active(true)
                        .locale(locale, defaultLocale)
                        .values(), Sort.by(Sort.Direction.ASC, "orderAscending")));

        return CollectionModel.wrap(categories.stream().map(category -> {
            category.setLocale(locale);

            CategoryResBody restCategoryResBody = new CategoryResBody();
            restCategoryResBody.setId(category.getId());
            restCategoryResBody.setName(category.getName().getValue());
            restCategoryResBody.setImage(category.getImage());
            return restCategoryResBody;

        }).collect(Collectors.toList()));
    }
}
