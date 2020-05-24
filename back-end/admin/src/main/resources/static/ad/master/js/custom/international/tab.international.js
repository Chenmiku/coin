// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {
        $('[data-type="tabpanel-language"]').each(function () {
            $(this).find('.nav-tabs a:first').tab('show');
        });

    });

})(window, document, window.jQuery);