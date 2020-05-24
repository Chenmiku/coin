<#--<#assign pageparam = "&key=value"/>-->
<#assign pageparam = ""/>
<div class="container-fluid">
    <#-- HEADER -->
    <#assign createparam = header.url +"/create"/>
  <div class="row">
    <div class="col-md-12">
      <header class="clearfix pb-lg mb-sm">
          <#-- 목록 상태 -->
        <div class="pull-left">
          <div class="list-total">총 ${list?size!}개</div>
        </div>
          <#-- END : 목록 상태 -->
          <#if createparam?has_content>
          <#-- 등록 버튼 -->
            <div class="pull-right">
              <a href="<@spring.url createparam/>" class="btn btn-primary btn-lg">새로등록</a>
            </div>
          <#-- 등록 버튼 -->
          </#if>
      </header>
    </div>
  </div>
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
              <col width="60%">
              <col width="10%">
              <col width="10%">
              <col width="5%">
            </colgroup>
            <thead>
            <tr>
              <th class="text-center">#</th>
              <th style="width: 100px" class="text-center">유형</th>
              <th style="width:auto" class="text-center">이름</th>
              <th style="width:60px" class="text-center">순서</th>
              <th style="width:80px" class="text-center">노출여부</th>
              <th style="width:200px" class="text-center">등록일</th>
              <th style="width:80px" class="text-center">#</th>
            </tr>
            </thead>
            <tbody>

            <#if list?has_content>
                <#list list as item>
                  <tr>
                    <td class="text-center">${list?size - (item_index)}</td>
                    <td class="text-center">${item.type.value!}</td>
                    <td class="text-left">
                        <#if international>
                          <ul style="padding-left: 13px; margin-bottom: 0px">
                              <#if im.koKr>
                              <li <#if !item.internationalMode.koKr>class="inactive"</#if>>국문
                                : <#if item.internationalMode.koKr>${item.name.textKoKr!}<#else>비활성</#if></li></#if>
                              <#if im.enUs>
                              <li <#if !item.internationalMode.enUs>class="inactive"</#if>>영문
                                : <#if item.internationalMode.enUs>${item.name.textEnUs!}<#else>비활성</#if></li></#if>
                              <#if im.zhCn>
                              <li <#if !item.internationalMode.zhCn>class="inactive"</#if>>간체(중)
                                : <#if item.internationalMode.zhCn>${item.name.textZhCn!}<#else>비활성</#if></li></#if>
                              <#if im.zhTw>
                              <li <#if !item.internationalMode.zhTw>class="inactive"</#if>>번체(중)
                                : <#if item.internationalMode.zhTw>${item.name.textZhTw!}<#else>비활성</#if></li></#if>
                              <#if im.jaJp>
                              <li <#if !item.internationalMode.jaJp>class="inactive"</#if>>일문
                                : <#if item.internationalMode.jaJp>${item.name.textJaJp!}<#else>비활성</#if></li></#if>
                          </ul>
                        <#else>
                            <#if item.name?has_content>
                                ${item.name.value!}
                            </#if>
                        </#if>
                    </td>
                    <td class="text-center">
                      <div class="wrapper-order"
                           data-type="update-order"
                           data-id="${item.id?c}"
                           data-action="<@spring.url header.url + "/order"/>"
                           data-csrf-param-name="${_csrf.parameterName}"
                           data-csrf-value="${_csrf.token}">
                        <button type="button" class="btn btn-xs btn-default" data-type="btn-order" data-mode="UP">
                          <em class="fa fa-arrow-up"></em>
                        </button>
                        <button type="button" class="btn btn-xs btn-default" data-type="btn-order" data-mode="DOWN">
                          <em class="fa fa-arrow-down"></em>
                        </button>
                      </div>
                    </td>
                    <td class="text-center"><#if item.active>
                        <div class="label label-success">활성</div><#else>
                        <div class="label label-warning">비활성</div></#if></td>
                    <td style="width: 120px" class="text-center">${item.createdDate.format('yyyy.MM.dd HH:mm:ss')}</td>
                    <td style="width: 80px" class="text-center">
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
      </div>
    </div>
  </div>
</div>