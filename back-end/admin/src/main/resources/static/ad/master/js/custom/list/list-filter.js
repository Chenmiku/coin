// Custom jQuery
// -----------------------------------

(function (window, document, $, undefined) {

    $(function () {

        var isdestroying = false;

        var initDatetimepicker = function () {

            if ($.fn.datetimepicker) {
                var datepickers = $('.list-filter-date');
                var inputStartDate = $("input[name='startDate']").val();
                var inputEndDate = $("input[name='endDate']").val();

                datepickers.each(function () {
                    var $format = $(this).data('format') === undefined ? "YYYY-MM-DD HH:mm:ss" : $(this).data('format');

                    var datepicker = $(this).datetimepicker({
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
                        format: $format,
                        toolbarPlacement: 'default',
                        showTodayButton: true,
                        showClear: true,
                        showClose: true,
                        ignoreReadonly: true,
                        allowInputToggle: true
                    });

                    if (datepicker.data('type') === 'startDate') {

                        if (inputStartDate) {
                            setTimeout(function () {
                                $(".list-filter-date[data-type='endDate']").data("DateTimePicker").minDate(inputStartDate);
                            }, 0);
                        }
                        datepicker.data("DateTimePicker").options({useCurrent: false});
                    } else if (datepicker.data('type') === 'endDate') {

                        if (inputEndDate) {
                            setTimeout(function () {
                                $(".list-filter-date[data-type='startDate']").data("DateTimePicker").maxDate(inputEndDate);
                            }, 0);
                        }
                        datepicker.data("DateTimePicker").options({useCurrent: false});
                    }
                });

                datepickers.each(function () {
                    var datepicker = $(this);

                    datepicker.on('dp.change', function (e) {

                        if (!isdestroying) {

                            var _this = $(this);
                            var type = _this.data('type');

                            if (type === 'startDate') {
                                $(".list-filter-date[data-type='endDate']").data("DateTimePicker").minDate(e.date);
                            } else if (type === 'endDate') {
                                $(".list-filter-date[data-type='startDate']").data("DateTimePicker").maxDate(e.date);
                            }
                        }
                    });
                });
            }
        };
        var clearDatetimepicker = function () {
            isdestroying = true;
            setTimeout(function () {
                var datepickers = $('.list-filter-date');

                datepickers.each(function () {
                    var datepicker = $(this);
                    datepicker.data("DateTimePicker").minDate(false);
                    datepicker.data("DateTimePicker").maxDate(false);
                    datepicker.data("DateTimePicker").clear();
                });

                isdestroying = false;
            }, 0);
        };

        $('#initial-list-filter').on('click', function (e) {
            e.preventDefault();

            $('.panel.panel-list-filter').on('panel.refresh', function (e, panel) {

                setTimeout(function () {

                    panel.removeSpinner();

                    clearDatetimepicker();
                    var form = $('#form-list-filter');
                    console.debug(form);
                    form.find('input').val('');
                    form.find('select').val('');
                    setTimeout(function () {
                        form.submit();
                    }, 5);
                }, 200);

            });
        });

        $('#submit-list-filter').on('click', function (e) {
            e.preventDefault();

            var form = $('#form-list-filter');
            form.submit();
        });

        $('#form-list-filter').find('input[name="query"]').on('keyup' , function(e){
            if (e.keyCode == 13) {
                var form = $('#form-list-filter');
                form.submit();
            }
        });

        /* 엑셀 다운로드 */
        var isClickExcel = false;
        $('#excel-list-filter').on('click', function (e) {
            e.preventDefault();

            if (isClickExcel) {
                return;
            }
            $.notify("다운로드 중입니다...", {});
            isClickExcel = true;

            var objects = $('#form-list-filter').serializeObject();
            var token = $("meta[name='_csrf']").attr("content");
            var param = $("meta[name='_csrf_param']").attr("content");

            var form = document.createElement("form");
            form.setAttribute("method", "post");
            form.setAttribute("action", $(this).data('action'));
            document.body.appendChild(form);

            var inputs = [];
            inputs.push({name: param, value: token});

            for (var key in objects) {
                inputs.push({name: key, value: objects[key]});
            }

            inputs.forEach(function (item) {
                var input = document.createElement("input");
                input.setAttribute("type", "hidden");
                input.setAttribute("name", item.name);
                input.setAttribute("value", item.value);
                form.appendChild(input);
            });

            form.submit();

            setTimeout(function () {
                isClickExcel = false;
                form.remove();
            }, 5000);
        });

        initDatetimepicker();
    });

})(window, document, window.jQuery);
