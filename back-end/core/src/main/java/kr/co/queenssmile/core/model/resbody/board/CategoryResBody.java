package kr.co.queenssmile.core.model.resbody.board;

import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "category", collectionRelation = "categories")
@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResBody extends BaseResponseBody {

    private static final long serialVersionUID = 6898460053912840759L;

    private Long id;
    private String name;
    private String image;
}
