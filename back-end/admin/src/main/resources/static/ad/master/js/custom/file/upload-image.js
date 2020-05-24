(function (window, document, $, undefined) {

    $(function () {

        $('[data-type="upload-image"]').each(function () {

            var $this = $(this);
            var template = $('#template-item-image').html();
            var updatePath = $this.data('value'); // 업데이할 이미지 data-value
            var inputName = $this.data('name');
            var validatorTarget = $this.data('validatorTarget');

            var loadImage = function (url) {
                if (!isEmpty(url)) {
                    var listImage = $this.find('.list-image-uploader');
                    listImage.append(Mustache.render(template, {
                        image: url,
                        inputName: inputName
                    }));
                }
            };

            var refreshPlusBtn = function () {
                if ($this.find('.item-box-image').length > 0) {
                    $this.find('.wrapper-image-upload').hide();
                } else {
                    $this.find('.wrapper-image-upload').show();
                }
            };

            var deleteImageBtn = function () {

                var btnDelete = $this.find('.btn-image-uploader-delete');
                btnDelete.off();
                btnDelete.on('click', function () {
                    var $btnDelete = $(this);
                    $btnDelete.off();
                    $btnDelete.closest('.box-image-uploader').remove();
                    refreshPlusBtn();
                });
            };

            loadImage(updatePath);
            refreshPlusBtn();
            deleteImageBtn();

            $this.find('.wrapper-image-upload').each(function () {

                var $wrapper = $(this);
                var inputFile = $wrapper.find('.input-image-upload');
                var imageDropZone = $wrapper;
                var limit = 3000;

                inputFile.fileupload({
                    dataType: 'json',
                    sequentialUploads: true,
                    singleFileUploads: false,
                    add: function (e, data) {
                        //console.debug("add");
                        //console.debug(data, 'data');

                        var uploadErrors = [];
                        var acceptFileTypes = /^image\/(jpe?g|png|gif|svg|blob)$/i;

                        var oriFile = data.originalFiles[0];

                        if (oriFile['type'] != null && !acceptFileTypes.test(oriFile['type'])) {
                            uploadErrors.push('"gif", "jpeg", "jpg", "png", "svg", "blob" 이미지만 등록하세요.');
                        }

                        if (oriFile['size'] != null && oriFile['size'] > (1048576 * limit)) {
                            uploadErrors.push('파일이 너무 큽니다. 3GB이하로 등록하세요.');
                        }

                        if (uploadErrors.length > 0) {
                            alert(uploadErrors.join("\n"));
                        } else {
                            $(validatorTarget).find('.parsley-errors-list').removeClass("filled");
                            data.submit();
                        }
                    },
                    done: function (e, data) {
                        // console.debug("done");
                        console.debug(data, 'data');

                        var item = data.result;
                        console.debug(item, 'result');

                        loadImage(item.url);

                        refreshPlusBtn();
                        deleteImageBtn();
                    },

                    progressall: function (e, data) {
                        //console.debug("progressall");
                        var progress = parseInt(data.loaded / data.total * 100, 10);
                    },

                    fail: function (e, data) {
                        //console.debug(e);
                        //console.debug(data);
                        if(data.jqXHR.responseJSON) {
                            // alert("서버와 통신 중 문제가 발생했습니다");
                            $.notify(data.jqXHR.responseJSON.message, {status: "danger"});
                        }
                    },
                    dropZone: imageDropZone
                });
            });
        });

    });

})(window, document, window.jQuery);