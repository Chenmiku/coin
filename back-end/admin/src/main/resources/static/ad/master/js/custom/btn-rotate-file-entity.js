$(function () {

  $('[data-type="btn-rotate-file-entity"]').each(function () {
    var $this = $(this);
    var $id = $this.data('id');
    var $idEntryWork = $this.data('idEntry');

    $this.on('click', function (e) {
      $this.prop('disabled', true);

      e.preventDefault();

      if ($id) {
        $.notify("이미지를 회전중입니다. 약 30초만 기다려주세요.", {status: "success"});

        $.ajax({
          url: "/admin/api/file/" + $id + "/rotate/" + $idEntryWork,
          method: 'POST',
          contentType: "application/json"
        }).done(function (result) {
          console.debug(result);
          window.location.reload();

        }).fail(function (jqXHR, textStatus) {
          var message = "(" + jqXHR.status + ") ";
          if (jqXHR.responseJSON && jqXHR.responseJSON.message) {
            message = message + jqXHR.responseJSON.message;
          }
          $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요.<br>Message: " + message, {status: "danger"});
        });
      }

    });
  });

  $('[data-type="btn-rotate-file-winner"]').each(function () {
    var $this = $(this);
    var $id = $this.data('id');
    var $img = $this.data('img');

    $this.on('click', function (e) {
      $this.prop('disabled', true);

      e.preventDefault();

      if ($id) {
        $.notify("이미지를 회전중입니다. 약 30초만 기다려주세요.", {status: "success"});

        $.ajax({
          url: "/admin/api/file/winner/" + $id + "/rotate/img/" + $img,
          method: 'POST',
          contentType: "application/json"
        }).done(function (result) {
          console.debug(result);
          window.location.reload();

        }).fail(function (jqXHR, textStatus) {
          var message = "(" + jqXHR.status + ") ";
          if (jqXHR.responseJSON && jqXHR.responseJSON.message) {
            message = message + jqXHR.responseJSON.message;
          }
          $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요.<br>Message: " + message, {status: "danger"});
        });
      }

    });
  });
});