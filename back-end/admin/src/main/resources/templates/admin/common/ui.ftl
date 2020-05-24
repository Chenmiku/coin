<#ftl strip_whitespace=true>

<#-- 기폰 입력폼 -->
<#macro formInputDefault path label required maxLength palceholder fieldType="text" dataType="">
  <div class="form-group">
    <label class="control-label">${label} <#if required><span class="text-require">*</span></#if></label>
      <@spring.formInput path,
      "placeholder=\"${palceholder!}\"
                            class=\"form-control\"
                            data-parsley-required=\"${required?c}\"
                            maxlength=\"${maxLength?c}\"
                            data-type=\"${dataType}\"" fieldType/>
      <#--<span class="help-block mb0"> </span>-->
  </div>
</#macro>


<#-- 텍스트 언어 양식 -->
<#macro formInputText path label required=false maxLength=255 palceholder="" type="text" attr="" message="" >
    <#assign requiredAttr = ''/>
    <#if required>
        <#assign requiredAttr = "data-parsley-required=\"${required?c}\""/>
    </#if>
  <div class="form-group">
    <label class="control-label">${label} <#if required><span class="text-require">*</span></#if></label>
      <@spring.formInput path,
      "placeholder=\"${palceholder!}\"
                            class=\"form-control\"
                             ${requiredAttr}
                            maxlength=\"${maxLength?c}\" ${attr!}" "${type}"/>
    <span class="help-block mb0">${message}</span>
  </div>
</#macro>

<#-- 텍스트 언어 양식 -->
<#macro formInputTextByLanguage path label required maxLength palceholder type="text" attr="" message="" >
    <#assign requiredAttr = ''/>
    <#if required>
        <#assign requiredAttr = "data-parsley-required=\"${required?c}\""/>
    </#if>
  <div class="form-group">
    <label class="control-label">${label} <#if required><span class="text-require">*</span></#if></label>
      <@spring.formInput path,
      "placeholder=\"${palceholder!}\"
                            class=\"form-control\"
                            ${requiredAttr}
                            data-parsley-language=\"true\"
                            maxlength=\"${maxLength?c}\" ${attr}" "${type}"/>
    <span class="help-block mb0">${message}</span>
  </div>
</#macro>


<#-- 텍스트 언어 양식 -->
<#macro formInpuTag path label required=false maxLength=255 palceholder="" type="text" attr="" message="" >
    <#assign requiredAttr = ''/>
    <#if required>
        <#assign requiredAttr = "data-parsley-required=\"${required?c}\""/>
    </#if>
  <div class="form-group">
    <label class="control-label">${label} <#if required><span class="text-require">*</span></#if></label>
      <@spring.formInput path,
      "placeholder=\"${palceholder!}\"
                            class=\"form-control\"
                            data-role=\"tagsinput\"
                             ${requiredAttr}
                            maxlength=\"${maxLength?c}\" ${attr!}" "${type}"/>
    <span class="help-block mb0">${message}</span>
  </div>
</#macro>


<#-- 판넬 메타 정보 -->
<#macro panelMetaInfo updatedDate createdDate>
  <div class="panel panel-default">
    <div class="panel-heading"><h4>메타 정보</h4></div>
    <div class="panel-body">

      <label class="control-label">최근 수정일 </label>
      <p class="form-control-static">
          <#if updatedDate?has_content>
              ${updatedDate.format('yyyy년 MM월 dd일 HH:mm:ss')}
          </#if></p>

      <hr/>

      <label class="control-label">등록일 </label>
      <p class="form-control-static">
          <#if createdDate?has_content>${createdDate.format('yyyy년 MM월 dd일 HH:mm:ss')}
          </#if></p>

    </div>
  </div>
</#macro>

<#-- 파일 업로드 단수 -->
<#macro uploadFile label="파일 업로드" fieldName="file" inputName="file" url="/admin/api/upload/file" fileObj="">
  <h5>${label!}</h5>
  <div data-type="wrap-file-upload">
  <span data-type="btn-upload-file"
        data-progress="#progress-${inputName!}"
        class="btn btn-default fileinput-button">
