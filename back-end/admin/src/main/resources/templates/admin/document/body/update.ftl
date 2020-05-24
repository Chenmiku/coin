<#import "/spring.ftl" as spring/>
<#-- START widgets box-->
<div id="update-document" class="container-fluid document-create-update">
    <#-- FORM -->
  <form id="form-update-document" action="<@spring.url header.url + "/update"/>" method="post" data-parsley-validate=""
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
            <@spring.formHiddenInput "document.id"/>
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
                  data-id="${document.id?c}">
            <span class="icon-trash"></span>
          </button>
        </div>

      </div>
    </div>
    <div class="row">
        <#-- CONTENT-->
      <div class="col-lg-7">

          <#-- TAB Panel or Panel Wrapper -->
        <div <#if international>role="tabpanel" class="panel panel-transparent" data-type="tabpanel-language"
             <#else>class="panel panel-default"</#if>>

            <@spring.bind "document.title"/>
            <#assign tabPath = spring.status.expression/>
            <#-- TAB LANGUAGE HEADER -->
            <@ui.tabListByLanguage tabPath/>
            <#-- END : TAB LANGUAGE HEADER -->

            <#-- TAB LANGUAGE BODY -->
          <div <#if international>class="tab-content bg-white" <#else>class="panel-body"</#if>>

              <#-- 국문 -->
              <#if im.koKr>
                <div id="${tabPath}-ko" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "document.title.textKoKr" "제목" true 255 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "document.content.textKoKr" "내용" true/>
                    <@ui.formActiveByLanguage "document.internationalMode.koKr" />
                </div>
              <#else>
                  <@spring.formHiddenInput "document.title.textKoKr"/>
                  <@spring.formHiddenInput "document.content.textKoKr"/>
              </#if>
              <#-- END : 국문 -->
              <#-- 영문 -->
              <#if im.enUs>
                <div id="${tabPath}-en" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "document.title.textEnUs" "제목" true 255 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "document.content.textEnUs" "내용" true/>
                    <@ui.formActiveByLanguage "document.internationalMode.enUs"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "document.title.textEnUs"/>
                  <@spring.formHiddenInput "document.content.textEnUs"/>
              </#if>
              <#-- END : 영문 -->
              <#-- 중문 (간체) -->
              <#if im.zhCn>
                <div id="${tabPath}-zh-cn" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "document.title.textZhCn" "제목" true 255 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "document.content.textZhCn" "내용" true/>
                    <@ui.formActiveByLanguage "document.internationalMode.zhCn"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "document.title.textZhCn"/>
                  <@spring.formHiddenInput "document.content.textZhCn"/>
              </#if>
              <#-- END : 중문 (간체) -->
              <#-- 중문 (번체) -->
              <#if im.zhTw>
                <div id="${tabPath}-zh-tw" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "document.title.textZhTw" "제목" true 255 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "document.content.textZhTw" "내용" true/>
                    <@ui.formActiveByLanguage "document.internationalMode.zhTw"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "document.title.textZhTw"/>
                  <@spring.formHiddenInput "document.content.textZhTw"/>
              </#if>
              <#-- END : 중문 (번체) -->
              <#-- 일문 -->
              <#if im.jaJp>
                <div id="${tabPath}-ja" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "document.title.textJaJp" "제목" true 255 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "document.content.textJaJp" "내용" true/>
                    <@ui.formActiveByLanguage "document.internationalMode.jaJp"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "document.title.textJaJp"/>
                  <@spring.formHiddenInput "document.content.textJaJp"/>
              </#if>
              <#-- END : 일문 -->
          </div>
        </div>
          <#-- END : TAB LANGUAGE BODY -->

      </div>
        <#-- END : CONTENT-->
        <#-- SIDBAR -->
      <div class="col-lg-5 pl0-lg">

        <div class="panel panel-default">
          <div class="panel-heading">설정 정보</div>
          <div class="panel-body">

            <div class="form-group">
              <label class="control-label">시행날짜 <span class="text-require">*</span></label>
                <@spring.formInput "document.executeDate"
                "data-type=\"datetimepicker\"
                                 data-format=\"YYYY-MM-DD\"
                                 type=\"text\"
                                 class=\"form-control\"
                                 required"/>
              <span class="help-block m-b-none">최신 시행날짜가 노출됩니다.</span>
            </div>

              <@spring.bind path="document.type"/>
            <div class="form-group">
              <label class="control-label">문서 유형 <span class="text-require">*</span></label>

              <select name="${spring.status.expression}" class="form-control">
                  <#list types as type>
                    <option value="${type}" <#if spring.stringStatusValue == type>selected</#if>>${type.value}</option>
                  <#--<#if hotel?has_content >selected</#if>-->
                  </#list>
              </select>
            </div>

            <hr/>

              <@ui.formActive "document.active"/>

          </div>
        </div>



          <#-- 메타 정보 (수정 페이지 필수) -->
          <@ui.panelMetaInfo document.updatedDate document.createdDate/>
      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>