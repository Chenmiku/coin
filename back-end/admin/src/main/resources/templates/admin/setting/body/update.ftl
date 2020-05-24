<#import "/spring.ftl" as spring/>
<#-- START widgets box-->
<div id="update-setting" class="container-fluid setting-create-update">
    <#-- FORM -->
  <form id="form-update-setting" action="<@spring.url header.url/>" method="post" data-parsley-validate="" novalidate=""
        data-parsley-international="true">
      <#-- 수정 완료 -->
      <#include "../../common/modify-success.ftl"/>
      <#-- END : 수정 완료 -->
    <div class="row">
      <div class="col-md-12">

        <div class="pull-left mb-lg">
          <button type="submit" class="btn btn-primary btn-lg">
            수정
          </button>
            <@spring.formHiddenInput "setting.id"/>
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </div>

          <#--<div class="pull-right mb-lg">-->
          <#--<a href="<@spring.url header.url/>" class="btn btn-default btn-lg" data-toggle="tooltip" data-placement="top" title="목록보기">-->
          <#--<span class="icon-list"></span>-->
          <#--</a>-->
          <#--</div>-->

      </div>
    </div>
    <div class="row">
        <#-- CONTENT-->
      <div class="col-lg-7">
        <div class="panel panel-default">
          <div class="panel-heading">언어 설정</div>
          <div class="panel-body">
              <@spring.bind "setting.defaultLocale"/>
            <div class="form-group">
              <label class="control-label">기본 언어<span class="text-require">*</span></label>

              <select name="${spring.status.expression}" class="form-control">
                  <#list locales as locale>
                    <option value="${locale}" <#if spring.stringStatusValue == locale>selected</#if>>
                        <#if locale == 'zh_CN'>
                          중문 (간체)
                        <#elseif locale == 'zh_TW'>
                          중문 (번체)
                        <#elseif locale == 'ko_KR'>
                          국문
                        <#elseif locale == 'en_US'>
                          영문
                        <#elseif locale == 'ja_JP'>
                          일문
                        <#else>
                            ${locale.displayLanguage}
                        </#if>
                    </option>
                  <#--<#if hotel?has_content >selected</#if>-->
                  </#list>
              </select>
            </div>
            <hr/>

              <@spring.bind "setting.international"/>
            <div class="form-group">
              <label class="control-label">국제화 모드</label>
              <div>
                <label class="radio-inline c-radio">
                  <input data-type="mode" id="inline-radio-international-1" type="radio"
                         name="${spring.status.expression}" value="true"
                         <#if spring.stringStatusValue == 'true'>checked</#if>>
                  <span class="fa fa-circle"></span>활성
                </label>
                <label class="radio-inline c-radio">
                  <input data-type="mode" id="inline-radio-international-2" type="radio"
                         name="${spring.status.expression}" value="false"
                         <#if spring.stringStatusValue == 'false'>checked</#if>>
                  <span class="fa fa-circle"></span>비활성
                </label>
              </div>
            </div>

          </div>
        </div>
        <div class="panel panel-default">
          <div class="panel-heading">국가 언어 설정</div>
          <div class="panel-body">
              <@spring.bind "setting.internationalMode.koKr"/>
            <div class="form-group">
              <label class="control-label">국문</label>
              <div>
                <label class="radio-inline c-radio">
                  <input data-type="internationalMode" id="inline-radio-koKr-1" type="radio"
                         name="${spring.status.expression}" value="true"
                         <#if spring.stringStatusValue == 'true'>checked</#if>>
                  <span class="fa fa-circle"></span>활성
                </label>
                <label class="radio-inline c-radio">
                  <input data-type="internationalMode" id="inline-radio-koKr-2" type="radio"
                         name="${spring.status.expression}" value="false"
                         <#if spring.stringStatusValue == 'false'>checked</#if>>
                  <span class="fa fa-circle"></span>비활성
                </label>
              </div>
            </div>

            <hr/>

              <@spring.bind "setting.internationalMode.enUs"/>
            <div class="form-group">
              <label class="control-label">영문</label>
              <div>
                <label class="radio-inline c-radio">
                  <input data-type="internationalMode" id="inline-radio-enUs-1" type="radio"
                         name="${spring.status.expression}" value="true"
                         <#if spring.stringStatusValue == 'true'>checked</#if>>
                  <span class="fa fa-circle"></span>활성
                </label>
                <label class="radio-inline c-radio">
                  <input data-type="internationalMode" id="inline-radio-enUs-2" type="radio"
                         name="${spring.status.expression}" value="false"
                         <#if spring.stringStatusValue == 'false'>checked</#if>>
                  <span class="fa fa-circle"></span>비활성
                </label>
              </div>
            </div>

            <hr/>

              <@spring.bind "setting.internationalMode.zhCn"/>
            <div class="form-group">
              <label class="control-label">중문 (간체)</label>
              <div>
                <label class="radio-inline c-radio">
                  <input data-type="internationalMode" id="inline-radio-zhCn-1" type="radio"
                         name="${spring.status.expression}" value="true"
                         <#if spring.stringStatusValue == 'true'>checked</#if>>
                  <span class="fa fa-circle"></span>활성
                </label>
                <label class="radio-inline c-radio">
                  <input data-type="internationalMode" id="inline-radio-zhCn-2" type="radio"
                         name="${spring.status.expression}" value="false"
                         <#if spring.stringStatusValue == 'false'>checked</#if>>
                  <span class="fa fa-circle"></span>비활성
                </label>
              </div>
            </div>

            <hr/>
              <@spring.bind "setting.internationalMode.zhTw"/>
            <div class="form-group">
              <label class="control-label">중문 (번체)</label>
              <div>
                <label class="radio-inline c-radio">
                  <input data-type="internationalMode" id="inline-radio-zhTw-1" type="radio"
                         name="${spring.status.expression}" value="true"
                         <#if spring.stringStatusValue == 'true'>checked</#if>>
                  <span class="fa fa-circle"></span>활성
                </label>
                <label class="radio-inline c-radio">
                  <input data-type="internationalMode" id="inline-radio-zhTw-2" type="radio"
                         name="${spring.status.expression}" value="false"
                         <#if spring.stringStatusValue == 'false'>checked</#if>>
                  <span class="fa fa-circle"></span>비활성
                </label>
              </div>
            </div>

            <hr/>
              <@spring.bind "setting.internationalMode.jaJp"/>
            <div class="form-group">
              <label class="control-label">일문 </label>
              <div>
                <label class="radio-inline c-radio">
                  <input data-type="internationalMode" id="inline-radio-jaJp-1" type="radio"
                         name="${spring.status.expression}" value="true"
                         <#if spring.stringStatusValue == 'true'>checked</#if>>
                  <span class="fa fa-circle"></span>활성
                </label>
                <label class="radio-inline c-radio">
                  <input data-type="internationalMode" id="inline-radio-jaJp-2" type="radio"
                         name="${spring.status.expression}" value="false"
                         <#if spring.stringStatusValue == 'false'>checked</#if>>
                  <span class="fa fa-circle"></span>비활성
                </label>
              </div>
            </div>

          </div>
        </div>

      </div>
        <#-- END : CONTENT-->
        <#-- SIDBAR -->
      <div class="col-lg-5 pl0-lg">
        <div class="panel panel-default">
          <div class="panel-heading">설정 관리</div>
          <div class="panel-body">
<#--            <@ui.formInputText "setting.limitByShipMin" "최소 발송제한" true 10 "최소 발송제한 횟수를 입력하세요." "number"/>-->
<#--            <hr/>-->
<#--            <@ui.formInputText "setting.limitByShip" "발송제한" true 10 "발송제한 횟수를 입력하세요." "number"/>-->
<#--            <hr/>-->
<#--            <@ui.formInputText "setting.limitByResend" "재발송제한" true 10 "재발송제한 횟수를 입력하세요." "number"/>-->
<#--            <hr/>-->
<#--            <@ui.formInputText "setting.sendSleep" "발송 지연" true 10 "발송 지연 시간(ms)을 입력하세요." "number"/>-->
          </div>
        </div>
          <#-- 메타 정보 (수정 페이지 필수) -->
        <div class="panel panel-default">
          <div class="panel-heading">메타 정보</div>
          <div class="panel-body">

            <label class="control-label">최근 수정일 </label>
            <p class="form-control-static">${setting.updatedDate.format('yyyy년 MM월 dd일 HH:mm:ss')}</p>

          </div>
        </div>
          <#-- END : 메타 정보 (수정 페이지 필수) -->

      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>