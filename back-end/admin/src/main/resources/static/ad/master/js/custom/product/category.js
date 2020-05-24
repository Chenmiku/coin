(function (window, document, $, undefined) {

  $(function () {

    $('.es-product-create-update').each(function () {

      // variables
      var $selectCategory1 = $('[data-type="select-category-1"]');
      var $selectCategory2 = $('[data-type="select-category-2"]');
      var $selectCategory3 = $('[data-type="select-category-3"]');
      var $selectCategory4 = $('[data-type="select-category-4"]');
      var $selectCategory5 = $('[data-type="select-category-5"]');
      var $inputKeywordKoKr = $('input[name="keyword.textKoKr"]');
      var $inputKeywordEnUs = $('input[name="keyword.textEnUs"]');
      var $inputKeywordJaJp = $('input[name="keyword.textJaJp"]');
      var $inputKeywordZhCn = $('input[name="keyword.textZhCn"]');
      var $inputKeywordZhTw = $('input[name="keyword.textZhTw"]');

      var handleOnInitTagsInput = function () {
        $('[data-role="tagsinput"]').tagsinput('destroy');
        setTimeout(function () {
          $('[data-role="tagsinput"]').tagsinput();
        });
      };

      var setTags = function (idCategory) {
        $.ajax({
          url: '/admin/api/category-product/' + idCategory,
          method: 'GET',
          contentType: "application/json"
        }).done(function (result) {
          console.debug(result, 'result');
          if (result.tags) {
            console.debug($inputKeywordKoKr.val(), '$inputKeywordKoKr.val()');
            if (result.tags.textKoKr) {
              $inputKeywordKoKr.val(isEmpty($inputKeywordKoKr.val()) ? result.tags.textKoKr : $inputKeywordKoKr.val() + "," + result.tags.textKoKr);
            }
            if (result.tags.textEnUs) {
              $inputKeywordEnUs.val(isEmpty($inputKeywordEnUs.val()) ? result.tags.textEnUs : $inputKeywordEnUs.val() + "," + result.tags.textEnUs);
            }
            if (result.tags.textJaJp) {
              $inputKeywordJaJp.val(isEmpty($inputKeywordJaJp.val()) ? result.tags.textJaJp : $inputKeywordJaJp.val() + "," + result.tags.textJaJp);
            }
            if (result.tags.textZhCn) {
              $inputKeywordZhCn.val(isEmpty($inputKeywordZhCn.val()) ? result.tags.textZhCn : $inputKeywordZhCn.val() + "," + result.tags.textZhCn);
            }
            if (result.tags.textZhTw) {
              $inputKeywordZhTw.val(isEmpty($inputKeywordZhTw.val()) ? result.tags.textZhTw : $inputKeywordZhTw.val() + "," + result.tags.textZhTw);
            }

            handleOnInitTagsInput();
          }
        }).fail(function (jqXHR, textStatus) {
          if (jqXHR.status.toString().startsWith("4")) {
            $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
          } else {
            $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
          }
        });
      };

      $selectCategory1.on('change', function () {
        setTags($(this).val());
      });
      $selectCategory2.on('change', function () {
        setTags($(this).val());
      });
      $selectCategory3.on('change', function () {
        setTags($(this).val());
      });
      $selectCategory4.on('change', function () {
        setTags($(this).val());
      });
      $selectCategory5.on('change', function () {
        setTags($(this).val());
      });

    });

  });

})(window, document, window.jQuery);