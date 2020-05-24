<ul class="pagination pagination-sm">
    <#-- 첫 페이징 -->
    <#if data.currentIndex != 1>
      <li>
        <a href="<@spring.url "?page=${0}&query=${data.query!}&size=${data.pageSize?c}&startDate=${startDate!}&endDate=${endDate!}${pageparam}"/>">First</a>
      </li>
    </#if>
    <#-- 이전 섹션 페이징 -->
    <#if (data.beginIndex - data.limit) gt 0>
      <li>
        <a href="<@spring.url "${((data.beginIndex - data.limit) gt 0)?then('?page=' + ((data.beginIndex - 1)-1)?c + ('&query=' + data.query + '&size=' + data.pageSize?c + '&startDate=' + (startDate!) + '&endDate=' + (endDate!))  + pageparam ,'javascript:void(0);')}"/>">
          &lt;
        </a>
      </li>
    </#if>
    <#-- Number 목록 -->
    <#list data.beginIndex..data.endIndex as i>
        <#if i <= data.pageCount>
          <li <#if data.currentIndex == i>class="active"</#if>>

            <a href="<@spring.url "${(i == data.currentIndex)?then('javascript:void(0)', '?page=' + (i - 1)?c + ('&query=' + data.query + '&size=' + data.pageSize?c + '&startDate=' + (startDate!) + '&endDate=' + (endDate!))  + pageparam )}"/>">${i?c}</a>
          </li>
        </#if>
    </#list>
    <#-- 다음 섹션 페이징 -->
    <#if (data.beginIndex + data.limit) lte data.pageCount>
      <li>
        <a href="<@spring.url "${((data.beginIndex + data.limit) lte data.pageCount)?then('?page=' + ((data.beginIndex + data.limit)-1)?c + ('&query=' + data.query + '&size=' + data.pageSize?c + '&startDate=' + (startDate!) + '&endDate=' + (endDate!)) + pageparam,'javascript:void(0);')}"/>">
          &gt;
        </a>
      </li>
    </#if>
    <#-- 마지막 페이징 -->

    <#if data.page.totalPages != 0 && data.page.totalPages != data.currentIndex>
      <li>
        <a href="<@spring.url "?page=${(data.page.totalPages - 1)?c}&query=${data.query!}&size=${data.pageSize?c}&startDate=${startDate!}&endDate=${endDate!}${pageparam}"/>">Last</a>
      </li>
    </#if>
</ul>