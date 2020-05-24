$(function () {
  
  $('.winner-list').each(function () {

    // POD
    $('[name="allCheckedCategories"]').on('change', function () {
      var $this = $(this);
      var list = $('[name="checkedCategories"]');
      list.prop('checked', $this.is(":checked"));
    });

    $('[name="checkedCategories"]').each(function () {
      $(this).on('change', function () {
        if ($(this).is(":checked") === false) {
          $('[name="allCheckedCategories"]').prop('checked', false);
        }
      })
    });
  });
});