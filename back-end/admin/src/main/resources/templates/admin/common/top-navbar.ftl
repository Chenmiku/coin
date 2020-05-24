<header class="topnavbar-wrapper">
  <!-- START Top Navbar-->
  <nav role="navigation" class="navbar topnavbar">
    <!-- START navbar header-->
    <div class="navbar-header">
      <a href="#/" class="navbar-brand">
        <div class="brand-logo">
          <img src="<@spring.url"/assets/images/logo-wh.svg"/>" alt="App Logo" class="img-responsive" style="height: 26px">
<#--            <h5 style="color: white; margin:0px; font-size: 14px">${appName} Admin</h5>-->
        </div>
        <div class="brand-logo-collapsed">
          <img src="<@spring.url"/assets/images/logo-single-wh.svg"/>" alt="App Logo" class="img-responsive" style="height: 26px; margin: 8px auto;">
<#--            <h5 style="font-size: 18px; color: white; margin-top:7px;">Admin</h5>&lt;#&ndash;&ndash;&gt;-->
        </div>
      </a>
    </div>
    <!-- END navbar header-->
    <!-- START Nav wrapper-->
    <div class="nav-wrapper">
      <!-- START Left navbar-->
      <ul class="nav navbar-nav">
        <li>
          <!-- Button used to collapse the left sidebar. Only visible on tablet and desktops-->
          <a href="#" data-trigger-resize="" data-toggle-state="aside-collapsed" class="hidden-xs">
            <em class="fa fa-navicon"></em>
          </a>
          <!-- Button to show/hide the sidebar on mobile. Visible on mobile only.-->
          <a href="#" data-toggle-state="aside-toggled" data-no-persist="true" class="visible-xs sidebar-toggle">
            <em class="fa fa-navicon"></em>
          </a>
        </li>
      </ul>
      <!-- END Left navbar-->
      <!-- START Right Navbar-->
      <ul class="nav navbar-nav navbar-right">
        <li>
          <a href="javascript:document.getElementById('form-admin-logout').submit();" title="Admin logout">
            <em class="icon-logout"></em>
          </a>
        </li>
          <#--<!-- Search icon&ndash;&gt;-->
          <#--<li>-->
          <#--<a href="#" data-search-open="">-->
          <#--<em class="icon-magnifier"></em>-->
          <#--</a>-->
          <#--</li>-->
          <#--<!-- START Offsidebar button&ndash;&gt;-->
          <#--<li>-->
          <#--<a href="#" data-toggle-state="offsidebar-open" data-no-persist="true">-->
          <#--<em class="icon-notebook"></em>-->
          <#--</a>-->
          <#--</li>-->
          <#--<!-- END Offsidebar menu&ndash;&gt;-->
      </ul>
      <!-- END Right Navbar-->
    </div>
    <!-- END Nav wrapper-->
    <!-- START Search form-->
      <#--<form role="search" action="<@spring.url"search.html"/>" class="navbar-form">-->
      <#--<div class="form-group has-feedback">-->
      <#--<input type="text" placeholder="Type and hit enter ..." class="form-control">-->

      <#--<div data-search-dismiss="" class="fa fa-times form-control-feedback"></div>-->
      <#--</div>-->
      <#--<button type="submit" class="hidden btn btn-default">Submit</button>-->
      <#--</form>-->
    <!-- END Search form-->
    <form action="<@spring.url "/admin/logout"/>" method="post" id="form-admin-logout">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </nav>
  <!-- END Top Navbar-->
</header>