$(function () {
  $('[data-type="product-option"]').each(function () {
    $('[data-role="tagsinput"]').tagsinput({confirmKeys: [13, 9]});

    var $listOption = $('[data-type="list-option"]');
    var $listOptionValue = $('[data-type="list-option-value"]');
    var $template = $('#template-option-list').html();
    var $template2 = $('#template-option-value-list').html();


    $listOption.each(function () {
      var _list = $(this);
      var lang = _list.data('lang');

      if (lang === 'koKr') {

      } else if(lang === 'enUs') {
      } else if(lang === 'zhCn') {
      } else if(lang === 'zhTw') {
      } else if(lang === 'jaJp') {

      }
    });

    $('#btn-add-option').on('click', function (e) {
      e.preventDefault();


      $listOption.each(function () {
        var _list = $(this);
        var lang = _list.data('lang');

        _list.append(Mustache.render($template, {name:'option-title-' + lang}));
        // if (lang === 'ko-kr') {
        //
        // } else if(lang === 'en-us') {
        //   _list.append(Mustache.render($template, {name:''}));
        // } else if(lang === 'zh-cn') {
        //   _list.append(Mustache.render($template, {name:''}));
        // } else if(lang === 'zh-tw') {
        //   _list.append(Mustache.render($template, {name:''}));
        // } else if(lang === 'ja-jp') {
        //   _list.append(Mustache.render($template, {name:''}));
        // }
      });

      $('[data-role="tagsinput"]').tagsinput({confirmKeys: [13, 9]});

    });

    $('#submit-list-option').on('click', function (e) {
      e.preventDefault();

      $listOptionValue.each(function () {
        var _list = $(this);
        var lang = _list.data('lang');
        _list.append(Mustache.render($template2, {name:'option-value-title-' + lang}));
      });

    });
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