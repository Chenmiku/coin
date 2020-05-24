package kr.co.queenssmile.api.service.board.post;

import kr.co.queenssmile.core.domain.board.post.Post;

import java.util.List;

public interface PostAPIService {
    Post get(Long id);
    List<Post> findAllByType(Post.Type type);
}
