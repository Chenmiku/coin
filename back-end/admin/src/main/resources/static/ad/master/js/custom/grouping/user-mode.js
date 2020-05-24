(function (window, document, $, undefined) {

  $(function () {

    $('.grouping-create-update').each(function () {

      // variables
      var $radioUserMode = $('[name="userMode"]');
      var $selectBuyerLevels = $('[name="buyerLevels"]');

      var collapse = function(val) {
        if (val === 'ALL') {
          $selectBuyerLevels.closest('div').hide();
        } else {
          $selectBuyerLevels.closest('div').show();
        }
      };

      $radioUserMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="userMode"]:checked').val());

    });

  });

})(window, document, window.jQuery);