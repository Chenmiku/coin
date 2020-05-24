<#-- START widgets box-->
<div class="container-fuild">
    <#-- FORM -->
  <form id="form-email" data-type="email-create-update" action="<@spring.url "/admin/document/popup/send"/>" method="post" data-parsley-validate="" novalidate="" data-parsley-international="true">
    <div class="row">
      <div class="col-md-12">

        <div class="pull-left mb-lg">
          <button type="submit" class="btn btn-primary btn-lg">
            보내기
          </button>
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </div>

      </div>
    </div>
    <div class="row">
        <#-- CONTENT-->
      <div class="col-lg-12">

        <div class="panel panel-default">
          <div class="panel-body">

            <div class="form-group">
              <label class="control-label">수신자의 전화번호 <span class="text-require">*</span></label>
                <@spring.formTextarea "email.destPhone",
                "placeholder=\"수신자의 전화번호\"
                            class=\"form-control\"
                            rows=\"3\"
                            required=\"required\"
                            "/>
              <span class="help-block m-b-none"> 동보발송 시 콤마(,) 붙여서 전송, 예시: 01011112222,01011112223</span>
            </div>
            <div class="form-group">
              <label class="control-label">수신자의 이름</label>
                <@spring.formInput "email.destName",
                "placeholder=\"수신자의 이름\"
                            class=\"form-control\"
                            maxlength=\"255\"
                            required=\"required\"
                            " "text"/>
              <span class="help-block m-b-none">예시: 홍길동</span>
            </div>

            <div class="form-group">
              <label class="control-label">발송시간 (없을 경우 즉시발송)</label>
              <div id="send-time-datetimepicker" class="input-group date">
                  <@spring.bind path="email.sendTime"/>
                <input type="text"
                       class="form-control"
                       placeholder="발송시간"
                       data-input-target="input-send-time"
                       data-parsley-errors-container="#error-send-time"
                       value="${spring.stringStatusValue}"
                >
                <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
              </div>
              <div id="error-send-time"></div>
              <input
                      id="input-send-time"
                      type="hidden"
                      name="${spring.status.expression}"
                      value="${spring.stringStatusValue}"
              />
              <span class="help-block m-b-none">예시: "20130529171111" (2013-05-29 17:11:11)</span>
            </div>
            <hr/>
            <div class="form-group">
              <label class="control-label">제목 (LMS 전용)</label>
                <@spring.formInput "email.subject",
                "placeholder=\"제목\"
                            class=\"form-control\"
                            maxlength=\"255\"
                            " "text"/>
              <span class="help-block m-b-none">예시: 제목</span>
            </div>
            <div class="form-group">
              <label class="control-label">내용 <span class="text-require">*</span></label>
                <@spring.formTextarea "email.msgBody",
                "placeholder=\"내용\"
                            class=\"form-control\"
                            rows=\"3\"
                            required=\"required\"
                            "/>
              <div class="clearfix"></div>
              <span id="msg-byte-size" class="help-block m-b-none pull-right">0 Bytes</span>
                <#--<span id="msg-size" class="help-block m-b-none pull-right mr">SMS&nbsp;&nbsp;</span> -->
              <span class="help-block m-b-none pull-left">(90byte 초과 시 자동 lms로 전환 발송)</span>
            </div>

          </div>
        </div>
      </div>
        <#-- END : CONTENT-->

    </div>
  </form>
    <#-- END : FORM -->
</div>