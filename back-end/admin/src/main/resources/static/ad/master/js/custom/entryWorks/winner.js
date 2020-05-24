$(function () {
  $('[data-type="btn-winner-reg"]').each(function () {
    var $btn = $(this);

    var $id = $btn.data('id');
    var $event = $btn.data('event');

    var $eventValue = null;

    if ($event === 'FIRST') {
      $eventValue = "1차 우수작"
    } else if ($event === 'SECOND') {
      $eventValue = "2차 우수작"
    } else if ($event === 'THIRD') {
      $eventValue = "3차 우수작"
    }

    $btn.on('click', function () {

      if ($event === 'FIRST') {

        if (confirm($eventValue + "에 선정하시겠습니까?")) {
          $.ajax({
            url: "/admin/api/winner",
            method: 'POST',
            contentType: "application/json",
            data: JSON.stringify({
              id: $id,
              event: $event
            })
          }).done(function (result) {
            console.debug(result);
            $.notify("1차 우수작에 등록되었습니다.", {status: "success"});

          }).fail(function (jqXHR, textStatus) {
            var message = "(" + jqXHR.status + ") ";
            if (jqXHR.responseJSON && jqXHR.responseJSON.message) {
              message = message + jqXHR.responseJSON.message;
            }
            $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요.<br>Message: " + message, {status: "danger"});
          });
        }

      } else {
        alert('아직 준비중입니다.');
      }
    })
  })
});