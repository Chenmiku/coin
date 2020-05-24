<#import "/spring.ftl" as spring/>
<#import "common/ui.ftl" as ui/>
<!DOCTYPE html>
<html lang="ko">

<head>
    <#include "common/head-meta.ftl">
</head>

<body>
<div class="wrapper">
  <div class="block-center mt-xl wd-xl">
    <!-- START panel-->
    <div class="panel panel-primary">
      <div class="panel-heading text-center">
        <a href="#" style="color: #fff;">
          <img src="<@spring.url"/assets/images/logo-wh.svg"/>" class="block-center" style="height: 26px">
        </a>
      </div>
      <div class="panel-body">
        <h5 class="text-center">관리자 페이지 로그인</h5>

        <form action="<@spring.url"/admin/login"/>" method="post" role="form" data-parsley-validate="" novalidate=""
              class="mb-lg">

          <div class="form-group has-feedback">
            <input type="email" name="email" placeholder="ID를 입력하세요." autocomplete="off" required autofocus
                   class="form-control" value="vuhongthaihy@gmail.com">
            <span class="fa fa-envelope form-control-feedback text-muted"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="password" name="password" placeholder="비밀번호를 입력하세요." required class="form-control"
                   value="1234">
            <span class="fa fa-lock form-control-feedback text-muted"></span>
          </div>
          <div class="clearfix">
              <#--<div class="checkbox c-checkbox pull-left mt0">-->
              <#--<label>-->
              <#--<input type="checkbox" value="" name="remember">-->
              <#--<span class="fa fa-check"></span>Remember Me</label>-->
              <#--</div>-->
              <#--<div class="pull-right"><a href="<@spring.url"recover.html"/>" class="text-muted">Forgot your password?</a>-->
              <#--</div>-->
              <#if RequestParameters.error??>
                <div role="alert" class="alert alert-danger">
                  로그인에 실패하였습니다.<br/>
                  아이디 또는 비밀번호가 잘못 입력되었습니다.
                </div>
              </#if>
              <#if RequestParameters.logout??>
                <div role="alert" class="alert alert-success">
                  로그아웃 되었습니다!
                </div>
              </#if>
          </div>
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <button type="submit" class="btn btn-block btn-primary mt-lg">로그인</button>
        </form>
          <#--<p class="pt-lg text-center">Need to Signup?</p><a href="<@spring.url"register.html"/>" class="btn btn-block btn-default">Register Now</a>-->
      </div>
    </div>
    <!-- END panel-->
    <div class="p-lg text-center">
      <span>&copy;</span>
      <span>2020</span>
      <span>-</span>
      <span>${appName}</span>
    </div>
  </div>
</div>
<#include "common/vendor.ftl">
<!-- PARSLEY-->
<script src="<@spring.url"/ad/vendor/parsleyjs/dist/parsley.min.js"/>"></script>
</body>

</html>