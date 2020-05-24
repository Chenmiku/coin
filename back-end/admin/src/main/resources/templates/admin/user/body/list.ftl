<#--<#assign pageparam = "&key=value"/>-->
<#assign pageparam = "&role=${role!}&buyerLevel=${buyerLevel!}"/>
<div id="user-list" class="container-fluid">
    <#-- 필터 -->
  <div class="row">
    <div class="col-md-12">
      <div id="" class="panel panel-default panel-list-filter">
        <div class="panel-heading">검색 필터

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
              <fieldset>
                <div class="row">
                  <div class="col-lg-6">
                    <div class="form-group">
                      <label class="col-xs-2 control-label">기업 ID</label>

                      <div class="col-xs-10">
                        <input name="idBusinessUser" type="text" class="form-control" placeholder="기업ID를 입력하세요."
                               value="<#if idBusinessUser?has_content>${idBusinessUser?c}</#if>">
                      </div>
                    </div>
                  </div>
                </div>
              </fieldset>

                <#include "/admin/common/list/fieldset-common.ftl"/>
                <#include "/admin/common/list/fieldset-date.ftl"/>
              <fieldset>
                <div class="row">
                  <div class="col-lg-6">
                    <div class="form-group">
                      <label class="col-xs-2 control-label">권한</label>

                      <div class="col-xs-5">
                        <select name="role" class="form-control">
                          <option value="">전체</option>
                            <#list roles as _role>
                              <option value="${_role.role?replace("ROLE_", "")}"
                                      <#if role?has_content && role.role == _role.role>selected</#if>>${_role.text}</option>
                            </#list>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
              </fieldset>

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


            <button id="excel-list-filter" type="button" data-action="<@spring.url "/admin/user/excel"/>"
                    class="btn btn-labeled btn-default">
              <span class="text">엑셀 다운로드</span>
              <span class="btn-label btn-label-right"><i class="fa fa-file-excel-o"></i></span>
            </button>
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
              <col width="5%">
              <col width="10%">
              <col width="10%">
              <col width="10%">
              <col width="10%">
              <col width="10%">
              <col width="5%">
              <col width="12%">
              <col width="5%">
            </colgroup>
            <thead>
            <tr>
              <th class="text-center"><input type="checkbox" class="check_all" data-type="checkUserByBU"/></th>
              <th class="text-center">#</th>
              <th class="text-center">권한</th>
              <th class="text-center">이름</th>
              <th class="text-center">이메일</th>
              <th class="text-center">휴대폰</th>
              <th class="text-center">IsLeave</th>
              <th class="text-center">접속정보</th>
              <th class="text-center">Actions</th>
            </tr>
            </thead>
            <tbody>

            <#if data?has_content>
                <#list data.page.content as item>
                  <tr>

                    <td class="text-center">
                      <input type="checkbox" name="selectCheckbox"
                             value="${item.id?c}"
                      />

                    <td class="text-center">no.${data.firstNo- (item_index + 1)}<br/>id.${item.id?c}</td>

                    <td class="text-center">
                        ${item.roleTopLevel!}
                    </td>

                    <td class="text-center">
                        ${item.fullName!}
                    </td>

                    <td class="text-center">
                      ${item.mobile!}
                    </td>

                    <td class="text-center">
                        ${item.email!}
                    </td>

                    <td class="text-center">
                      ${item.isLeave!}
                    </td>

                    <td class="text-left">
                      <ul style="padding-left: 13px; margin-bottom: 0px">
                        <li>최종로그인 : ${item.dormancyMeta.lastLoginDate.format('yyyy-MM-dd')}</li>
                        <li>가입일 : ${item.createdDate.format('yyyy-MM-dd')}</li>
                      </ul>
                    </td>

                    <td class="text-center">
                      <a href="<@spring.url header.url + "/update/${item.id?c}"/>" class="btn btn-sm btn-default"
                         data-toggle="tooltip" data-placement="top" title="상세보기 및 수정">
                        <em class="fa fa-pencil"></em>
                      </a>
                      <a href="<@spring.url header.url + "/leave/${item.id?c}"/>" class="btn btn-sm btn-default"
                         data-toggle="tooltip" data-placement="top" title="상세보기 및 수정">
                        <em class="fa fa-remove"></em>
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