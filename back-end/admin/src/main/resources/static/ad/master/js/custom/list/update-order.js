(function (window, document, $, undefined) {

  $(function () {

    $('[data-type="update-order"]').each(function () {

      var $updateOrder = $(this);

      $updateOrder.find('[data-type="btn-order"]').on('click', function () {

        var $this = $(this);
        var wrapper = $this.closest('.wrapper-order');

        var actionUrl = wrapper.data('action');
        var id = wrapper.data('id');
        var mode = $this.data('mode');

        var csrfParamName = wrapper.data('csrfParamName');
        var csrfValue = wrapper.data('csrfValue');

        // Static 처리시 form 생성
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", actionUrl);
        document.body.appendChild(form);

        var inputIdCategory = document.createElement("input");
        inputIdCategory.setAttribute("type", "hidden");
        inputIdCategory.setAttribute("name", "id");
        inputIdCategory.setAttribute("value", id);
        form.appendChild(inputIdCategory);

        var inputMode = document.createElement("input");
        inputMode.setAttribute("type", "hidden");
        inputMode.setAttribute("name", "mode");
        inputMode.setAttribute("value", mode);
        form.appendChild(inputMode);

        var inputCsrf = document.createElement("input");
        inputCsrf.setAttribute("type", "hidden");
        inputCsrf.setAttribute("name", csrfParamName);
        inputCsrf.setAttribute("value", csrfValue);
        form.appendChild(inputCsrf);

        form.submit();
      });
    });

  });

})(window, document, window.jQuery);
