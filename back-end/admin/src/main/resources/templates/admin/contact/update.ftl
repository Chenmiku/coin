<#import "/spring.ftl" as spring/>
<#import "../common/ui.ftl" as ui/>
<!DOCTYPE html>
<html lang="ko">

<head>
    <#include "../common/head-meta.ftl">
    <#include "../common/css/update.ftl">
    <#-- FILE UPLOAD -->
  <link rel="stylesheet" href="<@spring.url "/ad/vendor/blueimp-file-upload/css/jquery.fileupload.css"/>">
  <!-- TAGS INPUT-->
  <link rel="stylesheet" href="<@spring.url "/ad/vendor/bootstrap-tagsinput/dist/bootstrap-tagsinput.css"/>">
</head>
<body>
<div class="wrapper">
    <#-- top navbar-->
    <#include "../common/top-navbar.ftl">
    <#-- sidebar-->
    <#include "../common/sidebar.ftl">

    <#-- Main section -->
  <section>
      <#-- Page content-->
    <div class="content-wrapper">
        <#include "../common/header.ftl">
        <#include "body/update.ftl">
    </div>
  </section>

    <#-- Page footer-->
    <#include "../common/footer.ftl">
</div>
<#include "../common/tmpl/tmpl-image.ftl"/>

<#include "../common/vendor.ftl">
<#include "../common/script/update.ftl">

<#-- MUSTACHE-->
<script src="<@spring.url "/ad/vendor/mustache.js/mustache.min.js"/>"></script>
<#--### FILEUPLOAD ###-->
<#-- JQUERY UI -->
<script src="<@spring.url"/ad/vendor/jquery-ui/ui/widget.js"/>"></script>
<#-- JQUERY FRAME TRANSPORT -->
<script src="<@spring.url"/ad/vendor/blueimp-file-upload/js/jquery.iframe-transport.js"/>"></script>
<#-- JQUERY BASIC FILE UPLOAD -->
<script src="<@spring.url"/ad/vendor/blueimp-file-upload/js/jquery.fileupload.js"/>"></script>
<#-- SORTABLE -->
<script src="<@spring.url "/ad/vendor/html.sortable/dist/html.sortable.js"/>"></script>
<!-- TAGS INPUT-->
<script src="<@spring.url "/ad/vendor/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"/>"></script>
</body>

</html>