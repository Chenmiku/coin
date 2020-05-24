/**
 * 상품 가격 관리
 * 2019.04.04 deokin
 * */
$(function () {
  $('[data-type="product-price"]').each(function () {
    var $this = $(this);
    var $radioMembershipPrice = $this.find('[name="membershipPrice"]'); // 회원가 적용 라디오
    var $membershipPriceActive = $('[data-membership-price-active]'); // 마진 등급 테이블

    var $inputOriginPrice = $this.find('#originPrice'); // 제품 원가 인풋(디폴트)
    var $inputProductionPrice = $this.find('[name="productionCost"]'); // 판매 소비자가 인풋(디폴트)
    var $inputMarginProductionPrice = $this.find('#margin-production-cost'); // 마진 인풋(디폴트)

    var $inputMembershipPrices = $this.find('[data-origin-price]'); // 등급별 판매 소비자가 인풋들
    var $inputDiscountRates = $this.find('[data-discount-rate]'); // 등급별 할인율 인풋들

    // 회원가 활성 상태 변경 시, 마진 테이블 숨김처리.
    var collapse = function () {
      if ($radioMembershipPrice.filter(':checked').val() === 'true') {
        $membershipPriceActive.show()
      } else {
        $membershipPriceActive.hide()
      }
    };
    $radioMembershipPrice.on('change', function () {
      collapse();
    });
    collapse();

    // 할인율 반영된 금액 계산 함수.
    var handleCalculatePrice = function (originPrice, discountRate) {
      var result = 0;
      if (!isEmpty(originPrice) && !isEmpty(discountRate)) {
        result = parseFloat(originPrice) * ((100 - parseFloat(discountRate)) / 100);
      }
      return result.toFixed(2)
    };

    // 할인된 금액의 할인율 계산 함수.
    var handleCalculateRate = function (originPrice, price) {
      var result = 0;
      if (!isEmpty(originPrice) && !isEmpty(price)) {
        var rate = 1 - (parseFloat(price) / parseFloat(originPrice))
        result = rate * 100;
      }
      return result.toFixed(1)
    };

    // 마진 계산 함수.
    var handleCalculateMargin = function (originPrice, price) {
      var result = 0;
      if (!isEmpty(originPrice) && !isEmpty(price)) {
        result = parseFloat(price) - parseFloat(originPrice);
      }
      return result.toFixed(2)
    };

    /*
    * 인풋 값 세팅 함수.
    * */
    // 등급별 판매가 계산/적용.(기본 판매 소비자가 기준으로 계산됨.)
    var handleSetMembershipPrice = function (defaultPrice) {
      $inputMembershipPrices.each(function (index) {
        var $this_price = $(this);
        var calcPrice = handleCalculatePrice(defaultPrice, $('#discountRate-' + index).val());
        $this_price.val(calcPrice).change();
      })
    };

    // 제품 원가 변경 시.
    $inputOriginPrice.on('change', function () {
      var defaultPrice = $inputOriginPrice.val() * 2; // 제품원가의 2배 기본 설정.

      // 판매소비자가(디폴트)
      $inputProductionPrice.val(defaultPrice);

      // 마진(디폴트) 계산
      $inputMarginProductionPrice.val(handleCalculateMargin($inputOriginPrice.val(), defaultPrice));

      // 등급별 판매가 계산/적용.(기본 판매 소비자가 기준으로 계산됨.)
      handleSetMembershipPrice(defaultPrice);
    });

    // 판매소비자(디폴트)가 변경 시.
    $inputProductionPrice.on('change', function () {
      var defaultPrice = $inputProductionPrice.val();

      // 마진(디폴트) 계산
      $inputMarginProductionPrice.val(handleCalculateMargin($inputOriginPrice.val(), defaultPrice)); // 마진(디폴트) 마진

      // 등급별 판매가 계산/적용.(기본 판매 소비자가 기준으로 계산됨.)
      handleSetMembershipPrice(defaultPrice);
    });

    // 등급 별 판매 소비자가 변경 시.
    $inputMembershipPrices.each(function (index) {
      var $this_price = $(this);
      $this_price.on('change', function () {

        var calcRate = handleCalculateRate($inputProductionPrice.val(), $this_price.val());
        var calcPrice = handleCalculateMargin($inputOriginPrice.val(), $this_price.val());

        // 각 판매 소비자가의 마진 계산/적용(할인율 적용).
        $('#margin-' + index).val(calcPrice);
        $('#discountRate-' + index).val(calcRate);
      });
    });

    // 등급 별 할인율 변경 시.
    $inputDiscountRates.each(function (index) {
      var $this_rate = $(this);
      $this_rate.on('change', function () {

        var calcPrice = parseFloat($inputProductionPrice.val()) * ((100 - parseFloat($this_rate.val())) / 100);

        // 각 판매 소비자가의 마진 계산/적용(할인율 적용).
        $('#price-' + index).val(calcPrice.toFixed(2)).change();
      });
    });

    // 초기화
    if(!isEmpty($inputProductionPrice.val())){
      $inputProductionPrice.change()
    }

  });
});