<i class="fa fa-fw fa-upload"></i>
  <span>파일 업로드</span>
<input data-type="upload-file" type="file" name="${fieldName!}" data-name="${inputName!}" data-url="${url!}"/>
  </span>

    <div id="progress-${inputName!}" class="mt" style="display: none;"></div>
    <ul data-type="uploaded-file" class="mt mb0">

        <#if fileObj?has_content>
          <li data-type="item-upload-file" >
            <a class="text-file" target="_blank" href="${fileObj.url!}">${fileObj.originalFilename!}</a>
            <a class="fileupload-delete" data-type="delete-file-upload" href="#" data-url="${fileObj.url}">[삭제]</a>
            <input type="hidden" name="${inputName}.url" value="${fileObj.url!}" />
            <input type="hidden" name="${inputName}.filename" value="${fileObj.filename!}" />
            <input type="hidden" name="${inputName}.mimeType" value="${fileObj.mimeType!}" />
            <input type="hidden" name="${inputName}.originalFilename" value="${fileObj.originalFilename!}" />
          </li>
        </#if>
    </ul>
  </div>
</#macro>

<#-- 파일 업로드 복수 -->
<#macro uploadFiles label="파일 업로드" fieldName="file" inputName="file" url="/admin/api/upload/file" uploadFiles=[]>

  <div data-type="wrap-file-upload">
  <span data-type="btn-upload-files"
        data-progress="#progress-${inputName!}"
        class="btn btn-default fileinput-button">
<i class="fa fa-fw fa-upload"></i>
  <span>파일 업로드</span>
<input data-type="upload-files" type="file" name="${fieldName!}" data-name="${inputName!}" data-url="${url!}" multiple/>
  </span>

    <div id="progress-${inputName!}" class="mt" style="display: none;"></div>
    <ul data-type="uploaded-file" class="mt mb0">
        <#if uploadFiles?has_content>

            <#list uploadFiles as uploadFile>
              <li data-type="item-upload-file" data-index="${uploadFile_index?c}">
                <a class="text-file" href="/api/v1/file-download?id=${uploadFile.id?c}"
                   download>${uploadFile.originalFilename}</a>
                <a class="fileupload-delete" data-type="delete-file-upload" href="#"
                   data-url="${uploadFile.url}">[삭제]</a>

                <input type="hidden" name="${inputName}[${uploadFile_index?c}].url" value="${uploadFile.url!}"/>
                <input type="hidden" name="${inputName}[${uploadFile_index?c}].orderAscending"
                       value="${uploadFile_index?c}"/>
                <input type="hidden" name="${inputName}[${uploadFile_index?c}].filename"
                       value="${uploadFile.filename!}"/>
                <input type="hidden" name="${inputName}[${uploadFile_index?c}].mimeType"
                       value="${uploadFile.mimeType!}"/>
                <input type="hidden" name="${inputName}[${uploadFile_index?c}].originalFilename"
                       value="${uploadFile.originalFilename!}"/>
              </li>
            </#list>

        </#if>
    </ul>
  </div>
</#macro>

<#-- 이미지 단수 -->
<#macro uploadImage label="이미지" fieldName="image" fieldValue="" comment="">
  <div data-type="upload-image" data-validator-target="${fieldName!}-validator" data-name="${fieldName!}"
       data-value="${fieldValue!}">
    <label class="control-label">${label!}</label>
    <small class="mb text-require"></small>
      <#if comment?has_content>
        <span class="help-block mt0">${comment}</span>
      </#if>
    <div class="row">
      <div class="col-md-12">
        <div class="form-image-uploader">

          <ul class="list-image-uploader list-unstyled sortable"></ul>

          <div class="wrapper-image-upload box-image-uploader">

            <span class="plus-image-upload">+</span>

            <input class="input-image-upload" type="file" name="file"
                   data-url="/admin/api/upload/image"
                   data-parsley-errors-container="#${fieldName!}-validator"/>
          </div>
        </div>
      </div>
    </div>

    <div id="${fieldName!}-validator">
    </div>
  </div>
</#macro>

