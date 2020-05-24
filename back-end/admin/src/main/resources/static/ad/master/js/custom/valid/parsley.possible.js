(function (window, document, $, undefined) {

  $(function () {

    if ($('[data-parsley-remote-validator="possible"]').length > 0) {

      if (window.Parsley) {
        window.Parsley.addAsyncValidator('possible',
          function (xhr) {
            //console.log(this.$element, 'this.$element'); // jQuery Object[ input[name="q"] ]
            //console.log(xhr.status, 'xhr.status');
            if (xhr.status === 200) {
              return xhr.responseJSON.result === 'success';
            }
            return false;
          }
        );
      }
    }
  });

})(window, document, window.jQuery);