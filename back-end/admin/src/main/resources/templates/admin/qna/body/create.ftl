<#import "/spring.ftl" as spring/>
<#-- START widgets box-->
<div class="container-fluid qna-create-update">
    <#-- FORM -->
  <form id="form-create-qna" action="<@spring.url header.url + "/create"/>" method="post" data-parsley-validate=""
        novalidate="">
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
        <div class="panel panel-default">

          <div class="panel-body">

            <!-- 제목 -->
              <@ui.formInputText "qna.title" "제목" true 100 "제목을 입력하세요."/>

            <hr/>

              <#-- WYSISWYG EDITOR -->
              <@ui.wysiswygEdior "qna.content" "내용" true/>
              <#-- END : WYSISWYG EDITOR -->

            <hr/>
              <#-- WYSISWYG EDITOR -->
              <@ui.wysiswygEdior "qna.answer.content" "답변" false/>
              <#-- END : WYSISWYG EDITOR -->

          </div>
        </div>

      </div>
        <#-- END : CONTENT-->
        <#-- SIDBAR -->
      <div class="col-lg-5 pl0-lg">

        <div class="panel panel-default">
          <div class="panel-body">

            <hr/>
              <#-- 카테고리 -->
              <@ui.formCategory "qna.categories" "카테고리" categories/>
          </div>
        </div>

        <div class="panel panel-default">
          <div class="panel-heading">설정</div>
          <div class="panel-body">

              <#-- 활성 모드 -->
              <@ui.formActive "qna.active"/>

          </div>
        </div>
      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>