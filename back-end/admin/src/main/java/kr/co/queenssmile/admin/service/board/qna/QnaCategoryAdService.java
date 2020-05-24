package kr.co.queenssmile.admin.service.board.qna;

import kr.co.queenssmile.core.domain.board.qna.category.QnaCategory;

import java.util.List;
import java.util.Locale;

public interface QnaCategoryAdService {

  // CUD
  QnaCategory create(QnaCategory category);

  QnaCategory update(QnaCategory category);

  void delete(Long id);

  void changeOrder(Long id, String mode);

  // R
  QnaCategory get(Locale locale, Long id);

  List<QnaCategory> list(Locale locale);

  boolean isDuplicate(Locale locale, String name);
}
