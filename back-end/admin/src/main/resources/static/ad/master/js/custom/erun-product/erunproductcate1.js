$(function () {


  $('#selectC1s').each(function () {
    var $select = $(this);
    var $selectSub = $('#selectC1S1s');

    $select.on('change', function () {
      var subCategories = $('#selectC1s option:selected').data('ic1s1');

      $selectSub.html('');
      if (subCategories) {
        subCategories.forEach(function (value, index) {
          $selectSub.append('<option value="' + value.ky + '">' + value.name + '</option>');
        });
      }
    });
  })
});