<#-- 이미지 복수 -->
<#macro uploadImages label="이미지 (복수등록)" fieldName="image" imgUploadImages=[] comment="">
  <div data-type="upload-images" data-validator-target="${fieldName!}-validator"
       data-upload-images-target="${fieldName!}-upload-images" data-name="${fieldName!}">
    <label class="control-label">${label!}</label>
    <small class="mb text-require"></small>
      <#if comment?has_content>
        <span class="help-block mt0">${comment}</span>
      </#if>
    <div class="row">
      <div class="col-md-12">
        <div class="form-image-uploader">

          <ul class="list-image-uploader list-unstyled list-image-uploader sortable"></ul>

          <div class="wrapper-image-upload box-image-uploader">

            <span class="plus-image-upload">+</span>

            <input class="input-image-upload" type="file" name="file"
                   data-url="/admin/api/upload/image"
                   multiple
                   data-parsley-errors-container="#${fieldName!}-validator"/>
          </div>
        </div>
      </div>
    </div>

    <div id="${fieldName!}-validator">
    </div>

  </div>

    <#if imgUploadImages?has_content>
        <#list imgUploadImages as imgUploadImage>

          <input type="hidden" data-type="${fieldName!}-upload-images"
                 data-url="${imgUploadImage.url!}"
                 data-size="${imgUploadImage.size!}"
                 data-data-precision="${imgUploadImage.dataPrecision!}"
                 data-width="${imgUploadImage.width!}"
                 data-height="${imgUploadImage.height!}"
                 data-make="${imgUploadImage.make!}"
                 data-model="${imgUploadImage.model!}"
                 data-software="${imgUploadImage.software!}"
                 data-focal-length="${imgUploadImage.focalLength!}"
                 data-index="${imgUploadImage.orderAscending}">
        </#list>
    </#if>
</#macro>

<#--위즈윅 에디터-->
<#-- ALL : 전체 -->
<#-- ONLYTEXT : 텍스트 -->
<#macro wysiswygEdior path label="내용" required=false height=600 mode="ALL">
    <#assign requiredAttr = ''/>
    <#if required>
        <#assign requiredAttr = "data-parsley-required=\"${required?c}\""/>
    </#if>
    <@spring.bind path/>
  <div id="parsley-handler-${spring.status.expression?replace(".", "_")}" class="form-group">
    <label class="control-label">${label!} <#if required><span class="text-require">*</span></#if></label>
    <textarea name="${spring.status.expression}"
              data-type="froala-content"
  ${requiredAttr}
data-test="${required?c}"
              data-parsley-language="true"
              data-parsley-class-handler="#parsley-handler-${spring.status.expression?replace(".", "_")}"
              data-height="${height}"
              data-mode="${mode}"
    >${spring.stringStatusValue!}</textarea>
  </div>
</#macro>

<#-- TEXTAREA -->
<#macro formTextarea path label="내용" required=false rows=10 attr="" message="">
    <#assign requiredAttr = ''/>
    <#if required>
        <#assign requiredAttr = "data-parsley-required=\"${required?c}\""/>
    </#if>
    <@spring.bind path/>
  <div class="form-group">
    <label class="control-label">${label!} <#if required><span class="text-require">*</span></#if></label>
    <textarea name="${spring.status.expression}"
              ${requiredAttr}
              class="form-control"
              rows="${rows}"
              data-parsley-language="true"
              ${attr!}
    >${spring.stringStatusValue!}</textarea>
    <span class="help-block m-b-none">${message}</span>
  </div>
</#macro>

<#--불린 모드-->
<#macro formBoolean path label labelTrue="활성" labelFalse="비활성" comment="">
    <@spring.bind path/>
  <div class="form-group">
    <label class="control-label">${label}</label>
      <#if comment?has_content>
        <span class="help-block mt0">${comment}</span>
      </#if>
    <div>
      <label class="radio-inline c-radio">
        <input data-type="active" id="inline-radio-${spring.status.expression}-true" type="radio"
               name="${spring.status.expression}" value="true" <#if spring.stringStatusValue == 'true'>checked</#if>>
        <span class="fa fa-circle"></span>${labelTrue}
      </label>
      <label class="radio-inline c-radio">
        <input data-type="active" id="inline-radio-${spring.status.expression}-false" type="radio"
               name="${spring.status.expression}" value="false" <#if spring.stringStatusValue == 'false'>checked</#if>>
        <span class="fa fa-circle"></span>${labelFalse}
      </label>
    </div>
  </div>
