package kr.co.queenssmile.admin.service.board.qna;

import kr.co.queenssmile.core.domain.board.qna.Qna;
import kr.co.queenssmile.core.model.Filter;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

public interface QnaAdService {

  // CUD
  Qna create(Qna qna);

  Qna update(Qna qna, HttpServletRequest request);

  void delete(Long id);

  // R
  Qna get(Long id);

  Page<Qna> page(Filter filter, Boolean isActive);
}
