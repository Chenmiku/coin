package kr.co.queenssmile.admin.service.board.post;

import kr.co.queenssmile.core.domain.board.post.Post;
import kr.co.queenssmile.core.model.Filter;
import org.springframework.data.domain.Page;

import java.util.Locale;

public interface PostAdService {

    // CUD
    Post create(Post post);
    Post update(Post post);
    void delete(Long id);

    // R
    Post get(Locale locale, Long id);
    Page<Post> page(Locale locale,
                      Filter filter,
                      Post.Type type,
                      Boolean isActive,
                      Long idCategory);

}
