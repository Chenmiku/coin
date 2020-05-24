package kr.co.queenssmile.api.service.board.faq;

import kr.co.queenssmile.core.domain.board.faq.category.FaqCategory;

import java.util.List;
import java.util.Locale;

public interface FAQCategoryAPIService {
    List<FaqCategory> findAll(Locale locale);
}
