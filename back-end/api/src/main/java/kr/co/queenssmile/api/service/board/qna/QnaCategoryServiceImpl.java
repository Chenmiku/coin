package kr.co.queenssmile.api.service.board.qna;

import com.google.common.collect.Lists;
import kr.co.queenssmile.core.domain.board.qna.category.QnaCategory;
import kr.co.queenssmile.core.domain.board.qna.category.QnaCategoryPredicate;
import kr.co.queenssmile.core.domain.board.qna.category.QnaCategoryRepository;
import kr.co.queenssmile.core.model.resbody.board.CategoryResBody;
import kr.co.queenssmile.core.service.setting.AppSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Slf4j
@Service
public class QnaCategoryServiceImpl implements QnaCategoryService {
    @Autowired
    private QnaCategoryRepository qnaCategoryRepository;

    @Autowired
    private AppSettingService appSettingService;

    @Override
    public CollectionModel<EntityModel<CategoryResBody>> categories(Locale locale) {

        Locale defaultLocale = appSettingService.getDefaultLocale();

        List<QnaCategory> categories = Lists.newArrayList(qnaCategoryRepository.findAll(
                QnaCategoryPredicate.getInstance()
                        .active(true)
                        .locale(locale, defaultLocale)
                        .values(),
                Sort.by(Sort.Direction.ASC, "orderAscending")));

        return CollectionModel.wrap(categories.stream().map(category -> {

            category.lazy();
            category.setLocale(locale);

            CategoryResBody restCategoryResBody = new CategoryResBody();
            restCategoryResBody.setId(category.getId());
            restCategoryResBody.setName(category.getName().getValue());
            return restCategoryResBody;
        }).collect(Collectors.toList()));
    }

}
