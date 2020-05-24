/**
 * 적립금 지급 with VueJS
 * 2019.04.04 deokin
 * */
$(function () {
  $('[data-type="product-point"]').each(function () {
    var $this = $(this);
    var $initValue = $this.data('value');

    var vm_point = new Vue({
      el: $this[0],
      data: {
        membershipPoint: Boolean($initValue)
      }
    })
  })
});