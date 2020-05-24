package kr.co.queenssmile.core.model;

import kr.co.queenssmile.core.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Filter implements java.io.Serializable {

    private static final long serialVersionUID = 7986177657377217045L;

    final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Pageable pageable;
    private String query;
    private String startDate;
    private String endDate;

    public Filter(Pageable pageable) {
        this.pageable = pageable;
        this.query = "";
        this.startDate = "";
        this.endDate = "";
    }

    public Filter(Pageable pageable, String query) {
        this.pageable = pageable;
        this.query = query;
        this.startDate = "";
        this.endDate = "";
    }

    public Filter(Pageable pageable, String startDate, String endDate) {
        this.pageable = pageable;
        this.query = "";
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Filter(String startDate, String endDate) {
        this.pageable = null;
        this.query = "";
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Filter(String query, String startDate, String endDate) {
        this.pageable = null;
        this.query = query;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        try {
            return StringUtils.isEmpty(this.startDate) ? null : LocalDateTime.parse(this.startDate, FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    public LocalDateTime getEndDate() {
        try {
            return StringUtils.isEmpty(this.endDate) ? null : LocalDateTime.parse(this.endDate, FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }
}
