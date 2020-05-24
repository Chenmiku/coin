$(function () {

  $('[data-type="calculate-formula"]').each(function () {

    var $this = $(this);
    var $formula = $this.data('formula');
    var $id = $this.data('id');

    $this.on('click', function (e) {

      e.preventDefault();

      var $price = $this.closest('.input-group').find('[data-type="calculate-formula-price"]').val();

      if($price == 0 || !$price) {
        $.notify('가격을 정확히 입력하세요.', {status: "warning"});
        return;
      }

      var data = {
        price: $price
      };

      $.ajax({
        url: '/admin/api/formula/calculate/' + $id,
        method: 'POST',
        contentType: "application/json",
        data: JSON.stringify(data)
      }).done(function (data) {
        $.notify(data + '원', {status: "success"});
      });
    });
  });
});