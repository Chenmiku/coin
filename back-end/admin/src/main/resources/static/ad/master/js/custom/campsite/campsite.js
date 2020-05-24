$(function () {


  $('.campsite-create-update').each(function () {

    var bindDeleteBtn = function () {
      $('[data-type="btn-delete-camp-type"]').off('click');
      $('[data-type="btn-delete-camp-type"]').on('click', function (e) {
        e.preventDefault();

        if (confirm(($(this).closest('tr').data('index') + 1) + '번째 열을 정말 삭제하시겠습니까?')) {

          var $campTypeId = $(this).closest('tr').data("id");
          var $tr = $(this).closest('tr');
          if ($campTypeId) {
            $.ajax({
              url: "/admin/api/camp-type/" + $campTypeId,
              method: 'DELETE',
              contentType: "application/json"
            }).done(function (result) {
              console.debug(result);
              $tr.remove();
            }).fail(function (jqXHR, textStatus) {
              if (jqXHR.status.toString().startsWith("4")) {
                $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
              } else {
                $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
              }
            });
          } else {
            $tr.remove();
          }
        }
      });
    };

    bindDeleteBtn();

    $('#btn-add-season-type').on('click', function () {

      var $template = $('#template-camp-type').html();
      var $tbody = $('#tbody-camp-type');

      var $lastChildIndex = $tbody.find('[ data-type="tr-camp-type"]:last-child').data('index');
      $lastChildIndex = isEmpty($lastChildIndex) ? 0 : $lastChildIndex + 1;

      var item = {
        index: $lastChildIndex
      };

      if ($lastChildIndex > 10) {
        alert("최대 10개까지 등록가능합니다.");
        return;
      }
      $tbody.append(Mustache.render($template, item));
      bindDeleteBtn();
    });
  });
});