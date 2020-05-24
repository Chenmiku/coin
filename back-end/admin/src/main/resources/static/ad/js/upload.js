// Image Upload
// ----------------------------------- 

(function (window, document, $, undefined) {

    $(function () {
        'use strict';

        $('.form-img-uploader').each(function () {

            var _this = $(this);
            var imageView = _this.find('.form-img-complete');
            var imageBox = _this.find('.form-img-box');
            var inputFile = _this.find('.form-input-file');
            var formImgBoxCenter = _this.find('.form-img-box-center');
            var imageHiddenInput = _this.find('.img-hidden-input');
            var imageDropZone = imageBox;

            //var imageView = $('#image-view');
            //var imageBox = $('.form-img-vox');

            inputFile.fileupload({
                dataType: 'json',
                sequentialUploads: true,
                add: function (e, data) {
                    if (data.files.error) alert(data.files[0].error);

                    var uploadErrors = [];
                    var acceptFileTypes = /^image\/(jpe?g|png)$/i;

                    if (data.originalFiles[0]['type'] != null && !acceptFileTypes.test(data.originalFiles[0]['type'])) {
                        uploadErrors.push('jpg, jpeg 또는 png 이미지만 등록하세요.');
                    }

                    if (data.originalFiles[0]['size'] != null && data.originalFiles[0]['size'] > 1048576) {
                        uploadErrors.push('파일이 너무 큽니다. 1MB이하로 등록하세요.');
                    }

                    if (uploadErrors.length > 0) {
                        alert(uploadErrors.join("\n"));
                    } else {
                        data.submit();
                    }
                },
                done: function (e, data) {

                    var result = data.result;
                    var filePath = result.path + result.filename;

                    imageView.attr('src', filePath);
                    imageView.css('display', 'block');

                    //$('.form-img-box-center').css('display', 'none');
                    formImgBoxCenter.css('display', 'none');

                    imageBox.css('background-color', '#ffffff');
                    imageBox.css('border', '0');

                    imageHiddenInput.val(filePath);
                    //$('#image').val(filePath);
                },

                progressall: function (e, data) {
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                },

                fail: function (e, data) {
                    console.debug(e);
                    console.debug(data);
                    alert("서버와 통신 중 문제가 발생했습니다");
                },
                dropZone: imageDropZone
            });

        });


    });

})(window, document, window.jQuery);