$(function () {
  $('[data-type="product-option-old"]').each(function () {

    var optionValue = {
      title: {
        internationalMode: {
          koKr: null,
          enUs: null,
          zhCn: null,
          zhTw: null,
          jaJp: null
        }
      },
      items: []
    };

    var option = {
      title: {
        internationalMode: {
          koKr: null,
          enUs: null,
          zhCn: null,
          zhTw: null,
          jaJp: null
        }
      }, // 옵션 제목
      code: null, // 상품 코드
      addPrice: 0, // 추가 가격
      price: 0, // 가격
      qty: 0, // 수량
      limit: 0 // 제한수량
    };

    var options = [];
    var optionValues = [];

    var $container = $(this);

    var koKr = $container.data('koKr');
    var enUs = $container.data('enUs');
    var zhCn = $container.data('zhCn');
    var zhTw = $container.data('zhTw');
    var jaJp = $container.data('jaJp');

    var $wrapOptionValueList = $('[data-type="wrap-option-value-list"]');
    var $wrapOptionList = $('[data-type="wrap-option-list"]');
    var $btnAdd = $container.find('#btn-add-option-ss');

    var $inputOptionTitleKoKr = $container.find('[name="option-title-kokr"]');
    var $inputOptionTitleEnUs = $container.find('[name="option-title-enus"]');
    var $inputOptionTitleZhCn = $container.find('[name="option-title-zhcn"]');
    var $inputOptionTitleZhTw = $container.find('[name="option-title-zhtw"]');
    var $inputOptionTitleJaJp = $container.find('[name="option-title-jajp"]');

    window.deleteOption = function (e, index) {
      e.preventDefault();
      alert(index);
      options.splice(index, 1);
      $(e.target).closest('.row').remove();
    };

    var onChangeOptionValue = function () {

      $wrapOptionValueList.html('');

      optionValues.forEach(function (optionValue, index) {

        $wrapOptionValueList.append('<div class="row" data-index="' + index + '">' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">옵션 제목</label>' +
            '   <input type="text" class="form-control" value="' + (koKr ? optionValue.title.internationalMode.koKr : optionValue.title.internationalMode.zhCn) + '"/>' +
            '   <input name="optionTitle" type="hidden" value="' + JSON.stringify(optionValue.title) + '"/>' +
            ' </div>' +
            '</div>' +

            '<div class="col-sm-12">' +

            '<div class="row">' +

            '          <div class="col-sm-2">' +
            '            <div class="form-group">' +
            '              <label class="control-label">옵션 값 (국문)</label>' +
            '              <input name="option-value-kokr" type="text" class="form-control" ' + (koKr ? '' : 'disabled') + '/>' +
            '            </div>' +
            '          </div>' +

            '          <div class="col-sm-2">' +
            '            <div class="form-group">' +
            '              <label class="control-label">옵션 값 (영문)</label>' +
            '              <input name="option-value-enus" type="text" class="form-control" ' + (enUs ? '' : 'disabled') + '/>' +
            '            </div>' +
            '          </div>' +

            '          <div class="col-sm-2">' +
            '            <div class="form-group">' +
            '              <label class="control-label">옵션 값 (중문(간체))</label>' +
            '              <input name="option-value-zhcn" type="text" class="form-control" ' + (zhCn ? '' : 'disabled') + '/>' +
            '            </div>' +
            '          </div>' +

            '          <div class="col-sm-2">' +
            '            <div class="form-group">' +
            '              <label class="control-label">옵션 값 (중문(번체)</label>' +
            '              <input name="option-value-zhtw" type="text" class="form-control" ' + (zhTw ? '' : 'disabled') + '/>' +
            '            </div>' +
            '          </div>' +

            '          <div class="col-sm-2">' +
            '            <div class="form-group">' +
            '              <label class="control-label">옵션 값 (일문)</label>' +
            '              <input name="option-value-jajp" type="text" class="form-control" ' + (jaJp ? '' : 'disabled') + '/>' +
            '            </div>' +
            '          </div>' +

            '        <div class="col-sm-2">' +
            '          <div class="form-group">' +
            '            <label class="control-label text-center"></label>' +
            '            <div class="clearfix">' +
            '              <button id="btn-remove-option-value" class="btn btn-default pull-left" >삭제</button>' +
            '            </div>' +
            '          </div>' +
            '        </div>' +
            '      </div>' +


            '</div>' +
            '</div>'
        );
        console.debug(option);

        $('[name="optionCode"]').tagsinput({confirmKeys: [13, 9]});
      })
    };
    var onChangeOption = function () {

      $wrapOptionList.html('');

      options.forEach(function (option, index) {

        $wrapOptionList.append('<div class="row" data-index="' + index + '">' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">옵션 제목</label>' +
            '   <input name="optionTitle" type="text" class="form-control" value="' + (koKr ? option.title.internationalMode.koKr : option.title.internationalMode.zhCn) + '"/>' +
            ' </div>' +
            '</div>' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">상품코드</label>' +
            '   <input name="optionCode" type="text" class="form-control"/>' +
            ' </div>' +
            '</div>' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">추가금액</label>' +
            '   <input name="optionAddPrice" type="number" class="form-control"/>' +
            ' </div>' +
            '</div>' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">수량</label>' +
            '   <input name="optionQty" type="number" class="form-control"/>' +
            ' </div>' +
            '</div>' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">제한수량</label>' +
            '   <input name="optionLimit" type="number" class="form-control"/>' +
            ' </div>' +
            '</div>' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">삭제</label><br/>' +
            '   <button class="btn btn-default" onclick="deleteOption(event , ' + index + ')">삭제</button>' +
            ' </div>' +
            '</div>' +

            '</div>');
        console.debug(option);
      })
    };


    var showOptionField = function () {
      var isSkuField = $container.find('[name="skuField"]:checked').val() == 'true';

      if (isSkuField) {
        $('[data-type="option-field-body"]').show();

      } else {
        $('[data-type="option-field-body"]').hide();
      }
    };

    $container.find('[name="skuField"]').on('change', function () {

      showOptionField();

    });


    $btnAdd.on('click', function (e) {
      e.preventDefault();

      var optionTitleKoKr = $inputOptionTitleKoKr.val() ? $inputOptionTitleKoKr.val() : null;
      var optionTitleEnUs = $inputOptionTitleEnUs.val() ? $inputOptionTitleEnUs.val() : null;
      var optionTitleZhCn = $inputOptionTitleZhCn.val() ? $inputOptionTitleZhCn.val() : null;
      var optionTitleZhTw = $inputOptionTitleZhTw.val() ? $inputOptionTitleZhTw.val() : null;
      var optionTitleJaJp = $inputOptionTitleJaJp.val() ? $inputOptionTitleJaJp.val() : null;

      if (koKr && (optionTitleKoKr == null || optionTitleKoKr.length === 0)) {
        $.notify("옵션제목(국문)을 입력하세요.", {status: "warning"});
        return;
      }
      if (enUs && (optionTitleEnUs == null || optionTitleEnUs.length === 0)) {
        $.notify("옵션제목(영문)을 입력하세요.", {status: "warning"});
        return;
      }
      if (zhCn && (optionTitleZhCn == null || optionTitleZhCn.length === 0)) {
        $.notify("옵션제목(중문(간체))을 입력하세요.", {status: "warning"});
        return;
      }
      if (zhTw && (optionTitleZhTw == null || optionTitleZhTw.length === 0)) {
        $.notify("옵션제목(중문(번체))을 입력하세요.", {status: "warning"});
        return;
      }
      if (jaJp && (optionTitleJaJp == null || optionTitleJaJp.length === 0)) {
        $.notify("옵션제목(일문)을 입력하세요.", {status: "warning"});
        return;
      }

      optionValues.push({
        title: {
          internationalMode: {
            koKr: optionTitleKoKr,
            enUs: optionTitleEnUs,
            zhCn: optionTitleZhCn,
            zhTw: optionTitleZhTw,
            jaJp: optionTitleJaJp
          }
        },
        items: []
      });

      onChangeOptionValue();
    });

    // init
    showOptionField();
  });
});

/*
options.push({
  title: {
    internationalMode: {
      koKr: optionTitleKoKr,
      enUs: optionTitleEnUs,
      zhCn: optionTitleZhCn,
      zhTw: optionTitleZhTw,
      jaJp: optionTitleJaJp
    }
  }, // 옵션 제목
  code: null, // 상품 코드
  addPrice: 0, // 추가 가격
  price: 0, // 가격
  qty: 0, // 수량
  limit: 0 // 제한수량
});
*/