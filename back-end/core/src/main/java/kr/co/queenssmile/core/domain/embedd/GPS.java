package kr.co.queenssmile.core.domain.embedd;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
@ToString
public class GPS implements java.io.Serializable {

    private static final long serialVersionUID = -2998172682294014417L;

    @Column(name = "gLatitude" , columnDefinition = "DOUBLE default 0.0")
    private double latitude; // 위도 (가로축)

    @Column(name = "gLongitude", columnDefinition = "DOUBLE default 0.0")
    private double longitude; // 경도 (세로축)
}
