package kr.co.queenssmile.core.domain.board.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;

public interface PostRepository extends
    PagingAndSortingRepository<Post, Long>,
    QuerydslPredicateExecutor<Post> {

  @Query("select p.id from Post p where p.regDate < ?1 and p.type = ?2 order by p.regDate desc")
  java.util.List<Long> previous(LocalDateTime regDate, Post.Type type, Pageable pageable);

  @Query("select p.id from Post p where p.regDate > ?1 and p.type = ?2 order by p.regDate asc")
  java.util.List<Long> next(LocalDateTime regDate, Post.Type type, Pageable pageable);

  @Query("from Post p where p.type = ?1 order by p.regDate desc")
  java.util.List<Post> findAllByType(Post.Type type);
}
