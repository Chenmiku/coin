// Custom jQuery
// ----------------------------------- 

(function (window, document, $, undefined) {

    $(function () {

        $('#create-sample').each(function () {

            var template = $('#template-item-image').html();

            var bindingSortable = function () {

                var btnDelete = $('.btn-image-uploader-delete');
                btnDelete.off();
                btnDelete.on('click', function () {
                    var _this = $(this);
                    _this.off();
                    _this.closest('.box-image-uploader').remove();
                });

                var _sortable = sortable('.sortable', {
                    forcePlaceholderSize: true,
                    placeholder: '<div class="box-image-uploader empty-image-uploader"></div>'
                });

                _sortable[0].addEventListener('sortstart', function (e) {
                    //console.debug('---- sortstart ----');
                    //console.debug(e.detail);
                    /*

                     This event is triggered when the user starts sorting and the DOM position has not yet changed.

                     e.detail.item contains the current dragged element
                     e.detail.placeholder contains the placeholder element
                     e.detail.startparent contains the element that the dragged item comes from

                     */
                });

                _sortable[0].addEventListener('sortstop', function (e) {
                    //console.debug('---- sortstop ----');
                    //console.debug(e.detail);
                    /*

                     This event is triggered when the user stops sorting. The DOM position may have changed.

                     e.detail.item contains the element that was dragged.
                     e.detail.startparent contains the element that the dragged item came from.

                     */
                });

                _sortable[0].addEventListener('sortupdate', function (e) {
                    //console.debug('---- sortupdate ----');
                    //console.debug(e.detail);
                    /*

                     This event is triggered when the user stopped sorting and the DOM position has changed.

                     e.detail.item contains the current dragged element.
                     e.detail.index contains the new index of the dragged element (considering only list items)
                     e.detail.oldindex contains the old index of the dragged element (considering only list items)
                     e.detail.elementIndex contains the new index of the dragged element (considering all items within sortable)
                     e.detail.oldElementIndex contains the old index of the dragged element (considering all items within sortable)
                     e.detail.startparent contains the element that the dragged item comes from
                     e.detail.endparent contains the element that the dragged item was added to (new parent)
                     e.detail.newEndList contains all elements in the list the dragged item was dragged to
                     e.detail.newStartList contains all elements in the list the dragged item was dragged from
                     e.detail.oldStartList contains all elements in the list the dragged item was dragged from BEFORE it was dragged from it
                     */
                });
            };

            bindingSortable();

            $('#product-image-uploader').each(function () {

                var listThumb = $('#list-image-uploader');
                var _this = $(this);
                var inputFile = _this.find('#input-image-upload');
                var imageDropZone = _this;
                var limit = 2;

                inputFile.fileupload({
                    dataType: 'json',
                    sequentialUploads: true,
                    add: function (e, data) {

                        //console.debug("add");
                        //console.debug(data, 'data');

                        if (data.files.error) {
                            alert(data.files[0].error);
                        }

                        var uploadErrors = [];
                        var acceptFileTypes = /^image\/(jpe?g|png|gif|svg|blob)$/i;

                        if (data.originalFiles[0]['type'] != null && !acceptFileTypes.test(data.originalFiles[0]['type'])) {
                            uploadErrors.push('"gif", "jpeg", "jpg", "png", "svg", "blob" 이미지만 등록하세요.');
                        }

                        if (data.originalFiles[0]['size'] != null && data.originalFiles[0]['size'] > (1048576 * limit)) {
                            uploadErrors.push('파일이 너무 큽니다. 2MB이하로 등록하세요.');
                        }

                        if (uploadErrors.length > 0) {
                            alert(uploadErrors.join("\n"));
                        } else {
                            data.submit();
                        }
                    },
                    done: function (e, data) {
                        //console.debug("done");
                        //console.debug(data, 'data');
                        var list = data.result;

                        list.forEach(function (item) {
                            listThumb.append(Mustache.render(template, {image: item.url, index: $('.item-box-image').length}));
                        });

                        bindingSortable();
                    },

                    progressall: function (e, data) {
                        //console.debug("progressall");
                        var progress = parseInt(data.loaded / data.total * 100, 10);
                    },

                    fail: function (e, data) {
                        //console.debug(e);
                        //console.debug(data);
                        alert("서버와 통신 중 문제가 발생했습니다");
                    },
                    dropZone: imageDropZone
                });
            });
        });

    });

})(window, document, window.jQuery);