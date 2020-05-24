package kr.co.queenssmile.admin.service.board.comment;

import kr.co.queenssmile.core.domain.board.comment.Comment;
import kr.co.queenssmile.core.model.Filter;
import org.springframework.data.domain.Page;

import java.util.Locale;

public interface CommentAdService {

  // CUD
  Comment create(Comment comment);

  Comment update(Comment comment);

  void delete(Long id);

  // R
  Comment get(Locale locale, Long id);

  Page<Comment> pageByEvent(Locale locale, Filter filter);
}
