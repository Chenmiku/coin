(function (window, document, $, undefined) {

  $(function () {

    $('#list-product').each(function () {

      // variables
      var $checkAll = $("input[name=checkAll]");
      var $checkProduct = $("input[name=checkProduct]");
      var $updateForm = $('#form-list-update');
      var $removeBadgeForm = $('#form-list-remove-badge');
      var $filterForm = $('#form-list-filter');

      // check all
      $checkAll.on('click', function () {
        var chk = $(this).is(":checked");

        if (chk) {
          $checkProduct.prop("checked", true);
        } else {
          $checkProduct.prop("checked", false);
        }
      });

      // remove badge submit
      var $isClickRemoveBadge = false;
      $removeBadgeForm.parsley().on('form:submit', function () {

        if ($("input[name=checkProduct]:checked").length === 0) {
          alert('변경할 상품을 선택하세요.');
          return false;
        }

        if (isEmpty($('[name="idBadgeByRemove"]').val())) {
          alert('제거할 벳지를 선택하세요.');
          return false;
        }

        if ($isClickRemoveBadge) {
          return false;
        }
        $.notify("수정 중입니다...", {timeout: 30000});
        $isClickRemoveBadge = true;

        var $removeBadgeFormParams = $removeBadgeForm.serializeObject();
        var $filterFormParams = $filterForm.serializeObject();
        var $token = $("meta[name='_csrf']").attr("content");
        var $tokenParam = $("meta[name='_csrf_param']").attr("content");

        var $form = document.createElement("form");
        $form.setAttribute("method", "post");
        $form.setAttribute("action", $removeBadgeForm.attr('action'));
        document.body.appendChild($form);

        var $inputs = [];
        $inputs.push({name: $tokenParam, value: $token});

        $("input[name=checkProduct]:checked").each(function () {
          var $thisValue = $(this).val();
          $inputs.push({name: 'idProducts', value: $thisValue});
          console.log($thisValue);
        });

        for (var key in $removeBadgeFormParams) {
          $inputs.push({name: key, value: $removeBadgeFormParams[key]});
        }

        for (var key in $filterFormParams) {
          $inputs.push({name: key, value: $filterFormParams[key]});
        }

        $inputs.forEach(function (item) {
          var $input = document.createElement("input");
          $input.setAttribute("type", "hidden");
          $input.setAttribute("name", item.name);
          $input.setAttribute("value", item.value);
          $form.appendChild($input);
          console.debug(item.name + " ::: " + item.value);
        });

        $form.submit();

        return false;
      });

      // update submit
      var $isClickUpdate = false;
      $updateForm.parsley().on('form:submit', function () {

        if ($("input[name=checkProduct]:checked").length === 0) {
          alert('변경할 상품을 선택하세요.');
          return false;
        }

        if (isEmpty($('[name="updateSaleStatus"]').val())
          && isEmpty($('[name="updateDisplayStatus"]').val())) {
          alert('상태값을 선택하세요.');
          return false;
        }

        if ($isClickUpdate) {
          return false;
        }
        $.notify("수정 중입니다...", {timeout: 30000});
        $isClickUpdate = true;

        var $updateFormParams = $updateForm.serializeObject();
        var $filterFormParams = $filterForm.serializeObject();
        var $token = $("meta[name='_csrf']").attr("content");
        var $tokenParam = $("meta[name='_csrf_param']").attr("content");

        var $form = document.createElement("form");
        $form.setAttribute("method", "post");
        $form.setAttribute("action", $updateForm.attr('action'));
        document.body.appendChild($form);

        var $inputs = [];
        $inputs.push({name: $tokenParam, value: $token});

        $("input[name=checkProduct]:checked").each(function () {
          var $thisValue = $(this).val();
          $inputs.push({name: 'idProducts', value: $thisValue});
          console.log($thisValue);
        });

        for (var key in $updateFormParams) {
          $inputs.push({name: key, value: $updateFormParams[key]});
        }

        for (var key in $filterFormParams) {
          $inputs.push({name: key, value: $filterFormParams[key]});
        }

        $inputs.forEach(function (item) {
          var $input = document.createElement("input");
          $input.setAttribute("type", "hidden");
          $input.setAttribute("name", item.name);
          $input.setAttribute("value", item.value);
          $form.appendChild($input);
          console.debug(item.name + " ::: " + item.value);
        });

        $form.submit();

        return false;
      });

      // select delete
      var $isClickDelete = false;
      $('#btn-select-delete').on('click', function (e) {
        e.preventDefault();
        var $this = $(this);

        if ($("input[name=checkProduct]:checked").length === 0) {
          alert('삭제할 상품을 선택하세요.');
          return false;
        }

        if ($isClickUpdate) {
          return false;
        }
        $isClickUpdate = true;

        swal({
          title: "정말 데이터를 삭제하시겠습니까?",
          text: "삭제 후, 다시 복구할 수 없습니다.",
          type: "warning",
          showCancelButton: true,
          confirmButtonColor: "#DD6B55",
          confirmButtonText: "네, 삭제합니다.",
          cancelButtonText: "아니오.",
          closeOnConfirm: false,
          closeOnCancel: false
        }, function (isConfirm) {

          if (isConfirm) {

            var form = document.createElement("form");
            form.setAttribute("method", "post");
            form.setAttribute("action", $this.data('action'));
            document.body.appendChild(form);

            var token = $("meta[name='_csrf']").attr("content");
            var parameterName = $("meta[name='_csrf_param']").attr("content");

            var inputCsrf = document.createElement("input");
            inputCsrf.setAttribute("type", "hidden");
            inputCsrf.setAttribute("name", parameterName);
            inputCsrf.setAttribute("value", token);
            form.appendChild(inputCsrf);

            var $inputs = [];
            $("input[name=checkProduct]:checked").each(function () {
              var $thisValue = $(this).val();
              $inputs.push({name: 'idProducts', value: $thisValue});
            });

            $inputs.forEach(function (item) {
              var $input = document.createElement("input");
              $input.setAttribute("type", "hidden");
              $input.setAttribute("name", item.name);
              $input.setAttribute("value", item.value);
              form.appendChild($input);
              // console.debug(item.name + " ::: " + item.value);
            });

            form.submit();

          } else {
            swal("취소되었습니다.", "이 데이터는 그대로 유지됩니다.", "error");
          }
        });

      });

    });

  });

})(window, document, window.jQuery);