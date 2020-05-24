$(function () {

  $('[data-type="ticket-mobile-send"]').each(function () {
    var $this = $(this);
    var $id = $this.data('id');
    var $button = $this.find('button');
    $button.on('click', function (e) {
      alert($id);
    });
  });
});