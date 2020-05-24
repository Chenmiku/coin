<#--<#assign pageparam = "&key=value"/>-->
<#assign pageparam = ""/>
<div class="container-fluid">
    <#-- 필터 -->
  <div class="row">
    <div class="col-md-12">
      <div id="" class="panel panel-default panel-list-filter">
        <div class="panel-heading">Lọc dữ liệu

          <a href="#" data-tool="panel-collapse" data-toggle="tooltip" title="Collapse Panel" class="pull-right">
            <em class="fa fa-minus"></em>
          </a>

          <a id="initial-list-filter" href="javascript:void(0);" data-tool="panel-refresh" data-toggle="tooltip"
             title="" class="pull-right">
            <em class="fa fa-refresh"></em>
          </a>

        </div>
        <div class="panel-wrapper collapse in">
          <div class="panel-body">
            <form id="form-list-filter" action="" method="get">
                <#include "/admin/common/list/fieldset-common.ftl"/>
                <#include "/admin/common/list/fieldset-date.ftl"/>
                <#include "/admin/common/list/fieldset-search.ftl"/>
            </form>
          </div>
            <#-- 필터 실행 -->
          <div class="panel-footer">

            <button id="submit-list-filter" type="button" class="btn btn-primary">검색</button>

            <div class="btn-group">
              <button type="button" data-toggle="dropdown" class="btn dropdown-toggle btn-default">${data.pageSize} 개씩
                보기<span class="caret"></span></button>
              <ul role="menu" class="dropdown-menu">
                <li>
                  <a href="<@spring.url "?query=${data.query!}&size=10&startDate=${startDate!}&endDate=${endDate!}${pageparam}"/>">10
                    개씩 보기</a></li>
                <li>
                  <a href="<@spring.url "?query=${data.query!}&size=20&startDate=${startDate!}&endDate=${endDate!}${pageparam}"/>">20
                    개씩 보기</a></li>
                <li>
                  <a href="<@spring.url "?query=${data.query!}&size=50&startDate=${startDate!}&endDate=${endDate!}${pageparam}"/>">50
                    개씩 보기</a></li>
                <li>
                  <a href="<@spring.url "?query=${data.query!}&size=100&startDate=${startDate!}&endDate=${endDate!}${pageparam}"/>">100
                    개씩 보기</a></li>
                <li>
                  <a href="<@spring.url "?query=${data.query!}&size=500&startDate=${startDate!}&endDate=${endDate!}${pageparam}"/>">500
                    개씩 보기</a></li>
              </ul>
            </div>


              <#--<button id="excel-list-filter" type="button" data-action="<@spring.url "/admin/board/blogCategory/excel"/>" class="btn btn-labeled btn-default">-->
              <#--<span class="text">엑셀 다운로드</span>-->
              <#--<span class="btn-label btn-label-right"><i class="fa fa-file-excel-o"></i></span>-->
              <#--</button>-->
          </div>
            <#-- END : 필터 실행 -->
        </div>
      </div>
    </div>
  </div>
    <#-- END : 필터 -->
    <#-- HEADER -->
    <#assign createparam = header.url +"/create"/>
    <#include "/admin/common/list/header.ftl"/>
    <#-- END : HEADER -->
  <div class="row">
    <div class="col-md-12">
      <div class="panel panel-default">

          <#-- START table-responsive-->
        <div class="table-responsive">
          <table id="table-ext-1" class="table table-bordered table-hover">
            <colgroup>
              <col width="5%">

              <col width="10%">
              <col width="40%">
              <col width="20%">

              <col width="5%">
              <col width="5%">
              <col width="10%">
              <col width="5%">
            </colgroup>
            <thead>
            <tr>
              <th class="text-center">#</th>

              <th class="text-center">썸네일</th>
              <th class="text-center">제목</th>
              <th class="text-center">조회수</th>

              <th class="text-center">공지모드</th>
              <th class="text-center">노출여부</th>
              <th class="text-center">등록일</th>
              <th class="text-center">Actions</th>
            </tr>
            </thead>
            <tbody>

            <#if data?has_content>
                <#list data.page.content as item>
                  <tr>
                    <td class="text-center">no.${data.firstNo- (item_index + 1)}<br/>id.${item.id?c}</td>
                    <td class="text-center">
                        <#if item.thumbnail?has_content>
                          <img src="${item.thumbnail}" style="max-height: 60px;"/>
                        <#else>
                          -
                        </#if>
                    </td>
                    <td class="text-left">
                        <#if international>
                          <ul style="padding-left: 13px; margin-bottom: 0px">
                              <#if im.koKr>
                              <li <#if !item.internationalMode.koKr>class="inactive"</#if>>국문
                                : <#if item.internationalMode.koKr>${item.title.textKoKr!}<#else>비활성</#if></li></#if>
                              <#if im.enUs>
                              <li <#if !item.internationalMode.enUs>class="inactive"</#if>>영문
                                : <#if item.internationalMode.enUs>${item.title.textEnUs!}<#else>비활성</#if></li></#if>
                              <#if im.zhCn>
                              <li <#if !item.internationalMode.zhCn>class="inactive"</#if>>간체(중)
                                : <#if item.internationalMode.zhCn>${item.title.textZhCn!}<#else>비활성</#if></li></#if>
                              <#if im.zhTw>
                              <li <#if !item.internationalMode.zhTw>class="inactive"</#if>>번체(중)
                                : <#if item.internationalMode.zhTw>${item.title.textZhTw!}<#else>비활성</#if></li></#if>
                              <#if im.jaJp>
                              <li <#if !item.internationalMode.jaJp>class="inactive"</#if>>일문
                                : <#if item.internationalMode.jaJp>${item.title.textJaJp!}<#else>비활성</#if></li></#if>
                          </ul>
                        <#else>
                            ${item.title.value!}
                        </#if>
                    </td>
                    <td class="text-center">${item.pageView}</td>

                    <td class="text-center"><#if item.top>
                        <div class="label label-success">활성</div><#else>
                        <div class="label label-warning">비활성</div></#if></td>
                    <td class="text-center"><#if item.active>
                        <div class="label label-success">활성</div><#else>
                        <div class="label label-warning">비활성</div></#if></td>
                    <td class="text-center">${item.regDate.format('yyyy.MM.dd HH:mm:ss')}</td>
                    <td class="text-center">
                      <a href="<@spring.url header.url + "/update/${item.id?c}"/>" class="btn btn-sm btn-default"
                         data-toggle="tooltip" data-placement="top" title="상세보기 및 수정">
                        <em class="fa fa-pencil"></em>
                      </a>
                    </td>
                  </tr>
                </#list>
            </#if>
            </tbody>
          </table>
        </div>
          <#-- END table-responsive-->

          <#-- TABLE FOOTER -->
        <div class="panel-footer">
          <div class="row">
            <div class="col-lg-12 text-center">
                <#include "/admin/common/list/pagination.ftl"/>
            </div>
          </div>
        </div>
          <#-- END : TABLE FOOTER -->
      </div>
    </div>
  </div>
</div>