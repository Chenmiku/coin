package kr.co.queenssmile.api.web.board.post;

import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.queenssmile.api.service.board.post.PostAPIService;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.board.post.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class APINoticeController {

    @Autowired
    private PostAPIService postAPIService;

    /**
     * [notice-board-1] 리스트
     */
    @Operation(summary = "[notice-board-1] Get list (리스트)", description = "no access_token")
    @GetMapping(value = "/notice/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getNoticeAll() {

        List<Post> notices = postAPIService.findAllByType(Post.Type.NOTICE);
        return ResponseEntity.ok(
                ImmutableMap
                        .builder()
                        .put("notices", notices)
                        .put("success", true)
                        .put("message", "success")
                        .build()
        );
    }

    /**
     * [notice-board-2] 상세
     */
    @Operation(summary = "[notice-board-2] Get detail (상세)", description = "no access_token")
    @GetMapping(value = "/notice/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable Long id) {
        if(id == null) {
            throw new BadRequestException();
        }
        Post post = postAPIService.get(id);
        return ResponseEntity.ok(
                ImmutableMap
                        .builder()
                        .put("post", post)
                        .put("success", true)
                        .put("message", "success")
                        .build()
        );
    }
}
