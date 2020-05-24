(function (window, document, $, undefined) {

  $(function () {

    $('.coupon-create-update').each(function () {

      // variables
      var $radioMode = $('[name="target"]');
      var $divProduct = $('[data-role="coupon-target-product"]');
      var $divCategory = $('[data-role="coupon-target-category"]');
      var $divBrand = $('[data-role="coupon-target-brand"]');
      var $divPlan = $('[data-role="coupon-target-plan"]');

      var collapse = function(val) {
        if (val === 'CATEGORY') {
          $divProduct.hide();
          $divBrand.hide();
          $divPlan.hide();
          $divCategory.show();
        } else if (val === 'BRAND') {
          $divProduct.hide();
          $divCategory.hide();
          $divPlan.hide();
          $divBrand.show();
        } else if (val === 'PLAN') {
          $divProduct.hide();
          $divCategory.hide();
          $divBrand.hide();
          $divPlan.show();
        } else {
          $divCategory.hide();
          $divBrand.hide();
          $divPlan.hide();
          $divProduct.show();
        }
      };

      $radioMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="target"]:checked').val());

    });

  });

})(window, document, window.jQuery);