<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html lang="ko">

<head>
    <#include "../common/head-meta.ftl">
    <#include "../common/css/create.ftl">
    <#-- CHOSEN -->
  <link rel="stylesheet" href="<@spring.url "/ad/vendor/chosen_v1.2.0/chosen.min.css"/>">
    <#-- FILE UPLOAD -->
  <link rel="stylesheet" href="<@spring.url "/ad/vendor/blueimp-file-upload/css/jquery.fileupload.css"/>">
    <#-- TAG INPUT -->
  <link rel="stylesheet" href="<@spring.url "/ad/vendor/bootstrap-tagsinput/dist/bootstrap-tagsinput.css"/>">
</head>
<body>
<div class="wrapper">
    <#-- top navbar-->

    <#-- sidebar-->

    <#-- Main section -->
  <section style="margin-left: 0;">
      <#-- Page content-->
    <div class="content-wrapper">
        <#include "body/email-popup.ftl">
    </div>
  </section>

</div>

<#include "../common/vendor.ftl">
<#include "../common/script/create.ftl">
<#-- CHOSEN -->
<script src="<@spring.url "/ad/vendor/chosen_v1.2.0/chosen.jquery.min.js"/>"></script>
<#-- MUSTACHE-->
<script src="<@spring.url "/ad/vendor/mustache.js/mustache.min.js"/>"></script>
<#--### FILEUPLOAD ###-->
<#-- JQUERY UI -->
<script src="<@spring.url"/ad/vendor/jquery-ui/ui/widget.js"/>"></script>
<#-- JQUERY FRAME TRANSPORT -->
<script src="<@spring.url"/ad/vendor/blueimp-file-upload/js/jquery.iframe-transport.js"/>"></script>
<#-- JQUERY BASIC FILE UPLOAD -->
<script src="<@spring.url"/ad/vendor/blueimp-file-upload/js/jquery.fileupload.js"/>"></script>
<#-- BOOTSTRAP TAGSINPUT -->
<script src="<@spring.url"/ad/vendor/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"/>"></script>

<#-- MODALS -->
</body>
</html>