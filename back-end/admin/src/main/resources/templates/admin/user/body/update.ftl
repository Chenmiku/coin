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
        <#-- CONTENT-->
      <div class="col-lg-7">

        <div class="panel panel-default">

          <div class="panel-body">
              <#if isNoLeave>
                  <@ui.formInputText "user.fullName" "이름" true 30 "이름을 입력하세요."/>

                <div class="form-group">
                  <label class="control-label">이메일 (ID) <span class="text-require">*</span></label>
                    <@spring.formInput "user.email",
                    "placeholder=\"이메일을 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"255\"
                            required=\"required\"
                            data-parsley-remote=\"/admin/api/user/${user.id?c}/duplicate/email\"
                            data-parsley-remote-options='{\"type\": \"POST\"}'
                            data-parsley-remote-validator=\"duplicate\"
                            data-parsley-remote-message=\"이미 사용중인 이메일입니다.\"" "email"/>
                    <#--<span class="help-block m-b-none"></span>-->
                </div>

                <div class="row">

                  <div class="col-lg-12">
<#--                      <@ui.formInputText "user.mobile" "휴대폰 번호 (- 없이 번호만 입력)" true 12 "휴대폰 번호를 입력하세요." "number"/>-->


                      <@ui.formInputDefault "user.mobile" "휴대폰번호 (숫자만 01011112222)" false 12 "휴대폰번호를 입력하세요.(숫자만 01011112222)"/>
                  </div>
                </div>

                <hr/>
                  <@ui.formRadioboxEnum "user.gender" "성별" false genders/>
                  <@ui.formDate "user.birthDate" "생일" true "YYYY-MM-DD"/>
                <hr/>
                <div>
                  <button type="button" class="btn btn-default" data-toggle="modal"
                          data-target="#modal-update-password">비밀번호 수정
                  </button>
                </div>

              <#else>
                탈퇴한 회원정보입니다.
              </#if>
          </div>
        </div>
          <#if isNoLeave>

            <div class="panel panel-default">
              <div class="panel-body">

                  <#-- 썸네일 이미지 -->
                  <@ui.uploadImage "프로필 이미지" "image" user.image/>
                  <#-- END : 썸네일 이미지 -->

              </div>
            </div>

            <div class="panel panel-default">
              <div class="panel-heading"><h4>소셜로그인</h4></div>
              <div class="panel-body">
                  <@ui.formInputDefault "user.socialId.facebookId" "페이스북 Key" false 255 "" "text"/>
                  <@ui.formInputDefault "user.socialId.facebookName" "페이스북 Username" false 255 "" "text"/>
                <hr/>
                  <@ui.formInputDefault "user.socialId.kakaoTalkId" "카카오톡 Key" false 255 "" "text"/>
                  <@ui.formInputDefault "user.socialId.kakaoTalkName" "카카오톡 Username" false 255 "" "text"/>
                <hr/>
                  <@ui.formInputDefault "user.socialId.naverId" "네이버 Key" false 255 "" "text"/>
                  <@ui.formInputDefault "user.socialId.naverName" "네이버 Username" false 255 "" "text"/>
                <hr/>

              </div>
            </div>
          </#if>
      </div>
        <#-- END : CONTENT-->
        <#-- SIDBAR -->
      <div class="col-lg-5 pl0-lg">


          <#if isNoLeave>
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

                    <hr/>

                    <h4>약관</h4>
                      <@ui.formBoolean "user.termsAgree.taService" "이용약관" "동의" "미동의" ""/>
                      <@ui.formBoolean "user.termsAgree.taPrivacy" "개인정보취급방침" "동의" "미동의" ""/>
                      <@ui.formBoolean "user.termsAgree.taYouth" "만 14세 이상 가입확인" "동의" "미동의" ""/>
