(function (window, document, $, undefined) {

  $(function () {

    $('.grouping-create-update').each(function () {

      // variables
      var $radioTargetMode = $('[name="target"]');
      var $divProduct = $('[data-role="grouping-product"]');
      var $divCategory = $('[data-role="grouping-category"]');
      var $divBrand = $('[data-role="grouping-brand"]');

      var collapse = function(val) {
        if (val === 'CATEGORY') {
          $divProduct.hide();
          $divBrand.hide();
          $divCategory.show();
        } else if (val === 'BRAND') {
          $divProduct.hide();
          $divCategory.hide();
          $divBrand.show();
        } else {
          $divCategory.hide();
          $divBrand.hide();
          $divProduct.show();
        }
        console.debug(val, 'value');
      };

      $radioTargetMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="target"]:checked').val());

    });

  });

})(window, document, window.jQuery);