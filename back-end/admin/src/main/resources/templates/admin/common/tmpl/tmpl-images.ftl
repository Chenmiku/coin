<script id="template-item-images" type="x-tmpl-mustache">
<li class="box-image-uploader item-box-image" style="background-image: url({{image}});">
    <a href="javascript:void(0);" class="btn-image-uploader-delete">
        <em class="icon-close"></em>
    </a>
    <input type="hidden" name="{{inputName}}[{{index}}].url" value="{{image}}"/>
    <input type="hidden" name="{{inputName}}[{{index}}].orderAscending" value="{{index}}" data-type="sortable-order" />

</script>