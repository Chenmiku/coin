<script id="template-upload-files" type="x-tmpl-mustache">
    <li data-type="item-upload-file" data-index="{{index}}">
        <a class="text-file" target="_blank" href="{{url}}" download>{{originalFilename}}</a>
        <a class="fileupload-delete" data-type="delete-file-upload" href="#" data-url="{{url}}">[삭제]</a>
        <input type="hidden" name="{{inputName}}[{{index}}].url" value="{{url}}" />
        <input type="hidden" name="{{inputName}}[{{index}}].orderAscending" value="{{index}}" />
        <input type="hidden" name="{{inputName}}[{{index}}].filename" value="{{filename}}" />
        <input type="hidden" name="{{inputName}}[{{index}}].mimeType" value="{{mimeType}}" />
        <input type="hidden" name="{{inputName}}[{{index}}].originalFilename" value="{{originalFilename}}" />
    </li>
</script>