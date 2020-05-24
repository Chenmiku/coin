// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('.timepicker-hhmm').each(function () {
            $(this).datetimepicker({
                format: 'HH:mm'
            });
        });

        $('.timepicker-lt').each(function () {
            $(this).datetimepicker({
                format: 'LT'
            });
        });
    });

})(window, document, window.jQuery);