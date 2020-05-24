// Custom jQuery
// -----------------------------------


(function(window, document, $, undefined){

    $(function(){

        $('#create-sample').each(function () {

            var BASIC_INFO_SIZE = 15;
            var index = 0;
            var list = $('#list-basic-info');
            var template = $('#template-item-basic-info').html();
            var plusBtn = $('#btn-plus-basic-info');

            // function
            var appendItem = function () {

                var itemId = '#item-basic-info-' + index;
                list.append(Mustache.render(template, {index: index}));

                $(itemId).find('.btn-delete').on('click', function (e) {
                    $(this).off('click');
                    $(itemId).remove();

                    if ($('.item-basic-info').length < BASIC_INFO_SIZE) {
                        plusBtn.show();
                    }
                });

                index++;

                if ($('.item-basic-info').length >= BASIC_INFO_SIZE) {
                    plusBtn.hide();
                }
            };

            // bind
            plusBtn.on('click', function () {

                if ($('.item-basic-info').length < BASIC_INFO_SIZE) {
                    appendItem();
                }
            });

            // init append
            appendItem();
        });
    });

})(window, document, window.jQuery);