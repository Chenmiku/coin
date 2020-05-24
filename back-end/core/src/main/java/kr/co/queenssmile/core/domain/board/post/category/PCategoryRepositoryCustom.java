package kr.co.queenssmile.core.domain.board.post.category;

import kr.co.queenssmile.core.domain.board.post.Post;

public interface PCategoryRepositoryCustom {

    boolean existsByNameAndTypeAndKoKr(String name, Post.Type type);

    boolean existsByNameAndTypeAndEnUs(String name, Post.Type type);

    boolean existsByNameAndTypeAndZhCn(String name, Post.Type type);

    boolean existsByNameAndTypeAndZhTw(String name, Post.Type type);

    boolean existsByNameAndTypeAndJaJp(String name, Post.Type type);
}
