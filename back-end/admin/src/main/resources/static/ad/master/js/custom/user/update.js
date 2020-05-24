(function (window, document, $, undefined) {

    $(function () {

        $('#form-update-password').each(function(){

            $(this).parsley().on('form:submit', function () {

                var form = $('#form-update-password');
                var data = form.serializeObject();

                $.ajax({
                    url: form.attr('action'),
                    method: 'POST',
                    contentType: "application/json",
                    data: JSON.stringify(data)
                }).done(function (result) {
                    $.notify("업로드 되었습니다.", {status:"success"});
                    $('#modal-update-password').modal('hide');
                    $('#form-update-password')[0].reset();

                }).fail(function (jqXHR, textStatus) {
                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status:"danger"});
                    } else {
                        $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status:"danger"});
                    }
                });

                return false;
            });
        });
    });

})(window, document, window.jQuery);