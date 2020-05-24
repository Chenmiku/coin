(function (window, document, $, undefined) {

    $(function () {

        $('#list-sample').each(function () {

            var OPTION_SIZE = 3;
            var index = 0;
            var list = $(this);
            var template = $('#template-item-product-option').html();
            var plusBtn = $('#anchor-add-option-item');

            // function
            var changeArticle = function () {
                var inputs = $('.input-product-option-value');
                if(inputs && inputs.length) {
                    inputs.each(function(){
                       var input = $(this);
                        console.log(input.val());
                    });
                }
            };

            var tagsInputListener = function (itemId) {
                var input = $(itemId).find('.input-product-option-value');
                input.on('itemAddedOnInit', function (event) {
                    // event.item: contains the item
                    console.log('itemAddedOnInit');
                });

                input.on('beforeItemAdd', function (event) {
                    // event.item: contains the item
                    // event.cancel: set to true to prevent the item getting added
                    console.log('beforeItemAdd');

                });

                input.on('itemAdded', function (event) {
                    // event.item: contains the item
                    console.log('itemAdded');
                    changeArticle();
                });

                input.on('beforeItemRemove', function (event) {
                    // event.item: contains the item
                    // event.cancel: set to true to prevent the item getting removed
                    console.log('beforeItemRemove');

                });

                input.on('itemRemoved', function (event) {
                    // event.item: contains the item
                    console.log('itemRemoved');
                    changeArticle();
                });
            };

            var appendItem = function () {

                var itemId = '#item-product-option-' + index;

                list.append(Mustache.render(template, {index: index}));

                $(itemId).find('.btn-delete').on('click', function (e) {
                    $(this).off('click');
                    $(itemId).find('.input-product-option-value').tagsinput('destroy');
                    $(itemId).remove();

                    if ($('.item-product-option').length < OPTION_SIZE) {
                        plusBtn.show();
                    }
                });

                $(itemId).find('.input-product-option-value').tagsinput();
                tagsInputListener(itemId);
                index++;

                if ($('.item-product-option').length >= OPTION_SIZE) {
                    plusBtn.hide();
                }
            };

            plusBtn.on('click', function () {

                if ($('.item-product-option').length < OPTION_SIZE) {
                    appendItem();
                }
            });

            // init append
            appendItem();

        });
    });

})(window, document, window.jQuery);