// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

  $(function () {
    $('#view-order').each(function () {

      $('select[name="orderStatus"]').each(function () {
        var $this = $(this);
        var idOrder = $this.data('id');

        $this.on('change', function (e) {
          var status = $(this).val();

          $.ajax({
            url: '/admin/api/order/order-status/' + idOrder + '/' + status,
            method: 'PATCH',
            contentType: "application/json"
          }).done(function (data) {
            if (data.result === 'success') {
              $.notify("데이터가 수정되었습니다.", {status: "success"});
              setTimeout(function () {
                window.location.reload();
              }, 1000);
            }
          }).fail(function (jqXHR, textStatus) {

            if (jqXHR.status.toString().startsWith("4")) {
              $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
            } else {
              $.notify(jqXHR.responseJSON.message, {status: "danger"});
            }
          });
        });
      });
    });
  });

})(window, document, window.jQuery);