<div class="row">
  <div class="col-md-12">
    <header class="clearfix pb-lg mb-sm">
        <#-- 목록 상태 -->
      <div class="pull-left">
        <div class="list-total">총 ${data.page.totalElements!}개,&nbsp;&nbsp;${data.currentIndex}/${data.page.totalPages}
          페이지
        </div>
      </div>
        <#-- END : 목록 상태 -->
        <#if createparam?has_content>
        <#-- 등록 버튼 -->
          <div class="pull-right">
            <a href="<@spring.url createparam/>" class="btn btn-primary btn-lg">Đăng kí mới</a>
          </div>
        <#-- 등록 버튼 -->
        </#if>
    </header>
  </div>
</div>