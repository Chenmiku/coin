(function (window, document, $, undefined) {

  $(function () {

    $('[data-type="update-order-sortable-product"]').each(function () {

      var $updateOrder = $(this);

      $updateOrder.find('[data-type="btn-order"]').on('click', function () {

        var $this = $(this);
        var wrapper = $this.closest('.wrapper-order');

        var actionUrl = wrapper.data('action');
        var idProduct = wrapper.data('idProduct');
        var idBrand = wrapper.data('idBrand');
        var mode = $this.data('mode');

        var csrfParamName = wrapper.data('csrfParamName');
        var csrfValue = wrapper.data('csrfValue');

        // Static 처리시 form 생성
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", actionUrl);
        document.body.appendChild(form);

        var inputIdProduct = document.createElement("input");
        inputIdProduct.setAttribute("type", "hidden");
        inputIdProduct.setAttribute("name", "idProduct");
        inputIdProduct.setAttribute("value", idProduct);
        form.appendChild(inputIdProduct);

        var inputIdBrand = document.createElement("input");
        inputIdBrand.setAttribute("type", "hidden");
        inputIdBrand.setAttribute("name", "idBrand");
        inputIdBrand.setAttribute("value", idBrand);
        form.appendChild(inputIdBrand);

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
