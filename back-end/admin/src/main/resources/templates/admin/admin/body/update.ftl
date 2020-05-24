<#import "/spring.ftl" as spring/>
<#assign isNoLeave = user.leaveMeta?has_content && !user.leaveMeta.leave && !user.leaveMeta.removePrivacyTime?has_content/>
<#-- START widgets box-->
<div id="update-user" class="container-fluid user-create-update">
    <#-- FORM -->
  <form id="form-update-user" action="<@spring.url header.url + "/update"/>" method="post" data-parsley-validate=""
        novalidate="">
      <#-- 수정 완료 -->
      <#include "../../common/modify-success.ftl"/>
      <#-- END : 수정 완료 -->
    <div class="row">
      <div class="col-md-12">

        <div class="pull-left mb-lg">
            <#if isNoLeave>
              <button type="submit" class="btn btn-primary btn-lg">
                수정
              </button>
            </#if>
            <@spring.formHiddenInput "user.id"/>
            <@spring.formHiddenInput "user.status"/>
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </div>

        <div class="pull-right mb-lg">
          <a href="<@spring.url header.url/>" class="btn btn-default btn-lg" data-toggle="tooltip" data-placement="top"
             title="목록보기">
            <span class="icon-list"></span>
          </a>
            <#if currentRole == 'ROLE_SUPER'>
              <button data-type="btn-delete" type="button" class="btn btn-danger btn-lg" data-toggle="tooltip"
                      data-placement="top" title="삭제"
                      data-action="<@spring.url header.url + "/delete"/>"
                      data-id="${user.id?c}">
                <span class="icon-trash"></span>
              </button>
            </#if>
        </div>

      </div>
    </div>
    <div class="row">
      <div class="col-lg-7">
        <#-- CONTENT-->
      <div class="panel panel-default">

        <div class="panel-body">

            <@ui.formInputText "user.fullName" "성명" true 30 "성명을 입력하세요."/>

          <div class="form-group">
            <label class="control-label">이메일 (ID) <span class="text-require">*</span></label>
              <@spring.formInput "user.email",
              "placeholder=\"이메일을 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"255\"
                            required=\"required\"
                            data-parsley-remote=\"${contextPath}/admin/api/user/${user.id?c}/duplicate/email\"
                            data-parsley-remote-options='{\"type\": \"POST\"}'
                            data-parsley-remote-validator=\"duplicate\"
                            data-parsley-remote-message=\"이미 사용중인 이메일입니다.\"" "email"/>
              <#--<span class="help-block m-b-none"></span>-->
          </div>

          <div class="row">

            <div class="col-lg-12">
                <@ui.formInputText "user.mobile" "휴대폰 번호 (- 없이 번호만 입력)" true 12 "휴대폰 번호를 입력하세요." "number"/>
            </div>
          </div>

          <hr/>
          <div>
            <button type="button" class="btn btn-default" data-toggle="modal"
                    data-target="#modal-update-password">비밀번호 수정
            </button>
          </div>


        </div>
      </div>

    </div>
      <#-- END : CONTENT-->
      <#-- SIDBAR -->
    <div class="col-lg-5 pl0-lg">


      <div class="panel panel-default">
        <div class="panel-body">

            <#if currentRole == 'ROLE_SUPER'>
                <@spring.bind path="user.role"/>
              <div class="form-group">
                <label class="control-label"><h4>권한</h4></label>

                <select name="${spring.status.expression}" class="form-control">
                    <#list authorities as authority>
                      <option value="${authority.role.role?replace("ROLE_", "")}"
                              <#if user.roleTopLevel == authority.role>selected</#if>>${authority.role}</option>
                    </#list>
                </select>
              </div>

                <@spring.formHiddenInput path="user.termsAgree.taService"/>
                <@spring.formHiddenInput path="user.termsAgree.taPrivacy"/>
                <@spring.formHiddenInput path="user.termsAgree.taThirdParty"/>
                <@spring.formHiddenInput path="user.termsAgree.taLocation"/>
                <@spring.formHiddenInput path="user.termsAgree.smsRcv"/>
                <@spring.formHiddenInput path="user.termsAgree.smsRcvDate"/>
                <@spring.formHiddenInput path="user.termsAgree.emailRcv"/>
                <@spring.formHiddenInput path="user.termsAgree.emailRcvDate"/>
                <@spring.formHiddenInput path="user.userDetailsMeta.accountNonExpired"/>
                <@spring.formHiddenInput path="user.userDetailsMeta.credentialsNonExpired"/>
                <@spring.formHiddenInput path="user.userDetailsMeta.enabled"/>

                <@spring.formHiddenInput "user.userDetailsMeta.accountNonLocked"/>

            </#if>
        </div>
      </div>

        <#-- 메타 정보 (수정 페이지 필수) -->
        <@ui.panelMetaInfo user.updatedDate user.createdDate/>
        <#-- END : 메타 정보 (수정 페이지 필수) -->
    </div>
      <#-- END : SIDBAR -->
</div>
</form>
</div>