// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('[data-type="active-language"]').each(function () {
            var $radio = $(this).find('[type="radio"]');

            var setRequired = function (is, $field) {
                var $tabpanel = $field.closest('[role="tabpanel"]');
                var $input = $tabpanel.find('[data-parsley-required]');
                $input.attr('data-parsley-required', is);
                console.debug(is, 'is');
                // console.debug($input, '$input');
                console.debug($input.length, '$input');

                // console.debug($input.attr('data-type'));
                if (is == 'true') {
                    $input.removeAttr('disabled');
                    $('[href="#' + $tabpanel.attr('id') + '"]').closest('[role="presentation"]').removeClass('inactive');
                    $input.each(function () {
                        if ($(this).attr('data-type') === 'froala-content') {
                            var $editor = $(this);
                            $editor.froalaEditor("edit.on");
                        }
                    });
                } else {
                    $input.attr('disabled', 'disabled');
                    $('[href="#' + $tabpanel.attr('id') + '"]').closest('[role="presentation"]').addClass('inactive');
                    $input.each(function () {
                        if ($(this).attr('data-type') === 'froala-content') {
                            var $editor = $(this);
                            setTimeout(function () {
                                $editor.froalaEditor("edit.off");
                            }, 500);
                        }
                    });
                }
            };

            var changeRadio = function () {

                $radio.each(function () {
                    var $this = $(this);
                    if ($this.is(":checked")) {
                        setRequired($this.val(), $this);
                    }
                });
            };

            $radio.on('change', function (e) {
                changeRadio();
            });

            changeRadio();
        });
    });

})(window, document, window.jQuery);