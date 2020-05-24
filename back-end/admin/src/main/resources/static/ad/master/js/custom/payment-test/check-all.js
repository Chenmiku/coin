$(function () {

  $('#form-create-payment-test').each(function () {

    var $inputAvailablePoint = $('#available-point');
    var $inputMinPoint = $('#min-point');
    var $inputUnitPoint = $('#unit-point');
    var $inputAmount = $('[name="amount"]');
    var $inputDiscountCoupon = $('#discount-coupon');

    var amountCalc = function () {
      var amount = $inputAmount.val();
      $inputAmount.val(amount);
    };

    var selectItem = function () {

      var subtotal = 0;

      $('#product-tbody').html('');
      $('#products-input').html('');
      $('#id-carts-input').html('');

      $("input[name=selectCheckbox]:checked").each(function () {
        var $this = $(this);

        // === 제품
        var $pId = $this.data('productId');
        var $pName = $this.data('productName');
        var $pPrice = $this.data('productPrice');
        var $pQty = $this.data('productQty');
        var $pDc = $this.data('productDc');
        var $pSubTotal = $this.data('productSubTotal');

        console.debug("id ::: " + $pId);
        console.debug("name ::: " + $pName);
        console.debug("price ::: " + $pPrice);
        console.debug("qty ::: " + $pQty);
        console.debug("dc ::: " + $pDc);
        console.debug("subtotal ::: " + $pSubTotal);

        $('#product-tbody').append('<tr>' +
          '<td class="text-center">' + $pId + '</td>' +
          '<td class="text-center">' + $pName + '</td>' +
          '<td class="text-center">' + numberWithCommas($pPrice - $pDc) + '원</td>' +
          '<td class="text-center">' + numberWithCommas($pQty) + '개</td>' +
          '<td class="text-center">' + numberWithCommas($pSubTotal) + '원</td>' +
          '</tr>');

        var productJson =
          '{' +
          '\"id\":' + $pId + ',' +
          '\"price\":' + $pPrice + ',' + // 원가
          '\"qty\":' + $pQty + ',' + // 수량
          '\"dc\":' + $pDc + ',' + // 개당 할인
          '\"amount\":' + $pSubTotal // 결제금액(소계)
          + '}';

        $('#products-input').append("<input type='hidden' name='products' value='" + productJson + "'/>");
        $('#id-carts-input').append('<input type="hidden" name="idCarts" value="' + $this.val() + '"/>');

        // === 계산식
        subtotal += Number($pSubTotal);
        console.debug("+ " + $pSubTotal);
      });


      console.debug("--------------------");
      console.debug("= " + subtotal);
      $inputAmount.val(subtotal);
      //

      amountCalc();
    };

    $('[data-type="checkPaymentTest"]').each(function () {

      $(this).click(function () {
        var chk = $(this).is(":checked");

        if (chk) {
          $("input[name=selectCheckbox]").prop("checked", true);
        } else {
          $("input[name=selectCheckbox]").prop("checked", false);
        }

        selectItem();
      })
    });

    $("input[name=selectCheckbox]").on('change', function () {
      selectItem();
    });

    $('#form-create-payment-test').parsley().on('form:submit', function () {

      var form = $('#form-create-payment-test');
      var data = form.serializeObject();

      console.debug(data, "data");

      var orderBody = {
        buyer: {
          fullname: data['buyer.fullname'],
          phone: data['buyer.phone'],
          email: data['buyer.email']
        },
        payMethod: data.payMethod,
        point: data.point ? Number(data.point) : 0,
        idCoupons: data.idCoupons ? data.idCoupons.split(',').map(function (idCoupon) {
          return Number(idCoupon);
        }) : [],
        subtotal: data.subtotal ? Number(data.subtotal) : 0,
        amount: data.amount ? Number(data.amount) : 0,
        idCarts: data.idCarts ? (isArray(data.idCarts) ? data.idCarts.map(function (idCart) {
          return Number(idCart);
        }) : [Number(data.idCarts)]) : [],
        products: data.products ? (isArray(data.products) ? data.products.map(function (product) {
          return product ? JSON.parse(product) : {};
        }) : [JSON.parse(data.products)]) : []
      };

      console.debug(orderBody, "orderBody");

      $.ajax({
          url: form.attr('action'),
          method: 'POST',
          contentType: "application/json",
          data: JSON.stringify(orderBody)
      }).done(function (result) {
          console.debug(result);
          $.notify("주문을 성공하였습니다.", {status:"success"});

      }).fail(function (jqXHR, textStatus) {
        var message = "(" + jqXHR.status + ") ";
        if (jqXHR.responseJSON && jqXHR.responseJSON.message) {
          message = message + jqXHR.responseJSON.message;
        }
        $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요.<br>Message: " + message, {status: "danger"});
      });

      return false;
    });

  });
});