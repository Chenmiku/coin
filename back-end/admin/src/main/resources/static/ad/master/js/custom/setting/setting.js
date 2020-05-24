// Custom jQuery
// ----------------------------------- 


(function (window, document, $, undefined) {

    $(function () {

        $('#form-update-setting').each(function () {
            var $form = $(this);

            var changeSelect = function () {
                var $selectInternational = $form.find('[name="international"]:checked');
                var $internationalMode = $('[data-type="internationalMode"]');
                if ($selectInternational.val() === 'false') {
                    $internationalMode.attr("disabled", true);
                    $internationalMode.attr("readonly", true);
                } else {
                    $internationalMode.attr("disabled", false);
                    $internationalMode.attr("readonly", false);
                }
            };

            $form.find('[name="international"]').on('change', function () {
                changeSelect();
            });

            changeSelect();
        })

    });

})(window, document, window.jQuery);