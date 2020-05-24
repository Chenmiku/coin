<#import "/spring.ftl" as spring/>
<#-- START widgets box-->
<div id="update-qna" class="container-fluid qna-create-update">
    <#-- FORM -->
  <form id="form-update-qna" action="<@spring.url header.url + "/update"/>" method="post" data-parsley-validate=""
        novalidate="">
      <#-- 수정 완료 -->
      <#include "../../common/modify-success.ftl"/>
      <#-- END : 수정 완료 -->
    <div class="row">
      <div class="col-md-12">

        <div class="pull-left mb-lg">
          <button type="submit" class="btn btn-primary btn-lg">
            수정
          </button>
            <@spring.formHiddenInput "qna.id"/>
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </div>

        <div class="pull-right mb-lg">
          <a href="<@spring.url header.url/>" class="btn btn-default btn-lg" data-toggle="tooltip" data-placement="top"
             title="목록보기">
            <span class="icon-list"></span>
          </a>
          <button data-type="btn-delete" type="button" class="btn btn-danger btn-lg" data-toggle="tooltip"
                  data-placement="top" title="삭제"
                  data-action="<@spring.url header.url + "/delete"/>"
                  data-id="${qna.id?c}">
            <span class="icon-trash"></span>
          </button>
        </div>

      </div>
    </div>
    <div class="row">
        <#-- CONTENT-->
      <div class="col-lg-7">
        <div class="panel panel-default">

          <div class="panel-body">

            <!-- 제목 -->
              <@ui.formInputText "qna.title" "제목" true 100 "제목을 입력하세요."/>

            <hr/>

              <#-- WYSISWYG EDITOR -->
              <@ui.wysiswygEdior "qna.content" "내용" true/>
              <#-- END : WYSISWYG EDITOR -->

<#--            <hr/>-->
<#--              &lt;#&ndash; WYSISWYG EDITOR &ndash;&gt;-->
<#--              <@ui.wysiswygEdior "qna.answer.content" "답변" true/>-->
<#--              &lt;#&ndash; END : WYSISWYG EDITOR &ndash;&gt;-->

          </div>
        </div>

      </div>
        <#-- END : CONTENT-->
        <#-- SIDBAR -->
      <div class="col-lg-5 pl0-lg">

        <div class="panel panel-default">
          <div class="panel-heading">작성자</div>
          <div class="panel-body">

              <#if qna.relativeUser?has_content>
              <#-- 작성자 -->
                <div class="row">
                  <div class="col-lg-6">
                    <div class="form-group">
                      <label class="control-label">회원 아이디(이메일)</label>
                      <p class="form-control-static">${qna.relativeUser.email}</p>
                    </div>
                  </div>
                  <div class="col-lg-6">
                    <div class="form-group">
                      <label class="control-label">회원명</label>
                      <p class="form-control-static">${qna.relativeUser.fullName}</p>
                    </div>
                  </div>
                </div>
              <#else>
                  <#if qna.qnaNoMember?has_content>
                    <div class="row">
                      <div class="col-lg-6">
                        <div class="form-group">
                          <label class="control-label">(비회원) 성</label>
                          <p class="form-control-static">${qna.qnaNoMember.lastName!}</p>
                        </div>
                      </div>
                      <div class="col-lg-6">
                        <div class="form-group">
                          <label class="control-label">이름</label>
                          <p class="form-control-static">${qna.qnaNoMember.firstName!}</p>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-lg-12">

                        <div class="form-group">
                          <label class="control-label">국가코드</label>

                            <@spring.bind "qna.qnaNoMember.locale"/>
                          <select name="${spring.status.expression}" class="form-control">
                              <#list locales as locale>
                                  <#if locale.country?has_content>
                                    <option value="${locale.country!}"
                                            <#if qna.qnaNoMember.locale?has_content && qna.qnaNoMember.locale?upper_case == locale.country>selected</#if>>${locale.displayCountry!}</option>
                                  </#if>
                              </#list>
                          </select>
                        </div>

                          <@ui.formInputDefault "qna.qnaNoMember.mobile" "휴대폰번호 (숫자만 01011112222)" false 12 "휴대폰번호를 입력하세요.(숫자만 01011112222)"/>

<#--                        <div class="form-group">-->
<#--                          <label class="control-label">휴대폰번호</label>-->
<#--                          <p class="form-control-static">${qna.qnaNoMember.mobile!}</p>-->
<#--                        </div>-->
                      </div>
                    </div>
                  </#if>
              </#if>

          </div>
        </div>

        <div class="panel panel-default">
          <div class="panel-body">

              <#-- TODO 작성자 -->
            <hr/>
              <#-- 카테고리 -->
              <@ui.formCategory "qna.categories" "카테고리" categories/>
          </div>
        </div>


        <div class="panel panel-default">
          <div class="panel-heading">설정</div>
          <div class="panel-body">

              <#-- 활성 모드 -->
              <@ui.formActive "qna.active"/>

          </div>
        </div>

          <#-- 메타 정보 (수정 페이지 필수) -->
          <@ui.panelMetaInfo qna.updatedDate qna.createdDate/>
          <#-- END : 메타 정보 (수정 페이지 필수) -->
      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>