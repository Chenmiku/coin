<#import "/spring.ftl" as spring/>
<#-- START widgets box-->
<div class="container-fluid contact-create-update">
    <#-- FORM -->
  <form id="form-create-contact" action="<@spring.url header.url + "/create"/>" method="post" data-parsley-validate=""
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

        <div class="panel panel-default">

          <div class="panel-body">

              <#--<!-- 제목 &ndash;&gt;-->
              <#--<div class="form-group">-->
              <#--<label class="control-label">제목 <span class="text-require">*</span></label>-->
              <#--<@spring.formInput "contact.title",-->
              <#--"placeholder=\"제목을 입력하세요.\"-->
              <#--class=\"form-control\"-->
              <#--maxlength=\"100\"-->
              <#--required"/>-->
              <#--&lt;#&ndash;<span class="help-block m-b-none"> </span>&ndash;&gt;-->
              <#--</div>-->

              <#--<hr/>-->

              <#-- WYSISWYG EDITOR -->
              <@ui.wysiswygEdior "contact.content" "내용" true/>
              <#-- END : WYSISWYG EDITOR -->

          </div>
        </div>

      </div>
        <#-- END : CONTENT-->
        <#-- SIDBAR -->
      <div class="col-lg-5 pl0-lg">

        <div class="panel panel-default">
          <div class="panel-body">
            <!-- 성명 -->
            <div class="form-group">
              <label class="control-label">성명 <span class="text-require">*</span></label>
                <@spring.formInput "contact.fullName",
                "placeholder=\"성명을 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"100\"
                            required"/>
                <#--<span class="help-block m-b-none"> </span>-->
            </div>

            <hr/>

            <!-- 휴대폰 번호 -->
            <div class="form-group">
              <label class="control-label">휴대폰 번호 <span class="text-require">*</span></label>
                <@spring.formInput "contact.mobile",
                "placeholder=\"휴대폰 번호를 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"100\"
                            required"/>
                <#--<span class="help-block m-b-none"> </span>-->
            </div>

            <hr/>

            <!-- 이메일 -->
            <div class="form-group">
              <label class="control-label">이메일 <span class="text-require">*</span></label>
                <@spring.formInput "contact.email",
                "placeholder=\"이메일을 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"255\"
                            required" "email"/>
                <#--<span class="help-block m-b-none"> </span>-->
            </div>


          </div>
        </div>

      </div>
        <#-- END : SIDBAR -->
    </div>
  </form>
</div>