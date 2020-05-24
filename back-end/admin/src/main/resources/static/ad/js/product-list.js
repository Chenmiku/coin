$(function () {

    $('.btn-product-order').on('click', function () {

        var _this = $(this);
        var wrapper = _this.closest('.wrapper-product-order');

        var actionUrl = wrapper.data('action');
        var id = wrapper.data('id');
        var ascending = wrapper.data('ascending');
        var mode = _this.data('mode');

        var csrfParamName = wrapper.data('csrfParamName');
        var csrfValue = wrapper.data('csrfValue');

        //console.log(mode, 'mode');
        //console.log(wrapper, 'wrapper');
        //console.log(ascending, 'ascending');

        // Static 처리시 form 생성
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", actionUrl);
        document.body.appendChild(form);

        var inputIdProduct = document.createElement("input");
        inputIdProduct.setAttribute("type", "hidden");
        inputIdProduct.setAttribute("name", "idProduct");
        inputIdProduct.setAttribute("value", id);
        form.appendChild(inputIdProduct);

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

        //var inputUrl = document.createElement("input");
        //inputUrl.setAttribute("type", "hidden");
        //inputUrl.setAttribute("name", "url");
        //inputUrl.setAttribute("value", client.browser.firefox ? document.URL : window.location.href);
        //form.appendChild(inputUrl);

        form.submit();
    });
});

/**
 * THUMBNAIL GALLERY
 */
$(function(){

    $('.product-magnific-popup').each(function () {

        var _this = $(this);
        var items = _this.data('images');

        _this.magnificPopup({
            items: items,
            gallery: {
                enabled: true,
                arrowMarkup: '<button title="%title%" type="button" class="mfp-arrow mfp-arrow-%dir%"></button>', // markup of an arrow button

                tPrev: 'Previous (Left arrow key)',
                tNext: 'Next (Right arrow key)',
                tCounter: '<span class="mfp-counter">%curr% of %total%</span>' // markup of counter
            },
            type: 'image'
        })
    });
});
