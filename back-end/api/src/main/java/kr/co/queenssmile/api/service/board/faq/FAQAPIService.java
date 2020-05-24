package kr.co.queenssmile.api.service.board.faq;

import kr.co.queenssmile.core.model.Filter;
import kr.co.queenssmile.core.model.resbody.board.CategoryResBody;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public interface FAQAPIService {

    PagedModel<?> pagedResources(Locale locale, HttpServletRequest request, Filter filter, Long idCategory);
    CollectionModel<EntityModel<CategoryResBody>> categories(Locale locale, HttpServletRequest request);
}
