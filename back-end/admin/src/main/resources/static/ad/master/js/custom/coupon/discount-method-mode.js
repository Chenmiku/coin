(function (window, document, $, undefined) {

  $(function () {

    $('.coupon-create-update').each(function () {

      // variables
      var $radioMode = $('[name="discountMethod"]');
      var $divRate = $('[data-role="coupon-discount-rate"]');
      var $divPrice = $('[data-role="coupon-discount-price"]');
      var $divFixed = $('[data-role="coupon-discount-fixed"]');
      var $divPoint = $('[data-role="coupon-discount-point"]');

      var collapse = function(val) {
        if (val === 'PRICE') {
          $divRate.closest('div').hide();
          $divFixed.closest('div').hide();
          $divPoint.closest('div').hide();
          $divPrice.closest('div').show();
        } else if (val === 'FIXED_AMOUNT') {
          $divRate.closest('div').hide();
          $divPrice.closest('div').hide();
          $divPoint.closest('div').hide();
          $divFixed.closest('div').show();
        } else if (val === 'POINT') {
          $divRate.closest('div').hide();
          $divPrice.closest('div').hide();
          $divFixed.closest('div').hide();
          $divPoint.closest('div').show();
        } else {
          $divPrice.closest('div').hide();
          $divFixed.closest('div').hide();
          $divPoint.closest('div').hide();
          $divRate.closest('div').show();
        }
      };

      $radioMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="discountMethod"]:checked').val());

    });

  });

})(window, document, window.jQuery);