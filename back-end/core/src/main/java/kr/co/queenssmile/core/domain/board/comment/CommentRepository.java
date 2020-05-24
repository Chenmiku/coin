package kr.co.queenssmile.core.domain.board.comment;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends
    PagingAndSortingRepository<Comment, Long>,
    QuerydslPredicateExecutor<Comment> {
}

