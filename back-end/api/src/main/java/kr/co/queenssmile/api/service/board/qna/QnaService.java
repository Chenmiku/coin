package kr.co.queenssmile.api.service.board.qna;

import kr.co.queenssmile.core.domain.board.qna.Qna;
import kr.co.queenssmile.core.model.Filter;
import kr.co.queenssmile.core.model.reqbody.board.qna.QuestionReqBody;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public interface QnaService {

  // CUD
  Qna create(Qna qna);

  Qna update(Qna qna, HttpServletRequest request);

  void delete(Long id);

  // R
  Qna get(Long id);

  Page<Qna> page(Filter filter, Boolean isActive);

  // Rest
  PagedModel<?> pagedResources(Locale locale, HttpServletRequest request, Filter filter, Long idCategory, String email);

  EntityModel<?> resource(Locale locale, HttpServletRequest request, Long id, String email);

  QuestionReqBody post(QuestionReqBody questionReqBody, String email);

  QuestionReqBody postNoMember(QuestionReqBody questionReqBody);

  void delete(Long id, String email);
}
