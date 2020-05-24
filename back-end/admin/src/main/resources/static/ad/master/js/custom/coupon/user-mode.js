(function (window, document, $, undefined) {

  $(function () {

    $('.coupon-create-update').each(function () {

      // variables
      var $radioUserMode = $('[name="userMode"]');
      var $selectBuyerLevels = $('[name="buyerLevels"]');

      var collapse = function(val) {
        if (val === 'LEVEL') {
          $selectBuyerLevels.closest('div').show();
        } else {
          $selectBuyerLevels.closest('div').hide();
        }
      };

      $radioUserMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="userMode"]:checked').val());

    });

  });

})(window, document, window.jQuery);