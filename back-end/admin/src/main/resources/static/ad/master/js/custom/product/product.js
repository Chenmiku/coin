$(function () {

  $('.es-product-create-update').each(function () {


    /* TODO (Start)Deprecated : 마진계산 코드 products/price.js 로 대체. 테스트 후 삭제.*/
    // var $container = $(this);
    // var $form = $container.find('form');
    // var $originPrice = $container.find('[name="originPrice"]');
    // var $productionCost = $container.find('[name="productionCost"]');
    // var $margin = $container.find('[name="margin"]');
    //
    // $form.keypress(function (e) {
    //   if (e.which === 13 || event.keyCode === 13 || e.which === 9 || event.keyCode === 9) {
    //     return false;
    //   }
    // });
    //
    // var calculate = function () {
    //   if ($productionCost.val() && $originPrice.val()) {
    //     console.debug($originPrice.val(), '1');
    //     console.debug($productionCost.val(), '2');
    //
    //     var margin = parseFloat($originPrice.val()) - parseFloat($productionCost.val());
    //     $margin.val(margin);
    //   }
    // };
    //
    // $originPrice.on('change', function () {
    //   calculate();
    // });
    //
    // $productionCost.on('change', function () {
    //   calculate();
    // });
    /* TODO (End)Deprecated*/

  });
});