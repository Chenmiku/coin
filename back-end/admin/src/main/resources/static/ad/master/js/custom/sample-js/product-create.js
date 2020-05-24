(function (window, document, $, undefined) {

    $(function () {
        window.checkUnload = false;

        //$('input, textarea').on('keydown', function () {
        //    window.checkUnload = true;
        //});
        //
        //window.onbeforeunload = function () {
        //    if (window.checkUnload) {
        //        return "이 페이지를 벗어나면 작성된 내용은 저장되지 않습니다.";
        //    }
        //};

        //$(window).on("beforeunload", function () {
        //    alert(window.checkUnload);
        //    if (window.checkUnload) return "이 페이지를 벗어나면 작성된 내용은 저장되지 않습니다.";
        //});

        $('#create-sample').each(function () {
            $('.chosen-select').chosen();
        });

        $('#form-create-sample').each(function () {
            $(this).on('submit', function () {
                var form = $(this);

                var data = form.serializeObject();
                console.debug(data, 'data');

                if (!data['images[0].url']) {
                    $('#product-image-validator').addClass("filled");
                } else {
                    $('#product-image-validator').removeClass("filled");
                }

                window.checkUnload = false;
                return true;
                //return false;
            })
        });
    });

})(window, document, window.jQuery);