<#import "/spring.ftl" as spring/>
<#-- START widgets box-->
<div id="update-contact" class="container-fluid contact-create-update">
    <#-- FORM -->
  <form id="form-update-contact" action="<@spring.url header.url + "/update"/>" method="post" data-parsley-validate=""
        novalidate="" data-parsley-international="true">
      <#-- 수정 완료 -->
      <#include "../../common/modify-success.ftl"/>
      <#-- END : 수정 완료 -->
    <div class="row">
      <div class="col-md-12">

        <div class="pull-left mb-lg">
          <button type="submit" class="btn btn-primary btn-lg">
            수정
          </button>
            <@spring.formHiddenInput "contact.id"/>
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
                  data-id="${contact.id?c}">
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

              <#-- WYSISWYG EDITOR -->
              <@ui.wysiswygEdior "contact.content" "내용" true/>
              <#-- END : WYSISWYG EDITOR -->

            <hr/>
              <#-- WYSISWYG EDITOR -->
              <@ui.wysiswygEdior "contact.memo" "관리자 메모" false/>
              <#-- END : WYSISWYG EDITOR -->
          </div>
        </div>

      </div>
        <#-- END : CONTENT-->
        <#-- SIDBAR -->
      <div class="col-lg-5 pl0-lg">

        <div class="panel panel-default">
          <div class="panel-body">
              <#-- 성명 -->
            <div class="form-group">
              <label class="control-label">성명 <span class="text-require">*</span></label>
                <@spring.formInput "contact.fullName",
                "placeholder=\"성명을 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"100\"
                            required"/>
            </div>

            <hr/>
            <!-- 휴대폰 번호 -->
            <div class="form-group">
              <label class="control-label">휴대폰 번호 <span class="text-require">*</span></label>
                <@spring.formInput "contact.mobile",
                "placeholder=\"휴대폰 번호를 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"100\"
                            required"/>
                <#--<span class="help-block m-b-none"> </span>-->
            </div>

            <hr/>
              <#-- 이메일 -->
            <div class="form-group">
              <label class="control-label">이메일 <span class="text-require">*</span></label>
                <@spring.formInput "contact.email",
                "placeholder=\"이메일을 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"255\"
                            required" "email"/>
            </div>

            <hr/>
              <@spring.bind "contact.complete"/>
            <div class="form-group">
              <label class="control-label">답변</label>
              <div>
                <label class="radio-inline c-radio">
                  <input data-type="mode" id="inline-radio-active-1" type="radio" name="${spring.status.expression}"
                         value="true" <#if spring.stringStatusValue == 'true'>checked</#if>>
                  <span class="fa fa-circle"></span>완료
                </label>
                <label class="radio-inline c-radio">
                  <input data-type="mode" id="inline-radio-active-2" type="radio" name="${spring.status.expression}"
                         value="false" <#if spring.stringStatusValue == 'false'>checked</#if>>
                  <span class="fa fa-circle"></span>미완료
                </label>
              </div>
            </div>


          </div>
        </div>

          <#-- 메타 정보 (수정 페이지 필수) -->
          <@ui.panelMetaInfo contact.updatedDate contact.createdDate/>
          <#-- END : 메타 정보 (수정 페이지 필수) -->
      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>