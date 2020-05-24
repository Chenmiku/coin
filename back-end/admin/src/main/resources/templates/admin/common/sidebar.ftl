<#assign menuEnabled = false/>
<aside class="aside">
    <#-- START Sidebar (left)-->
  <div class="aside-inner">
    <nav data-sidebar-anyclick-close="" class="sidebar">
        <#-- START sidebar nav-->
      <ul class="nav">

          <#list sidebar.sections as section>

            <!-- ROLE -->
              <#if section.roles?has_content>
                  <#list section.roles as access_role>
                      <#if access_role.role == currentRole>
                          <#assign menuEnabled = true/>
                          <#break/>
                      <#else>
                          <#assign menuEnabled = false/>
                      </#if>
                  </#list>
              </#if>

              <#if menuEnabled>
                <li class="nav-heading ">
                  <span data-localize="sidebar.heading.HEADER">${section.title}</span>
                </li>
                  <#list section.elements as element>

                      <#if element.roles?has_content>
                          <#list element.roles as access_role>
                              <#if access_role.role == currentRole>
                                  <#assign menuEnabled = true/>
                                  <#break/>
                              <#else>
                                  <#assign menuEnabled = false/>
                              </#if>
                          </#list>
                      </#if>

                      <#if menuEnabled>
                      <#-- 단일 폴더 -->
                          <#if element.type == 'ONE'>
                            <li class="<#if springMacroRequestContext.requestUri?contains(element.url)>active</#if>">
                              <a href="<@spring.url element.url/>" title="${element.title}">
                                  <#if element.count??>
                                      <#if subElement.count != "0">
                                        <div class="pull-right label ${element.label}">${element.count}</div>
                                      </#if>
                                  </#if>
                                <em class="${element.icon}"></em>
                                <span>${element.title}</span>
                              </a>
                            </li>
                          <#-- 멀티 폴더 -->
                          <#elseif element.type == 'MULTI'>
                            <li class="">
                              <a href="#${element.id}" title="Menu" data-toggle="collapse">
                                <em class="${element.icon}"></em>
                                <span data-localize="sidebar.nav.menu.MENU">${element.title}</span>
                              </a>
                              <ul id="${element.id}" class="nav sidebar-subnav collapse">
                                <li class="sidebar-subnav-header">상품 관리</li>
                                  <#list element.section.elements as subElement>

                                      <#if subElement.roles?has_content>
                                          <#list subElement.roles as access_role>
                                              <#if access_role.role == currentRole>
                                                  <#assign menuEnabled = true/>
                                                  <#break/>
                                              <#else>
                                                  <#assign menuEnabled = false/>
                                              </#if>
                                          </#list>
                                      </#if>

                                      <#if menuEnabled>
                                        <li class="<#if springMacroRequestContext.requestUri?contains(subElement.url)>active</#if>">
                                          <a href="<@spring.url subElement.url/>" title="${subElement.title}">
                                              <#if subElement.count??>
                                                  <#if subElement.count != "0">
                                                    <div class="pull-right label ${subElement.label}">${subElement.count}</div>
                                                  </#if>
                                              </#if>
                                            <span>${subElement.title}</span>
                                          </a>
                                        </li>
                                      </#if>
                                  </#list>
                              </ul>
                            </li>
                          </#if>
                      </#if>
                  </#list>
              </#if>
          </#list>

      </ul>
        <#-- END sidebar nav-->
    </nav>
  </div>
    <#-- END Sidebar (left)-->
</aside>