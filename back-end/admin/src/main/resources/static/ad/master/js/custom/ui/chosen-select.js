// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

  $(function () {

    // CHOSEN INIT
    $('[data-type="chosen-select"]').each(function () {
      var $this = $(this);
      $this.chosen();
    });

  });

})(window, document, window.jQuery);