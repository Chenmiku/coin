<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="description" content="">
<meta name="keywords" content="">
<title>${appName} | Admin</title>
<meta name="_csrf" content="${_csrf.token}"/>
<#-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<meta name="_csrf_param" content="${_csrf.parameterName}"/>

<#--<link rel="shortcut icon" href="/assets/images/favicon.ico"/>-->
<#-- =============== Froala Editor KEY ===============-->
<script id="fr-fek">try {
    (function (k) {
      localStorage.FEK = k;
      t = document.getElementById('fr-fek');
      t.parentNode.removeChild(t);
    })('${metaPlugin.froalaId!}')
  } catch (e) {
  }</script>

<#-- =============== VENDOR STYLES ===============-->
<#-- FONT AWESOME-->
<link rel="stylesheet" href="<@spring.url"/ad/vendor/fontawesome/css/font-awesome.min.css"/>">
<#-- SIMPLE LINE ICONS-->
<link rel="stylesheet" href="<@spring.url"/ad/vendor/simple-line-icons/css/simple-line-icons.css"/>">
<#-- ANIMATE.CSS-->
<link rel="stylesheet" href="<@spring.url"/ad/vendor/animate.css/animate.min.css"/>">
<#-- WHIRL (spinners)-->
<link rel="stylesheet" href="<@spring.url"/ad/vendor/whirl/dist/whirl.css"/>">
<#-- =============== PAGE VENDOR STYLES ===============-->
<#-- WEATHER ICONS-->
<link rel="stylesheet" href="<@spring.url"/ad/vendor/weather-icons/css/weather-icons.min.css"/>">
<#-- =============== BOOTSTRAP STYLES ===============-->
<link rel="stylesheet" href="<@spring.url"/ad/app/css/bootstrap.css"/>" id="bscss">
<#-- =============== APP STYLES ===============-->
<link rel="stylesheet" href="<@spring.url"/ad/app/css/app.css"/>" id="maincss">