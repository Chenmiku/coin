package kr.co.queenssmile.core.model;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ModelSample implements Serializable {

  private long id;
  private String name;
}
