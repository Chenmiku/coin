$(function () {

  $('[id="user-list"]').each(function () {
    var selectItem = function () {

      console.debug("selectItem");
      $("input[name=selectCheckbox]:checked").each(function () {
        var $this = $(this);

      });
    };

    $('[data-type="checkUserByBU"]').click(function () {
      var chk = $(this).is(":checked");

      if (chk) {
        $("input[name=selectCheckbox]").prop("checked", true);
      } else {
        $("input[name=selectCheckbox]").prop("checked", false);
      }

      selectItem();
    });

    $("input[name=selectCheckbox]").on('change', function () {
      selectItem();
    });
  });
});