</#macro>


<#--활성 모드-->
<#macro formActive path>
    <@spring.bind path/>
  <div class="form-group">
    <label class="control-label">상태 설정</label>
    <span class="help-block mt0">비활성일 경우 콘텐츠가 노출되지 않습니다.</span>
    <div>
      <label class="radio-inline c-radio">
        <input data-type="active" id="inline-radio-${spring.status.expression}-true" type="radio"
               name="${spring.status.expression}" value="true" <#if spring.stringStatusValue == 'true'>checked</#if>>
        <span class="fa fa-circle"></span>활성
      </label>
      <label class="radio-inline c-radio">
        <input data-type="active" id="inline-radio-${spring.status.expression}-false" type="radio"
               name="${spring.status.expression}" value="false" <#if spring.stringStatusValue == 'false'>checked</#if>>
        <span class="fa fa-circle"></span>비활성
      </label>
    </div>
  </div>
</#macro>

<#--채크박스 (enum) (TODO 테스트중)-->
<#macro formCheckboxEnum path label required=true list=[]>
    <@spring.bind path/>
  <div class="form-group">
    <label class="control-label">${label} <#if required><span class="text-require">*</span></#if></label>
      <#if list?size == 0>
        <p class="form-control-static">등록된 데이터가 없습니다.</p>
      <#else>
        <div class="row">
            <#list list as item>
              <div class="col-xs-6 col-md-4">
                <div class="checkbox c-checkbox">
                  <label><input name="${spring.status.expression}"
                                data-parsley-required="${required?c}"
                                type="checkbox" value="${item}"
                                <#if item.checked>checked</#if>><span class="fa fa-check"></span>${item.value!}</label>
                </div>
              </div>
            </#list>
        </div>
      </#if>
  </div>
</#macro>

<!-- 라디오 박스 (enum type) -->
<#macro formRadioboxEnum path label required=true list=[]>
    <@spring.bind path/>
  <div class="form-group">
    <label class="control-label">${label} <#if required><span class="text-require">*</span></#if></label>
      <#if list?size == 0>
        <p class="form-control-static">등록된 데이터가 없습니다.</p>
      <#else>
        <div>
            <#list list as item>
              <label class="radio-inline c-radio">
                <input id="inline-radio-${spring.status.expression}-${item_index}"
                       data-parsley-required="${required?c}"
                       type="radio" name="${spring.status.expression}" value="${item}"
                       <#if spring.stringStatusValue == item>checked</#if>>
                <span class="fa fa-circle"></span>${item.value}
              </label>
            </#list>
        </div>
      </#if>
  </div>
</#macro>

<#--카테고리-->
<#macro formCategory path label="카테고리" categories=[] message="">
    <@spring.bind path/>
  <div class="form-group">
    <label class="control-label"><h5 style="font-weight: bold">${label}</h5></label>
      <#if categories?size == 0>
        <p class="form-control-static">등록된 ${label}이(가) 없습니다.</p>
      <#else>
        <div class="row">

            <#list categories as category>
              <div class="col-xs-6 col-md-3">
                <div class="checkbox c-checkbox">
                  <label><input name="${spring.status.expression}" type="checkbox" value="${category.id?c}"
                                <#if category.checked>checked</#if>><span
                            class="fa fa-check"></span>${category.name.value!}
                  </label>
                </div>
              </div>
            </#list>
        </div>
      </#if>
  </div>
  <span class="help-block mb0">${message}</span>
</#macro>

<#-- 날짜 포맷 -->
<#macro formDate path label required=true format="YYYY-MM-DD HH:mm:ss">
    <@spring.bind path/>
  <div class="form-group">
    <label class="control-label">${label} <#if required><span class="text-require">*</span></#if></label>
    <div class="input-group date">
      <input type="text"
             class="form-control"
             data-parsley-required="${required?c}"
             data-type="datetimepicker"
             data-format="${format}"
             data-parsley-errors-container="#error-${spring.status.expression}"
             name="${spring.status.expression}"
             value="${spring.stringStatusValue}">
      <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
    </div>
    <div id="error-${spring.status.expression}"></div>
  </div>
