(function (window, document, $, undefined) {

  $(function () {

    $('.coupon-create-update').each(function () {

      // variables
      var $radioMode = $('[name="type"]');
      var $divLimit = $('[data-role="coupon-type-limit"]');
      var $divPeriod = $('[data-role="coupon-type-period"]');
      var $divCycle = $('[data-role="coupon-type-cycle"]');
      var $divDays = $('[data-role="coupon-type-days"]');

      var collapse = function(val) {
        if (val === 'DOWNLOAD') {
          $divCycle.hide();
          $divDays.hide();
          $divPeriod.show();
          $divLimit.show();
        } else if (val === 'AUTOMATIC') {
          $divPeriod.hide();
          $divLimit.hide();
          $divCycle.show();
          $divDays.show();
        } else if (val === 'BIRTHDAY') {
          $divPeriod.hide();
          $divLimit.hide();
          $divCycle.hide();
          $divDays.show();
        } else {
          $divLimit.hide();
          $divCycle.hide();
          $divDays.hide();
          $divPeriod.show();
        }
      };

      $radioMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="type"]:checked').val());

    });

  });

})(window, document, window.jQuery);