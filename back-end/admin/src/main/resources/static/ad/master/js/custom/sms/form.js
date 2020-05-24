(function (window, document, $, undefined) {

    $(function () {

        $('#form-sms,#form-email').each(function () {

            $('#send-time-datetimepicker').datetimepicker({
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
                locale: 'ko',
                format: "YYYYMMDDHHmmss",
                toolbarPlacement: 'default',
                showTodayButton: true,
                showClear: true,
                showClose: true,
                ignoreReadonly: true,
                allowInputToggle: true,
                widgetPositioning: {
                    horizontal: 'left',
                    vertical: 'auto'
                }
            });

            $('#send-time-datetimepicker').on('dp.change', function (e) {

                if (e.date && "format" in e.date)
                    $('#input-send-time').val(e.date.format('YYYYMMDDHHmmss'));
            });

            $('#msgBody').on('keyup', function (e) {

                var str = $(this).val();
                var $msgByteSize = $('#msg-byte-size');
                var $msgSize = $('#msg-size');
                if (!isEmpty(str)) {
                    var bytes = str.replace(/[\0-\x7f]|([0-\u07ff]|(.))/g, "$&$1$2").length;
                    $msgByteSize.html(bytes + " Bytes");
                    if (bytes > 2000) {
                        // $msgSize.html('MMS');
                    } else if (bytes > 90) {
                        // $msgSize.html('LMS');
                    } else {
                        // $msgSize.html('SMS');
                    }
                } else {
                    // $msgSize.html('SMS');
                    $msgByteSize.html('0 Bytes');
                }
            });
        });
    });

})(window, document, window.jQuery);