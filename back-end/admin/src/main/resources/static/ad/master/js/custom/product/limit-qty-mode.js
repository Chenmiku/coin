(function (window, document, $, undefined) {

  $(function () {

    $('.es-product-create-update').each(function () {

      // variables
      var $selectLimitQtyMode = $('[name="limitQtyMode"]');
      var $inputLimitQty = $("input[name=limitQty]");

      var collapse = function(val) {
        if (val === 'NOT') {
          $inputLimitQty.closest('div').hide();
        } else {
          $inputLimitQty.closest('div').show();
        }
      };

      $selectLimitQtyMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="limitQtyMode"]:checked').val());

    });

  });

})(window, document, window.jQuery);