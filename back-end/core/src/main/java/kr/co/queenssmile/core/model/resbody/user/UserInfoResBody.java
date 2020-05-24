package kr.co.queenssmile.core.model.resbody.user;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

@Relation(value = "userInfo")
@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResBody implements Serializable {

    private static final long serialVersionUID = 1309234549229035608L;

    private Long id;
    private String name;
    private String email;
    private String mobile;
}
