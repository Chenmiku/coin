<div id="modal-update-password" tabindex="-1" role="dialog" aria-labelledby="myModalLabelSmall" aria-hidden="true"
     class="modal fade">
  <div class="modal-dialog modal-md">
    <form id="form-update-password" data-parsley-validate="" novalidate=""
          action="<@spring.url "/admin/user/update/password"/>" method="post">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" data-dismiss="modal" aria-label="Close" class="close">
            <span aria-hidden="true">&times;</span>
          </button>
          <h4 id="myModalLabelSmall" class="modal-title">비밀번호 변경</h4>
        </div>
        <div class="modal-body">
            <@spring.formHiddenInput "user.id"/>
          <div class="form-group">
            <label class="control-label">비밀번호 <span class="text-require">*</span></label>
              <@spring.formPasswordInput "user.password",
              "placeholder=\"비밀번호를 입력하세요.\"
                        class=\"form-control\"
                        maxlength=\"50\"
                        required=\"required\"
                        data-parsley-minlength=\"8\"
                        data-parsley-maxlength=\"50\"
                        data-parsley-uppercase=\"1\"
                        data-parsley-lowercase=\"1\"
                        data-parsley-number=\"1\"
                        data-parsley-special=\"1\"
                        "/>
          </div>
          <div class="form-group">
            <label class="control-label">비밀번호 확인 <span class="text-require">*</span></label>
              <@spring.formPasswordInput "user.passwordConfirm",
              "placeholder=\"비밀번호를 확인하세요.\"
                        class=\"form-control \"
                        maxlength=\"255\"
                        required=\"required\"
                        data-parsley-equalto=\"#password\"
                        "/>
          </div>
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </div>
        <div class="modal-footer">
          <button type="button" data-dismiss="modal" class="btn btn-default">취소</button>
          <button type="submit" class="btn btn-primary">수정</button>
        </div>
      </div>
    </form>
  </div>
</div>