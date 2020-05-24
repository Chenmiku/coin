$(function () {

  $('[data-type="ticket-mobile-send"]').each(function () {
    var $this = $(this);
    var $id = $this.data('id');
    var $mobile = $this.find('[name="mobile"]');
    var $button = $this.find('button');


    $button.on('click', function (e) {
      if ($mobile.val() !== undefined && ($mobile.val().length === 11 || $mobile.val().length === 12)) {
        if (confirm("정말로 " + $mobile.val() + " 번호로 티켓번호를 발송하시겠습니까?"))
          $.ajax({
            url: "/admin/api/tickets",
            method: 'POST',
            contentType: "application/json",
            data: JSON.stringify({id: $id, mobile: $mobile.val()})
          }).done(function (result) {
            console.debug(result);
            $.notify("티켓이 등록되었습니다.", {status: "success"});

          }).fail(function (jqXHR, textStatus) {
            if (jqXHR.status.toString().startsWith("4")) {
              console.debug(jqXHR, "jqXHR");
              console.debug(textStatus, "textStatus");
              if (jqXHR.responseText && jqXHR.responseText.message) {
                $.notify(jqXHR.responseText, {status: "danger"});
              } else
                $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
            } else {
              $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
            }
          });
      } else {

        $.notify("휴대폰번호를 정확히 입력해주세요.", {status: "warning"});
      }

    });
  });
});