<script id="template-upload-file" type="x-tmpl-mustache">
<#--    <li data-type="item-upload-file">-->
<#--        <a class="text-file" target="_blank" href="{{url}}" download>{{originalFilename}}</a>-->
<#--        <a class="fileupload-delete" data-type="delete-file-upload" href="#" data-url="{{url}}">[삭제]</a>-->
<#--        <input type="hidden" name="{{inputName}}" value="{{url}}" />-->
<#--    </li>-->
  <li data-type="item-upload-file" data-index="{{index}}">
        <a class="text-file" target="_blank" href="{{url}}">{{originalFilename}}</a>
        <a class="fileupload-delete" data-type="delete-file-upload" href="#" data-url="{{url}}">[삭제]</a>
        <input type="hidden" name="{{inputName}}.url" value="{{url}}" />
        <input type="hidden" name="{{inputName}}.filename" value="{{filename}}" />
        <input type="hidden" name="{{inputName}}.mimeType" value="{{mimeType}}" />
        <input type="hidden" name="{{inputName}}.originalFilename" value="{{originalFilename}}" />
    </li>
</script>