<#--                      <@ui.formBoolean "user.termsAgree.taThirdParty" "제 3자 개인정보 위탁공유" "동의" "미동의" ""/>-->
<#--                      <@ui.formBoolean "user.termsAgree.taLocation" "위치기반서비스 이용약관" "동의" "미동의" ""/>-->
                    <hr/>
                    <h4>마케팅 활용 항목</h4>
                      <@ui.formBoolean "user.termsAgree.smsRcv" "SMS 수신 동의" "동의" "미동의" ""/>
                      <@ui.formDate "user.termsAgree.smsRcvDate" "SMS 수신 동의 시간" false "YYYY/MM/DD HH:mm:ss"/>
                    <hr/>
                      <@ui.formBoolean "user.termsAgree.emailRcv" "이메일 수신 동의" "동의" "미동의" ""/>
                      <@ui.formDate "user.termsAgree.emailRcvDate" "이메일 수신 동의 시간" false "YYYY/MM/DD HH:mm:ss"/>
                    <hr/>
                      <@ui.formBoolean "user.termsAgree.kakaoRcv" "카카오톡 수신 동의" "동의" "미동의" ""/>
                      <@ui.formDate "user.termsAgree.kakaoRcvDate" "카카오톡 수신 동의 시간" false "YYYY/MM/DD HH:mm:ss"/>
                    <hr/>
                    <h4>설정</h4>
                  <#--<@ui.formBoolean "user.userDetailsMeta.accountNonExpired" "계정 만료" "사용중" "만료" ""/>-->
                  <#--<@ui.formBoolean "user.userDetailsMeta.credentialsNonExpired" "비밀번호 유효" "true" "false" ""/>-->
                  <#--<@ui.formBoolean "user.userDetailsMeta.enabled" "계정 사용가능" "가능" "불가능" ""/>-->
                      <@spring.formHiddenInput path="user.userDetailsMeta.accountNonExpired"/>
                      <@spring.formHiddenInput path="user.userDetailsMeta.credentialsNonExpired"/>
                      <@spring.formHiddenInput path="user.userDetailsMeta.enabled"/>

                      <@ui.formBoolean "user.userDetailsMeta.accountNonLocked" "잠금모드" "해제" "잠금" ""/>
                  <#else>
                      <@spring.formHiddenInput path="user.role"/>
                  </#if>

              </div>
            </div>



          </#if>
        <div class="panel panel-default">
          <div class="panel-heading"><h4>탈퇴 및 개인정보 관리</h4></div>
          <div class="panel-body">

              <#if user.leaveMeta?has_content && user.leaveMeta.leave && !user.leaveMeta.removePrivacyTime?has_content>
                <div class="form-group">
                  <label class="control-label">탈퇴 회원 </label>
                  <span class="help-block">현재 탈퇴한 회원입니다. 개인정보는 탈퇴시점에서 1년후 자동 파기됩니다.</span>
                  <p class="form-control-static">${user.leaveMeta.leaveTime.format('"yyyy-MM-dd HH:mm:ss"에 탈퇴하였습니다.')!}</p>
                </div>
                <div>
                  <label class="control-label">탈퇴 회원 </label>
                  <p class="form-control-static"><#if user.leaveMeta.leaveReason?has_content>${user.leaveMeta.leaveReason}<#else>탈퇴사유 없음</#if></p>
                </div>
                <hr/>
                <button id="btn-restore-leave" type="button" class="btn btn-default"
                        data-action="<@spring.url header.url + "/restore-leave"/>"
                        data-id="${user.id?c}">회원 복구
                </button>

                <button id="btn-remove-privacy" type="button" class="btn btn-warning"
                        data-action="<@spring.url header.url + "/remove-privacy"/>"
                        data-id="${user.id?c}">개인정보 파기
                </button>

              <#elseif user.leaveMeta.removePrivacyTime?has_content>
                <div class="form-group">
                  <label class="control-label">탈퇴 날짜 </label>
                  <p class="form-control-static">${user.leaveMeta.leaveTime.format('"yyyy-MM-dd HH:mm:ss"에 탈퇴하였습니다.')!}</p>
                </div>
                <hr/>
                <div class="form-group">
                  <label class="control-label">개인정보 파기 날짜 </label>
                  <p class="form-control-static">${user.leaveMeta.removePrivacyTime.format('"yyyy-MM-dd HH:mm:ss"에 삭제되었습니다.')!}</p>
                </div>
              <#else>
                <div>
                  <button id="btn-leave-user" type="button" class="btn btn-default"
                          data-action="<@spring.url header.url + "/leave"/>"
                          data-id="${user.id?c}">회원탈퇴
                  </button>
                </div>
              </#if>
          </div>
        </div>

          <#if isNoLeave>

          <#-- TODO https://aartkorea0.atlassian.net/browse/CPGF-16 - 클릭방식으로 변경 -->
            <div class="panel panel-default">
              <div class="panel-heading"><h4>휴면 정보</h4></div>
              <div class="panel-body">

                <div class="row">
                  <div class="col-lg-6">
                      <@ui.formBoolean "user.dormancyMeta.dormancy" "휴면모드" "활성" "비활성" ""/>
                  </div>
                  <div class="col-lg-6">
                      <@ui.formDate "user.dormancyMeta.dormancyTime" "휴면된 날짜" false "YYYY-MM-DD HH:mm:ss"/>
                  </div>
                </div>

              </div>
            </div>

              <#if user.buyer?has_content>
                <div class="panel panel-default">
                  <div class="panel-heading">구매 정보</div>
                  <div class="panel-body">

                  </div>
                </div>
              </#if>

          </#if>

          <#-- 메타 정보 (수정 페이지 필수) -->
          <@ui.panelMetaInfo user.updatedDate user.createdDate/>
          <#-- END : 메타 정보 (수정 페이지 필수) -->
      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>