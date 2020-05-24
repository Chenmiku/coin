// Custom jQuery
// ----------------------------------- 


(function (window, document, $, undefined) {

  $(function () {

    $('#form-update-point-setting').each(function () {
      var $form = $(this);

      var changeSelect = function () {
        var $selectEnabled = $form.find('[name="enabled"]:checked');
        var $pointSettingEnabled = $('[data-type="pointSettingEnabled"]');
        if ($selectEnabled.val() === 'false') {
          $pointSettingEnabled.attr("disabled", true);
          $pointSettingEnabled.attr("readonly", true);
        } else {
          $pointSettingEnabled.attr("disabled", false);
          $pointSettingEnabled.attr("readonly", false);
        }
      };

      $form.find('[name="enabled"]').on('change', function () {
        changeSelect();
      });

      changeSelect();
    })

  });

})(window, document, window.jQuery);