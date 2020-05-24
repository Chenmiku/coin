(function(window, document, $, undefined){

    $(function(){

        $('[download]').each(function () {

            $(this).on('click',function (e) {
                e.preventDefault();
                download($(this).attr('href'));
            })
        })

    });

})(window, document, window.jQuery);