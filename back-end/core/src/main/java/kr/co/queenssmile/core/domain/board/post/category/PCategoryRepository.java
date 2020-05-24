package kr.co.queenssmile.core.domain.board.post.category;

import kr.co.queenssmile.core.domain.board.post.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PCategoryRepository extends
    PagingAndSortingRepository<PCategory, Long>,
    QuerydslPredicateExecutor<PCategory>,
    PCategoryRepositoryCustom {

  List<PCategory> findAllByType(Post.Type type, Sort sort);

  @Query("select max(c.orderAscending) from PostCategory c where c.type = ?1")
  Long highestOrder(Post.Type type);

  @Query("from PostCategory c where c.orderAscending < ?1 and c.type = ?2 order by c.orderAscending desc")
  List<PCategory> previous(long orderAscending, Post.Type type, Pageable pageable);

  @Query("from PostCategory c where c.orderAscending > ?1 and c.type = ?2 order by c.orderAscending asc")
  List<PCategory> next(long orderAscending, Post.Type type, Pageable pageable);

  List<PCategory> findByTypeOrderByOrderAscendingAsc(Post.Type type);
}

