// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        if ($.fn.datetimepicker) {
        var $datepickers = $('[data-type="datetimepicker"]');

            $datepickers.each(function () {

                var $format = $(this).data('format') === undefined ? "YYYY-MM-DD HH:mm:ss" : $(this).data('format');

                $(this).datetimepicker({
                    icons: {
                        time: 'fa fa-clock-o',
                        date: 'fa fa-calendar',
                        up: 'fa fa-chevron-up',
                        down: 'fa fa-chevron-down',
                        previous: 'fa fa-chevron-left',
                        next: 'fa fa-chevron-right',
                        today: 'fa fa-crosshairs',
                        clear: 'fa fa-trash',
                        close: 'fa fa-close'
                    },
                    locale: 'en',
                    format: $format,
                    toolbarPlacement: 'default',
                    showTodayButton: true,
                    showClear: true,
                    showClose: true,
                    ignoreReadonly: true,
                    allowInputToggle: true
                });
            });
        }
    });

})(window, document, window.jQuery);