</#macro>

<#--언어 모드 활성 모드-->
<#macro formActiveByLanguage path>
    <@spring.bind path/>
    <#if international>
      <hr/>
      <div class="form-group" data-type="active-language">
        <label class="control-label">활성 모드</label>
        <span class="help-block mt0">비활성일 경우 선택된 언어에서 노출되지 않고 데이터는 삭제됩니다.</span>
        <div>
          <label class="radio-inline c-radio">
            <input data-type="radio-active-language" id="inline-radio-${spring.status.expression}-true" type="radio"
                   name="${spring.status.expression}" value="true"
                   <#if spring.stringStatusValue == 'true'>checked</#if>>
            <span class="fa fa-circle"></span>활성
          </label>
          <label class="radio-inline c-radio">
            <input data-type="radio-active-language" id="inline-radio-${spring.status.expression}-false" type="radio"
                   name="${spring.status.expression}" value="false"
                   <#if spring.stringStatusValue == 'false'>checked</#if>>
            <span class="fa fa-circle"></span>비활성
          </label>
        </div>
      </div>
    <#else>
      <input type="hidden" name="${spring.status.expression}" value="${spring.stringStatusValue}">
    </#if>
</#macro>

<#-- 카테고리 이름 양식 -->
<#macro formCategoryByInputName path duplicate>
  <div class="form-group">
    <label class="control-label">이름 <span class="text-require">*</span></label>
      <@spring.formInput path,
      "placeholder=\"이름을 입력하세요.\"
                            class=\"form-control\"
                            maxlength=\"100\"
                            data-parsley-required=\"true\"
                            data-parsley-language=\"true\"
                            data-parsley-remote=\"${duplicate}\"
                            data-parsley-remote-options='{\"type\": \"POST\"}'
                            data-parsley-remote-validator=\"duplicate\"
                            data-parsley-remote-message=\"이미 사용되고 있는 이름입니다.\""/>
      <#--<span class="help-block mb0"> </span>-->
  </div>
</#macro>

<#-- 언어 탭 목록 -->
<#macro tabListByLanguage tabName>
    <#if international>
      <!-- Nav tabs-->
      <ul role="tablist" class="nav nav-tabs nav-justified" data-parsley-type="tab-language">

          <#if im.koKr>
            <li role="presentation">
              <a href="#${tabName}-ko" role="tab" data-toggle="tab" class="bb0">국문</a>
            </li>
          </#if>
          <#if im.enUs>
            <li role="presentation">
              <a href="#${tabName}-en" role="tab" data-toggle="tab" class="bb0">영문</a>
            </li>
          </#if>
          <#if im.zhCn>
            <li role="presentation">
              <a href="#${tabName}-zh-cn" role="tab" data-toggle="tab" class="bb0">중문(간체)</a>
            </li>
          </#if>
          <#if im.zhTw>
            <li role="presentation">
              <a href="#${tabName}-zh-tw" role="tab" data-toggle="tab" class="bb0">중문(번체)</a>
            </li>
          </#if>
          <#if im.jaJp>
            <li role="presentation">
              <a href="#${tabName}-ja" role="tab" data-toggle="tab" class="bb0">일문</a>
            </li>
          </#if>
      </ul>
    </#if>
</#macro>

<#--텍스트 에어리어-->
<#macro formTextareaDefault path label="내용" required=true maxLength=255 rows=3 placeholder="내용을 입력하세요.">
    <@spring.bind path/>
  <div class="form-group">
    <label class="control-label">${label!} <#if required><span class="text-require">*</span></#if></label>
    <div>
    <textarea name="${spring.status.expression}"
<#--              data-parsley-required="${required?c}"-->
              rows="${rows?c}"
              class="form-control"
              maxlength="${maxLength?c}"
              placeholder="${placeholder}"
    >${spring.stringStatusValue!}</textarea>
    </div>
  </div>
</#macro>
