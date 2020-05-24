<#import "/spring.ftl" as spring/>
<#-- START widgets box-->
<div class="container-fluid user-create-update">
    <#-- FORM -->
  <form id="form-create-user" action="<@spring.url header.url + "/create"/>" method="post" data-parsley-validate=""
        novalidate="" data-parsley-international="true">
    <div class="row">
      <div class="col-md-12">

        <div class="pull-left mb-lg">
          <button type="submit" class="btn btn-primary btn-lg">
            저장
          </button>
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </div>

        <div class="pull-right mb-lg">
          <a href="<@spring.url header.url/>" class="btn btn-default btn-lg" data-toggle="tooltip"
             data-placement="top" title="목록보기">
            <span class="icon-list"></span>
          </a>
        </div>

      </div>
    </div>
    <div class="row">
        <#-- CONTENT-->
      <div class="col-lg-7">

        <div class="panel panel-default">

          <div class="panel-body">


              <@ui.formInputText "user.fullName" "이름" true 30 "이름을 입력하세요."/>


            <div class="form-group">
              <label class="control-label">이메일 (ID) <span class="text-require">*</span></label>
                <@spring.formInput "user.email",
                "placeholder=\"이메일을 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"255\"
                            required=\"required\"
                            data-parsley-remote=\"/admin/user/duplicate/email\"
                            data-parsley-remote-options='{\"type\": \"POST\"}'
                            data-parsley-remote-validator=\"duplicate\"
                            data-parsley-remote-message=\"이미 사용중인 이메일입니다.\"" "email"/>
            </div>

<#--            <hr/>-->
<#--              <@ui.formRadioboxEnum "user.gender" "성별" false genders/>-->
<#--              <@ui.formDate "user.birthDate" "생일" true "YYYY-MM-DD"/>-->
            <hr/>
            <div class="form-group">
              <label class="control-label">비밀번호 <span class="text-require">*</span></label>
                <@spring.formPasswordInput "user.password",
                "placeholder=\"비밀번호를 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"50\"
                            required=\"required\"
                            data-parsley-minlength=\"5\"
                            data-parsley-maxlength=\"20\"
                            data-parsley-uppercase=\"1\"
                            data-parsley-lowercase=\"1\"
                            data-parsley-number=\"1\"
                            data-parsley-special=\"1\"
                            "/>
              <span class="help-block mb0">5~20자, 알파벳 1자이상, 숫자 포함, 특수문자 포함</span>
            </div>

            <div class="form-group">
              <label class="control-label">비밀번호 확인 <span class="text-require">*</span></label>
                <@spring.formPasswordInput "user.passwordConfirm",
                "placeholder=\"비밀번호 확인을 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"255\"
                            required=\"required\"
                            data-parsley-equalto=\"#password\"
                            "/>
            </div>


          </div>
        </div>

        <div class="panel panel-default">
          <div class="panel-body">

              <#-- 썸네일 이미지 -->
              <@ui.uploadImage "프로필 이미지" "image" user.image/>
              <#-- END : 썸네일 이미지 -->

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
                      <#--<#if user?has_content >selected</#if>-->
                      </#list>
                  </select>
                </div>
              <#else>
                  <@spring.formHiddenInput path="user.role"/>
              </#if>
            <hr/>
            <h4>약관</h4>
              <@ui.formBoolean "user.termsAgree.taService" "이용약관" "동의" "미동의" ""/>
              <@ui.formBoolean "user.termsAgree.taPrivacy" "개인정보취급방침" "동의" "미동의" ""/>
<#--              <@ui.formBoolean "user.termsAgree.taYouth" "만 14세 이상 가입확인" "동의" "미동의" ""/>-->
              <#--              <@ui.formBoolean "user.termsAgree.taThirdParty" "제 3자 개인정보 위탁공유" "동의" "미동의" ""/>-->
              <#--              <@ui.formBoolean "user.termsAgree.taLocation" "위치기반서비스 이용약관" "동의" "미동의" ""/>-->
            <hr/>
            <h4>마케팅 활용 항목</h4>
              <@ui.formBoolean "user.termsAgree.smsRcv" "SMS 수신 동의" "동의" "미동의" ""/>
              <@ui.formDate "user.termsAgree.smsRcvDate" "SMS 수신 동의 시간" false "YYYY/MM/DD HH:mm:ss"/>
            <hr/>
              <@ui.formBoolean "user.termsAgree.emailRcv" "이메일 수신 동의" "동의" "미동의" ""/>
              <@ui.formDate "user.termsAgree.emailRcvDate" "이메일 수신 동의 시간" false "YYYY/MM/DD HH:mm:ss"/>
<#--            <hr/>-->
<#--              <@ui.formBoolean "user.termsAgree.kakaoRcv" "카카오톡 수신 동의" "동의" "미동의" ""/>-->
<#--              <@ui.formDate "user.termsAgree.kakaoRcvDate" "카카오톡 수신 동의 시간" false "YYYY/MM/DD HH:mm:ss"/>-->
            <hr/>
            <h4>설정</h4>
              <#--<@ui.formBoolean "user.userDetailsMeta.accountNonExpired" "계정 만료" "사용중" "만료" ""/>-->
              <#--<@ui.formBoolean "user.userDetailsMeta.credentialsNonExpired" "비밀번호 유효" "true" "false" ""/>-->
              <#--<@ui.formBoolean "user.userDetailsMeta.enabled" "계정 사용가능" "가능" "불가능" ""/>-->
              <@spring.formHiddenInput path="user.userDetailsMeta.accountNonExpired"/>
              <@spring.formHiddenInput path="user.userDetailsMeta.credentialsNonExpired"/>
              <@spring.formHiddenInput path="user.userDetailsMeta.enabled"/>

              <@ui.formBoolean "user.userDetailsMeta.accountNonLocked" "잠금모드" "해제" "잠금" ""/>

          </div>
        </div>


      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>