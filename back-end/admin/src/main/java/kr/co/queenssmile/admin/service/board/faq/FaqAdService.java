package kr.co.queenssmile.admin.service.board.faq;


import kr.co.queenssmile.core.domain.board.faq.Faq;
import kr.co.queenssmile.core.model.Filter;
import org.springframework.data.domain.Page;

import java.util.Locale;

public interface FaqAdService {

    // CUD
    Faq create(Faq faq);
    Faq update(Faq faq);
    void delete(Long id);
    void changeOrder(Long id, String mode);

    // R
    Faq get(Locale locale, Long id);
    Faq get(Locale locale, Long id, Boolean isActive);
    Page<Faq> page(Locale locale, Filter filter, Boolean isActive, Long idCategory);
}
