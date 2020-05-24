// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {
        $('[data-parsley-international="true"]').each(function () {

            var $this = $(this);

            $this.parsley().on('field:error', function () {
                console.debug('ERROR');
                console.debug($(this.$element));
                var $field = $(this.$element);
                if ($field.data('parsleyLanguage') === true) {
                    var $tabpanel = $field.closest('[role="tabpanel"]');
                    $('[href="#' + $tabpanel.attr('id') + '"]').closest('[role="presentation"]').addClass('parsley-error');
                }
            });

            $this.parsley().on('field:success', function () {

                var $field = $(this.$element);
                if ($field.data('parsleyLanguage') === true) {
                    var $tabpanel = $field.closest('[role="tabpanel"]');
                    $('[href="#' + $tabpanel.attr('id') + '"]').closest('[role="presentation"]').removeClass('parsley-error');
                }
            });

            $this.parsley().on('form:submit', function (formInstance) {

                var $hasRequired = false;
                var $valueCount = 0;

                $('[data-type="active-language"]').find('[type="radio"]:checked').each(function () {
                    $hasRequired = true;
                    if ($(this).val() == 'true') {
                        $valueCount = $valueCount + 1;
                    }
                });

                if ($hasRequired && $valueCount === 0) {
                    $.notify("적어도 한가지 언어는 활성되어야 합니다.", {status: "warning"});
                    return false;
                }

                return true;
            });
        });

    });

})(window, document, window.jQuery);