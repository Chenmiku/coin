// Upload Demo
// -----------------------------------

(function (window, document, $, undefined) {

    $(function () {
        'use strict';

        var deleteFileupload = function ($warp) {

            $warp.find('[data-type="delete-file-upload"]').on('click', function (e) {

                e.preventDefault();

                var $uploadedFile = $warp.find('[data-type="uploaded-file"]');
                var url = $(this).data('url');

                $.ajax({
                    url: "/admin/api/upload/file",
                    method: 'DELETE',
                    contentType: "application/json",
                    data: JSON.stringify({url: url})
                }).done(function (result) {
                    $.notify("파일은 삭제되었습니다. 수정버튼을 눌려주세요.", {status: "success"});
                    $uploadedFile.html('');

                }).fail(function (jqXHR, textStatus) {
                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
                    }
                });

            });
        };

        $('[data-type="upload-file"]').each(function () {

            var $fileupload = $(this);
            var $template = $('#template-upload-file').html();
            var $warp = $fileupload.closest('[data-type="wrap-file-upload"]');
            var $btnFileUpload = $warp.find('[data-type="btn-upload-files"]');
            var $uploadedFile = $warp.find('[data-type="uploaded-file"]');
            var $inputName = $fileupload.data('name');
            var $progress = $($btnFileUpload.data('progress'));

            deleteFileupload($warp);

            $fileupload.fileupload({
                dataType: 'json',
                sequentialUploads: true,
                add: function (e, data) {
                    $progress.show();

                    //console.debug("add");
                    //console.debug(data, 'data');

                    if (data.files.error) {
                        alert(data.files[0].error);
                    }

                    var uploadErrors = [];

                    if (uploadErrors.length > 0) {
                        alert(uploadErrors.join("\n"));
                    } else {
                        data.submit();
                    }
                },
                done: function (e, data) {
                    // console.debug("done");
                    // console.debug(data, 'data');
                    var item = data.result;
                    item['inputName'] = $inputName;
                    item['index'] = 0;

                    $uploadedFile.html(Mustache.render($template, item));
                    $progress.hide();
                    deleteFileupload($warp);
                },

                progressall: function (e, data) {
                    //console.debug("progressall");
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    $progress.html('Uploading data progress (' + progress + '%)');
                    console.debug(progress, 'progress');
                },

                fail: function (e, data) {
                    // console.debug(e);
                    // console.debug(data);
                    $progress.html('');

                    if(data.jqXHR.responseJSON) {
                        // alert("서버와 통신 중 문제가 발생했습니다");
                        $.notify(data.jqXHR.responseJSON.message, {status: "danger"});
                    }
                }
            });
        });
    });

})(window, document, window.jQuery);