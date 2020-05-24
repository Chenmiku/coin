<#import "/spring.ftl" as spring/>
<#-- START widgets box-->
<div id="update-category" class="container-fluid category-create-update">
    <#-- FORM -->
  <form id="form-update-category" action="<@spring.url header.url + "/update"/>" method="post" data-parsley-validate=""
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
            <@spring.formHiddenInput "category.id"/>
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
                  data-id="${category.id?c}">
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
                    <@ui.formCategoryByInputName "category.name.textKoKr" "/admin/category-faq/${category.id?c}/duplicate/ko_KR"/>

                    <@ui.formActiveByLanguage "category.internationalMode.koKr" />
                </div>
              <#else>
                  <@spring.formHiddenInput "category.name.textKoKr"/>
              </#if>
              <#-- END : 국문 -->
              <#-- 영문 -->
              <#if im.enUs>
                <div id="${tabPath}-en" <#if international>role="tabpanel" class="tab-pane"</#if>>
                    <@ui.formCategoryByInputName "category.name.textEnUs" "/admin/category-faq/${category.id?c}/duplicate/en_US"/>

                    <@ui.formActiveByLanguage "category.internationalMode.enUs"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "category.name.textEnUs"/>
              </#if>
              <#-- END : 영문 -->
              <#-- 중문 (간체) -->
              <#if im.zhCn>
                <div id="${tabPath}-zh-cn" <#if international>role="tabpanel" class="tab-pane"</#if>>
                    <@ui.formCategoryByInputName "category.name.textZhCn" "/admin/category-faq/${category.id?c}/duplicate/zh_CN"/>

                    <@ui.formActiveByLanguage "category.internationalMode.zhCn"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "category.name.textZhCn"/>
              </#if>
              <#-- END : 중문 (간체) -->
              <#-- 중문 (번체) -->
              <#if im.zhTw>
                <div id="${tabPath}-zh-tw" <#if international>role="tabpanel" class="tab-pane"</#if>>
                    <@ui.formCategoryByInputName "category.name.textZhTw" "/admin/category-faq/${category.id?c}/duplicate/zh_TW"/>

                    <@ui.formActiveByLanguage "category.internationalMode.zhTw"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "category.name.textZhTw"/>
              </#if>
              <#-- END : 중문 (번체) -->
              <#-- 일문 -->
              <#if im.jaJp>
                <div id="${tabPath}-ja" <#if international>role="tabpanel" class="tab-pane"</#if>>
                    <@ui.formCategoryByInputName "category.name.textJaJp" "/admin/category-faq/${category.id?c}/duplicate/ja_JP"/>

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
          <div class="panel-body">

              <#-- 아이콘 이미지 -->
              <@ui.uploadImage "아이콘 이미지" "image" "${category.image!}" "44px 이상 정사각형 png 이미지로만 업로드해주세요."/>
              <#-- END : 아이콘 이미지 -->

          </div>
        </div>

        <div class="panel panel-default">
          <div class="panel-heading">설정</div>
          <div class="panel-body">

              <@ui.formActive path="category.active"/>

          </div>
        </div>

          <#-- 메타 정보 (수정 페이지 필수) -->
          <@ui.panelMetaInfo category.updatedDate category.createdDate/>
          <#-- END : 메타 정보 (수정 페이지 필수) -->

      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>