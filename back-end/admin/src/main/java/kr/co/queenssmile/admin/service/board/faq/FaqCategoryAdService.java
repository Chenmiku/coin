package kr.co.queenssmile.admin.service.board.faq;


import kr.co.queenssmile.core.domain.board.faq.category.FaqCategory;

import java.util.Locale;

public interface FaqCategoryAdService {

  // CUD
  FaqCategory create(FaqCategory category);

  FaqCategory update(FaqCategory category);

  void delete(Long id);

  void changeOrder(Long id, String mode);

  // R
  FaqCategory get(Locale locale, Long id);

  java.util.List<FaqCategory> list(Locale locale);

  boolean isDuplicate(Locale locale, String name);
}
