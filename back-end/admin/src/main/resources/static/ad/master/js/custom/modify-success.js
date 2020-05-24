// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('#modify-success').each(function () {
            var $this = $(this);

            setTimeout(function () {
                $this.fadeOut();
            }, 1500);
        });

    });

})(window, document, window.jQuery);