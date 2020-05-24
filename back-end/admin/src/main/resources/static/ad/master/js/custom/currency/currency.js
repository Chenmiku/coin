$(function () {
  $('[data-type="select-currency"]').each(function () {
    $(this).on('change', function (e) {
        var $val = $(this).find(':selected').data('currencyText');
        $('[data-type="currency-text"]').val($val);
    });
  });
});