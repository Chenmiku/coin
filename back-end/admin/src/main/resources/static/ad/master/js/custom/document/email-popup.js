(function (window, document, $, undefined) {

  $(function () {

    $('[data-type="btn-email-popup-document"]').each(function () {

      $(this).on('click', function (e) {
        e.preventDefault();

        var $this = $(this);

        window.smsPopup = window.open("/admin/document/popup", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=50,left=50,width=1000,height=800");
      });

    });
  });

})(window, document, window.jQuery);