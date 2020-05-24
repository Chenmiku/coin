<#import "/spring.ftl" as spring/>
<#import "../common/ui.ftl" as ui/>
<!DOCTYPE html>
<html lang="ko">

<head>
    <#include "../common/head-meta.ftl">
    <#include "../common/css/list.ftl">
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
        <#include "../common/header.ftl" />
        <#include "body/list.ftl">
    </div>
  </section>

    <#-- Page footer-->
    <#include "../common/footer.ftl">
</div>

<#include "../common/vendor.ftl">
<#include "../common/script/list.ftl">

</body>

</html>