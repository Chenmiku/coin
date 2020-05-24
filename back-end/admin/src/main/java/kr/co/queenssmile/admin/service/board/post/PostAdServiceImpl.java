package kr.co.queenssmile.admin.service.board.post;

import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.config.exception.crud.UpdateErrorException;
import kr.co.queenssmile.core.domain.board.post.Post;
import kr.co.queenssmile.core.domain.board.post.PostPredicate;
import kr.co.queenssmile.core.domain.board.post.PostRepository;
import kr.co.queenssmile.core.model.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Slf4j
@Service
public class PostAdServiceImpl implements PostAdService {

  @Autowired
  private PostRepository postRepository;

  @Override
  @Transactional
  public Post create(Post post) {
    post.uploadFiles();
    return postRepository.save(post);
  }

  @Override
  @Transactional
  public Post update(Post post) {

    if (post.getId() == null) {
      throw new BadRequestException();
    }

    return postRepository.findById(post.getId())
        .map(ori -> {
          BeanUtils.copyProperties(post, ori, Post.IGNORE_PROPERTIES);
          return postRepository.save(ori);
        }).orElseThrow(() -> new UpdateErrorException(post.getId(), Post.class.getName()));
  }

  @Override
  @Transactional(readOnly = true)
  public Post get(Locale locale, Long id) {

    return postRepository.findById(id)
        .map(post -> {
          post.lazy();
          post.setLocale(locale);
          return post;
        }).orElse(null);
  }

  @Override
  @Transactional
  public void delete(Long id) {

    postRepository.findById(id).ifPresent(post -> {
      post.delete();
      postRepository.delete(post);
    });
  }

  @Override
  @Transactional(readOnly = true)
  public Page<Post> page(Locale locale,
                           Filter filter,
                           Post.Type type,
                           Boolean isActive,
                           Long idCategory) {

    Page<Post> page = postRepository.findAll(
        PostPredicate.getInstance()
            .search(filter.getQuery())
            .startDate(filter.getStartDate())
            .endDate(filter.getEndDate())
            .type(type)
            .active(isActive)
            .category(idCategory)
            .values(),
        filter.getPageable());

    page.forEach(post -> {
      post.lazy();
      post.setLocale(locale);
    });
    return page;
  }

}
