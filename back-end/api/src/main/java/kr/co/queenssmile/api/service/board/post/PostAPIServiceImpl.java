package kr.co.queenssmile.api.service.board.post;

import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.board.post.Post;
import kr.co.queenssmile.core.domain.board.post.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class PostAPIServiceImpl implements PostAPIService {
    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional
    public Post get(Long id) {
        return postRepository.findById(id)
                .map(post -> {
                    post.lazy();
                    return post;
                }).orElse(null);
    }

    @Override
    @Transactional
    public List<Post> findAllByType(Post.Type type) {

        List<Post> post = postRepository.findAllByType(type);

        if (post == null) {
            throw new BadRequestException();
        }
        return post;
    }
}
