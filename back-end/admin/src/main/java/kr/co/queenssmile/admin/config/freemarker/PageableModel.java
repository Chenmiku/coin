package kr.co.queenssmile.admin.config.freemarker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;

@Data
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
public class PageableModel {

  public static final int PAGE_LIMIT_DEFAULT = 5;

  @NonNull
  private Page<?> page;

  @NonNull
  private Pageable pageable;

  @NonNull
  private String query;

  private int limit;

  public static PageRequest pageRequest(int currentPage, int pageSize) {
    return PageRequest.of(currentPage - 1, pageSize);
  }

  public static PageRequest pageRequest(int currentPage, int pageSize, Sort.Direction direction, String... properties) {
    return PageRequest.of(currentPage - 1, pageSize, direction, properties);
  }

  public static PageRequest pageRequest(int currentPage, int pageSize, Sort sort) {
    return PageRequest.of(currentPage - 1, pageSize, sort);
  }

  public java.util.Map<String, Object> toModel() {

    int pageSize = pageable.getPageSize();
    int limit = this.limit == 0 ? PAGE_LIMIT_DEFAULT : this.limit;

    java.util.Map<String, Object> model = new HashMap<>();

    if (page != null) {
      int current = page.getNumber() + 1;
      int temp = (current - 1) % limit;
      int begin = current - temp;
      int end = begin + limit - 1;
      int firstNo = (int) (page.getTotalElements() + 1) - ((current - 1) * pageSize);

      int pageCount;
      if (page.getTotalElements() % pageSize == 0) {
        pageCount = (int) page.getTotalElements() / pageSize;
      } else {
        pageCount = (int) page.getTotalElements() / pageSize + 1;
      }

      model.put("query", query);


      model.put("page", page);
      model.put("pageCount", pageCount);
      model.put("pageSize", pageSize);
      model.put("limit", limit);
      model.put("firstNo", firstNo); // th:each="item, iterStat : ${page} | ${firstNo - (iterStat.index + 1)}
      model.put("beginIndex", begin);
      model.put("endIndex", end);
      model.put("currentIndex", current);
      model.put("next", page.hasNext());
      model.put("previous", page.hasPrevious());
      model.put("nextPageable", page.nextPageable());
      model.put("previousPageable", page.previousPageable());

    }
    return model;
  }
}
