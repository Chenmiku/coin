package kr.co.queenssmile.api.service.board.faq;

import com.google.common.collect.Lists;
import kr.co.queenssmile.core.domain.board.faq.category.FaqCategory;
import kr.co.queenssmile.core.domain.board.faq.category.FaqCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class FAQCategoryAPIServiceImpl implements FAQCategoryAPIService {
    @Autowired
    private FaqCategoryRepository faqCategoryRepository;


    @Override
    @Transactional
    public List<FaqCategory> findAll(Locale locale) {

        List<FaqCategory> faqCategories = Lists.newArrayList(faqCategoryRepository.findAll(Sort.by(Sort.Direction.ASC, "orderAscending")));
        faqCategories.forEach(category -> category.setLocale(locale));
        return faqCategories;
    }
}
