// Custom jQuery
// ----------------------------------- 


(function (window, document, $, undefined) {

  $(function () {

    $('#form-update-buyer-level-setting').each(function () {
      var $form = $(this);

      var changeSelect = function () {
        var $selectEnabled = $form.find('[name="enabled"]:checked');
        var $buyerLevelSettingEnabled = $('[data-type="buyerLevelSettingEnabled"]');
        if ($selectEnabled.val() === 'false') {
          $buyerLevelSettingEnabled.attr("disabled", true);
          $buyerLevelSettingEnabled.attr("readonly", true);
        } else {
          $buyerLevelSettingEnabled.attr("disabled", false);
          $buyerLevelSettingEnabled.attr("readonly", false);
        }
      };

      $form.find('[name="enabled"]').on('change', function () {
        changeSelect();
      });

      changeSelect();
    })

  });

})(window, document, window.jQuery);