$(function () {

  $('[data-type="formula-expression"]').each(function () {

    $(this).on('change', function () {
      var $this = $(this);
      $('[data-type="input-formula-expression"]').val($this.val());
    });
  });

  $('[data-type="formula-discount-expression"]').each(function () {

    $(this).on('change', function () {
      var $this = $(this);
      $('[data-type="input-formula-discount-expression"]').val($this.val());
    });
  });

  $('[data-type="formula-saving-expression"]').each(function () {

    $(this).on('change', function () {
      var $this = $(this);
      $('[data-type="input-formula-saving-expression"]').val($this.val());
    });
  });
});