<#import "/spring.ftl" as spring/>
<#-- START widgets box-->
<div id="update-post" class="container-fluid post-create-update">
    <#-- FORM -->
  <form id="form-update-post" action="<@spring.url header.url + "/update"/>" method="post" data-parsley-validate=""
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
            <@spring.formHiddenInput "post.id"/>
            <@spring.formHiddenInput "post.type"/>
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
                  data-id="${post.id?c}">
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

            <@spring.bind "post.title"/>
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
                    <@ui.formInputTextByLanguage "post.title.textKoKr" "제목" true 200 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "post.content.textKoKr" "내용" false/>
                    <@ui.formActiveByLanguage "post.internationalMode.koKr" />
                </div>
              <#else>
                  <@spring.formHiddenInput "post.title.textKoKr"/>
                  <@spring.formHiddenInput "post.content.textKoKr"/>
              </#if>
              <#-- END : 국문 -->
              <#-- 영문 -->
              <#if im.enUs>
                <div id="${tabPath}-en" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "post.title.textEnUs" "제목" true 200 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "post.content.textEnUs" "내용" false/>
                    <@ui.formActiveByLanguage "post.internationalMode.enUs"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "post.title.textEnUs"/>
                  <@spring.formHiddenInput "post.content.textEnUs"/>
              </#if>
              <#-- END : 영문 -->
              <#-- 중문 (간체) -->
              <#if im.zhCn>
                <div id="${tabPath}-zh-cn" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "post.title.textZhCn" "제목" true 200 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "post.content.textZhCn" "내용" false/>
                    <@ui.formActiveByLanguage "post.internationalMode.zhCn"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "post.title.textZhCn"/>
                  <@spring.formHiddenInput "post.content.textZhCn"/>
              </#if>
              <#-- END : 중문 (간체) -->
              <#-- 중문 (번체) -->
              <#if im.zhTw>
                <div id="${tabPath}-zh-tw" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "post.title.textZhTw" "제목" true 200 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "post.content.textZhTw" "내용" false/>
                    <@ui.formActiveByLanguage "post.internationalMode.zhTw"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "post.title.textZhTw"/>
                  <@spring.formHiddenInput "post.content.textZhTw"/>
              </#if>
              <#-- END : 중문 (번체) -->
              <#-- 일문 -->
              <#if im.jaJp>
                <div id="${tabPath}-ja" <#if international>role="tabpanel" class="tab-pane"</#if>>

                    <#-- 제목 -->
                    <@ui.formInputTextByLanguage "post.title.textJaJp" "제목" true 200 "제목을 입력하세요."/>
                  <hr/>

                    <#-- WYSISWYG EDITOR -->
                    <@ui.wysiswygEdior "post.content.textJaJp" "내용" true/>
                    <@ui.formActiveByLanguage "post.internationalMode.jaJp"/>
                </div>
              <#else>
                  <@spring.formHiddenInput "post.title.textJaJp"/>
                  <@spring.formHiddenInput "post.content.textJaJp"/>
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

              <#-- 썸네일 이미지 -->
              <@ui.uploadImage "썸네일 이미지" "thumbnail" "${post.thumbnail!}"/>
              <#-- END : 썸네일 이미지 -->

              <#--<hr/>-->

              <#--&lt;#&ndash; 카테고리 &ndash;&gt;-->
              <#--<@ui.formCategory "post.categories" categories/>-->
          </div>
        </div>

        <div class="panel panel-default">
          <div class="panel-heading">설정</div>
          <div class="panel-body">

              <@ui.formDate "post.regDate" "등록날짜" true "YYYY-MM-DD HH:mm:ss"/>

            <hr/>
              <#-- 공지 모드 -->
              <@ui.formBoolean "post.top" "공지모드" "활성" "비활성" "활성일 경우 최상단에 노출됩니다."/>

            <hr/>
              <#-- 활성 모드 -->
              <@ui.formActive "post.active"/>

            <hr/>
              <#-- SEO 설명 -->
            <div class="form-group">
              <label class="control-label">SEO 설명 </label>

                <@spring.formInput "post.seo.description",
                "placeholder=\"SEO 설명을 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"255\""/>
              <span class="help-block m-b-none"> 45자 이내로 작성하세요. </span>
            </div>

              <#-- SEO 키워드 -->
            <div class="form-group">
              <label class="control-label">SEO 키워드 </label>

                <@spring.formInput "post.seo.keywords",
                "placeholder=\"SEO 키워드을 입력하세요.\"
                            class=\"form-control\"
                            data-role=\"tagsinput\"
                            maxlength=\"255\""/>
                <#--<span class="help-block m-b-none"> </span>-->
            </div>

          </div>
        </div>

          <#--<div class="panel panel-default">-->
          <#--<div class="panel-heading">파일</div>-->
          <#--<div class="panel-body">-->

          <#--&lt;#&ndash; 파일 업로드 &ndash;&gt;-->
          <#--<@ui.uploadFiles "업로드 (복수)" "file" "files" "/admin/api/upload/file" post.files/>-->

          <#--</div>-->
          <#--</div>-->

          <#-- 메타 정보 (수정 페이지 필수) -->
          <@ui.panelMetaInfo post.updatedDate post.createdDate/>
          <#-- END : 메타 정보 (수정 페이지 필수) -->
      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>