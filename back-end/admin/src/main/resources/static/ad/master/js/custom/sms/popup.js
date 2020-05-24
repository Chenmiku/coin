(function (window, document, $, undefined) {

  $(function () {

    $('[data-type="btn-sms"]').each(function () {

      $(this).on('click', function (e) {
        e.preventDefault();

        var $this = $(this);
        var title = $this.data('title') ? $this.data('title') : "";
        var message = $this.data('message') ? $this.data('message') : "";
        var recipients = $this.data('recipients') ? $this.data('recipients') : "";
        var username = $this.data('username') ? $this.data('username') : "";

        window.smsPopup = window.open("/admin/sms/popup?username=" + username + "&title=" + title + "&message=" + message + "&recipients=" + recipients, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=50,left=50,width=1000,height=800");
      });

    });
  });

})(window, document, window.jQuery);