package kr.co.queenssmile.api.service.board.qna;


import kr.co.queenssmile.core.model.resbody.board.CategoryResBody;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.Locale;

public interface QnaCategoryService {
    CollectionModel<EntityModel<CategoryResBody>> categories(Locale locale);
}
