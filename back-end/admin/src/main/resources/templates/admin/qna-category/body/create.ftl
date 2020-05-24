<#import "/spring.ftl" as spring/>
<#-- START widgets box-->
<div class="container-fluid category-create-update">
    <#-- FORM -->
  <form id="form-create-category" action="<@spring.url header.url + "/create"/>" method="post" data-parsley-validate=""
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

            <@spring.bind "category.name"/>
            <#assign tabPath = spring.status.expression/>
            <#-- TAB LANGUAGE HEADER -->
            <@ui.tabListByLanguage tabPath/>
            <#-- END : TAB LANGUAGE HEADER -->

            <#-- TAB LANGUAGE BODY -->
          <div <#if international>class="tab-content bg-white" <#else>class="panel-body"</#if>>

              <#-- 국문 -->
              <#if im.koKr>
                <div id="${tabPath}-ko" <#if international>role="tabpanel" class="tab-pane"</#if>>
                    <@ui.formCategoryByInputName "category.name.textKoKr" "/admin/category-qna/duplicate/ko_KR"/>

                    <@ui.formActiveByLanguage "category.internationalMode.koKr" />
                </div>
              <#else>
                  <@spring.formHiddenInput "category.name.textKoKr"/>
              </#if>
              <#-- END : 국문 -->
              <#-- 영문 -->
              <#if im.enUs>
                <div id="${tabPath}-en" <#if international>role="tabpanel" class="tab-pane"</#if>>
                    <@ui.formCategoryByInputName "category.name.textEnUs" "/admin/category-qna/duplicate/en_US"/>

                    <@ui.formActiveByLanguage "category.internationalMode.enUs"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "category.name.textEnUs"/>
              </#if>
              <#-- END : 영문 -->
              <#-- 중문 (간체) -->
              <#if im.zhCn>
                <div id="${tabPath}-zh-cn" <#if international>role="tabpanel" class="tab-pane"</#if>>
                    <@ui.formCategoryByInputName "category.name.textZhCn" "/admin/category-qna/duplicate/zh_CN"/>

                    <@ui.formActiveByLanguage "category.internationalMode.zhCn"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "category.name.textZhCn"/>
              </#if>
              <#-- END : 중문 (간체) -->
              <#-- 중문 (번체) -->
              <#if im.zhTw>
                <div id="${tabPath}-zh-tw" <#if international>role="tabpanel" class="tab-pane"</#if>>
                    <@ui.formCategoryByInputName "category.name.textZhTw" "/admin/category-qna/duplicate/zh_TW"/>

                    <@ui.formActiveByLanguage "category.internationalMode.zhTw"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "category.name.textZhTw"/>
              </#if>
              <#-- END : 중문 (번체) -->
              <#-- 일문 -->
              <#if im.jaJp>
                <div id="${tabPath}-ja" <#if international>role="tabpanel" class="tab-pane"</#if>>
                    <@ui.formCategoryByInputName "category.name.textJaJp" "/admin/category-qna/duplicate/ja_JP"/>

                    <@ui.formActiveByLanguage "category.internationalMode.jaJp"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "category.name.textJaJp"/>
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
          <div class="panel-heading">설정</div>
          <div class="panel-body">

              <#-- 활성 모드 -->
              <@ui.formActive "category.active"/>
          </div>
        </div>
      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>