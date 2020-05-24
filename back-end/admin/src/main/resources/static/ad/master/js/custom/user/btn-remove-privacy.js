// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('#btn-remove-privacy').each(function () {
            var $btn = $(this);

            $btn.on('click', function (e) {
                e.preventDefault();
                var _this = $(this);
                swal({
                    title: "정말 개인정보를 파기하시겠습니까?",
                    text: "파기 후, 다시 복구할 수 없습니다.",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "네, 파기합니다.",
                    cancelButtonText: "아니오.",
                    closeOnConfirm: false,
                    closeOnCancel: false
                }, function (isConfirm) {

                    if (isConfirm) {

                        var form = document.createElement("form");
                        form.setAttribute("method", "post");
                        form.setAttribute("action", _this.data('action'));
                        document.body.appendChild(form);

                        var token = $("meta[name='_csrf']").attr("content");
                        var parameterName = $("meta[name='_csrf_param']").attr("content");

                        var inputCsrf = document.createElement("input");
                        inputCsrf.setAttribute("type", "hidden");
                        inputCsrf.setAttribute("name", parameterName);
                        inputCsrf.setAttribute("value", token);
                        form.appendChild(inputCsrf);

                        var inputId = document.createElement("input");
                        inputId.setAttribute("type", "hidden");
                        inputId.setAttribute("name", "id");
                        inputId.setAttribute("value", _this.data("id"));
                        form.appendChild(inputId);

                        form.submit();

                    } else {
                        swal("취소되었습니다.", "", "error");
                    }
                });
            });
        });
    });

})(window, document, window.jQuery);