package kr.co.queenssmile.admin.service.board.post;

import kr.co.queenssmile.core.domain.board.post.Post;
import kr.co.queenssmile.core.domain.board.post.category.PCategory;

import java.util.List;
import java.util.Locale;

public interface PCategoryAdService {

  // CUD
  PCategory create(PCategory category);

  PCategory update(PCategory category);

  void delete(Long id);

  void changeOrder(Long id, String mode);

  // R
  PCategory get(Locale locale, Long id);

  List<PCategory> list(Locale locale, Post.Type type);

  boolean isDuplicate(Locale locale, String name, Post.Type type);
}
