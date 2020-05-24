<#import "/spring.ftl" as spring/>
<#-- START widgets box-->
<div id="update-faq" class="container-fluid faq-create-update">
    <#-- FORM -->
  <form id="form-update-faq" action="<@spring.url header.url + "/update"/>" method="post" data-parsley-validate=""
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
            <@spring.formHiddenInput "faq.id"/>
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
                  data-id="${faq.id?c}">
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

            <@spring.bind "faq.question"/>
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
                    <@ui.formInputTextByLanguage "faq.question.textKoKr" "제목" true 255 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "faq.answer.textKoKr" "내용" true/>
                    <@ui.formActiveByLanguage "faq.internationalMode.koKr" />
                </div>
              <#else>
                  <@spring.formHiddenInput "faq.question.textKoKr"/>
                  <@spring.formHiddenInput "faq.answer.textKoKr"/>
              </#if>
              <#-- END : 국문 -->
              <#-- 영문 -->
              <#if im.enUs>
                <div id="${tabPath}-en" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "faq.question.textEnUs" "제목" true 255 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "faq.answer.textEnUs" "내용" true/>
                    <@ui.formActiveByLanguage "faq.internationalMode.enUs"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "faq.question.textEnUs"/>
                  <@spring.formHiddenInput "faq.answer.textEnUs"/>
              </#if>
              <#-- END : 영문 -->
              <#-- 중문 (간체) -->
              <#if im.zhCn>
                <div id="${tabPath}-zh-cn" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "faq.question.textZhCn" "제목" true 255 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "faq.answer.textZhCn" "내용" true/>
                    <@ui.formActiveByLanguage "faq.internationalMode.zhCn"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "faq.question.textZhCn"/>
                  <@spring.formHiddenInput "faq.answer.textZhCn"/>
              </#if>
              <#-- END : 중문 (간체) -->
              <#-- 중문 (번체) -->
              <#if im.zhTw>
                <div id="${tabPath}-zh-tw" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "faq.question.textZhTw" "제목" true 255 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "faq.answer.textZhTw" "내용" true/>
                    <@ui.formActiveByLanguage "faq.internationalMode.zhTw"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "faq.question.textZhTw"/>
                  <@spring.formHiddenInput "faq.answer.textZhTw"/>
              </#if>
              <#-- END : 중문 (번체) -->
              <#-- 일문 -->
              <#if im.jaJp>
                <div id="${tabPath}-ja" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "faq.question.textJaJp" "제목" true 255 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "faq.answer.textJaJp" "내용" true/>
                    <@ui.formActiveByLanguage "faq.internationalMode.jaJp"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "faq.question.textJaJp"/>
                  <@spring.formHiddenInput "faq.answer.textJaJp"/>
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
          <div class="panel-body">

              <#-- 카테고리 -->
              <@ui.formCategory "faq.categories" "카테고리" categories/>
          </div>
        </div>

        <div class="panel panel-default">
          <div class="panel-heading">설정</div>
          <div class="panel-body">

              <@ui.formActive "faq.active"/>
          </div>
        </div>


          <#-- 메타 정보 (수정 페이지 필수) -->
          <@ui.panelMetaInfo faq.updatedDate faq.createdDate/>
          <#-- END : 메타 정보 (수정 페이지 필수) -->

      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>