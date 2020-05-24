$(function () {

  $('[data-type="radio-sku"]').each(function () {

    var $this = $(this);

    var changeRadio = function () {
      var value = $('[name="skuField"]:checked').val();

      if (value == 'true') {
        $('[data-type="sku-fields"]').show();
      } else {
        $('[data-type="sku-fields"]').hide();
      }
    };

    $this.find('input').on('change', function () {
      changeRadio();
    })
  });
});