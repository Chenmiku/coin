(function(window, document, $, undefined){

    $(function () {

        if ($('[data-parsley-remote-validator="duplicate"]').length > 0) {

            if (window.Parsley) {
                window.Parsley.addAsyncValidator('duplicate',
                    function (xhr) {
                        //console.log(this.$element, 'this.$element'); // jQuery Object[ input[name="q"] ]
                        //console.log(xhr.status, 'xhr.status');
                        if (xhr.status === 200) {
                            if (xhr.responseJSON.result === 'duplicate') {
                                return false;
                            }
                            return true;
                        }
                        return false;
                    }
                );
            }
        }
    });

})(window, document, window.jQuery);