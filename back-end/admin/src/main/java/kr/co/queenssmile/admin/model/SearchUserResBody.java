package kr.co.queenssmile.admin.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchUserResBody implements Serializable {

  private static final long serialVersionUID = -638180650304533843L;

  private Long id;
  private String email;
  private String fullName;
}
