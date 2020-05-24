package kr.co.queenssmile.core.domain.file;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FileEntityRepository extends
    PagingAndSortingRepository<FileEntity, Long>,
    QuerydslPredicateExecutor<FileEntity> {

  FileEntity findOneByFilename(String filename);
}
