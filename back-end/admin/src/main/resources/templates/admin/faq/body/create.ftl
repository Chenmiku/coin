<#import "/spring.ftl" as spring/>
<#-- START widgets box-->
<div class="container-fluid faq-create-update">
    <#-- FORM -->
  <form id="form-create-faq" action="<@spring.url header.url + "/create"/>" method="post" data-parsley-validate=""
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
          <a href="<@spring.url header.url/>" class="btn btn-default btn-lg" data-toggle="tooltip" data-placement="top"
             title="목록보기">
            <span class="icon-list"></span>
          </a>
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

                    <#-- 질문 -->
                    <@ui.formInputTextByLanguage "faq.question.textKoKr" "질문" true 255 "질문을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "faq.answer.textKoKr" "답변" true/>
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

                    <#-- 질문 -->
                    <@ui.formInputTextByLanguage "faq.question.textEnUs" "질문" true 255 "질문을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "faq.answer.textEnUs" "답변" true/>
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

                    <#-- 질문 -->
                    <@ui.formInputTextByLanguage "faq.question.textZhCn" "질문" true 255 "질문을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "faq.answer.textZhCn" "답변" true/>
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

                    <#-- 질문 -->
                    <@ui.formInputTextByLanguage "faq.question.textZhTw" "질문" true 255 "질문을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "faq.answer.textZhTw" "답변" true/>
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

                    <#-- 질문 -->
                    <@ui.formInputTextByLanguage "faq.question.textJaJp" "질문" true 255 "질문을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "faq.answer.textJaJp" "답변" true/>
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
      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>