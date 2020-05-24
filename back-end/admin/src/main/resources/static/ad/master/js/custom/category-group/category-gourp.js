$(function () {

  $('[data-type="group-category"]').each(function () {

    var $this = $(this);

    var groups = [];
    var category1 = [];
    var category2 = [];
    var category3 = [];
    var category4 = [];
    var category5 = [];

    var values = $this.data('values');
    console.debug(values, 'values');

    $.ajax({
      url: "/admin/api/category-product/group" + (values ? '?idCategory=' + values : ''),
      method: 'GET',
      contentType: "application/json"
    }).done(function (result) {
      console.debug(result);

      groups = result;

      // CATEGORY 1
      if (groups) {
        groups.forEach(function (item, key, map) {
          category1.push(item);
          var selected = item.checked ? 'selected' : '';
          $selectCategory1.append('<option value="' + item.id + '" ' + selected + '>(' + item.id + ') ' + item.content + '</option>');
        });
        onChangeCategory1();
      }

    }).fail(function (jqXHR, textStatus) {
      if (jqXHR.status.toString().startsWith("4")) {
        $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
      } else {
        $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
      }
    });

    var $selectCategory1 = $this.find('[data-type="select-category-1"]');
    var $selectCategory2 = $this.find('[data-type="select-category-2"]');
    var $selectCategory3 = $this.find('[data-type="select-category-3"]');
    var $selectCategory4 = $this.find('[data-type="select-category-4"]');
    var $selectCategory5 = $this.find('[data-type="select-category-5"]');

    var onChangeCategory1 = function () {

      $selectCategory2.html('<option value="">없음</option>');
      $selectCategory3.html('<option value="">없음</option>');
      $selectCategory4.html('<option value="">없음</option>');
      $selectCategory5.html('<option value="">없음</option>');
      category2 = [];
      category3 = [];
      category4 = [];
      category5 = [];

      var _id = $selectCategory1.find(':selected').val();

      // CATEGORY 2
      groups.forEach(function (item1, key, map) {

        if (item1.id == _id) {
          if (item1.children) {
            $selectCategory2.html('<option value="">카테고리2</option>');
            item1.children.forEach(function (item2, key, map) {
              category2.push(item2);
              var selected = item2.checked ? 'selected' : '';
              $selectCategory2.append('<option value="' + item2.id + '" ' + selected + '>(' + item2.id + ') ' + item2.content + '</option>');
            });
            onChangeCategory2();
          }
        }
      });
    };
    var onChangeCategory2 = function () {

      $selectCategory3.html('<option value="">없음</option>');
      $selectCategory4.html('<option value="">없음</option>');
      $selectCategory5.html('<option value="">없음</option>');
      category3 = [];
      category4 = [];
      category5 = [];

      var _id = $selectCategory2.find(':selected').val();

      // CATEGORY 2
      category2.forEach(function (item2, key, map) {

        if (item2.id == _id) {
          if (item2.children) {
            $selectCategory3.html('<option value="">카테고리3</option>');
            item2.children.forEach(function (item3, key, map) {
              category3.push(item3);
              var selected = item3.checked ? 'selected' : '';
              $selectCategory3.append('<option value="' + item3.id + '" ' + selected + '>(' + item3.id + ') ' + item3.content + '</option>');
            });
            onChangeCategory3();
          }
        }
      });
    };
    var onChangeCategory3 = function () {

      $selectCategory4.html('<option value="">없음</option>');
      $selectCategory5.html('<option value="">없음</option>');
      category4 = [];
      category5 = [];

      var _id = $selectCategory3.find(':selected').val();

      // CATEGORY 3
      category3.forEach(function (item3, key, map) {

        if (item3.id == _id) {
          if (item3.children) {
            $selectCategory4.html('<option value="">카테고리4</option>');
            item3.children.forEach(function (item4, key, map) {
              category4.push(item4);
              var selected = item4.checked ? 'selected' : '';
              $selectCategory4.append('<option value="' + item4.id + '" ' + selected + '>(' + item4.id + ') ' + item4.content + '</option>');
            });
            onChangeCategory4();
          }
        }
      });
    };
    var onChangeCategory4 = function () {

      $selectCategory5.html('<option value="">없음</option>');
      category5 = [];

      var _id = $selectCategory4.find(':selected').val();

      // CATEGORY 3
      category4.forEach(function (item4, key, map) {

        if (item4.id == _id) {
          if (item4.children) {
            $selectCategory5.html('<option value="">카테고리5</option>');
            item4.children.forEach(function (item5, key, map) {
              category5.push(item5);
              var selected = item5.checked ? 'selected' : '';
              $selectCategory5.append('<option value="' + item5.id + '" ' + selected + '>(' + item5.id + ') ' + item5.content + '</option>');
            });
            onChangeCategory5();
          }
        }
      });
    };
    var onChangeCategory5 = function () {

      var _id = $selectCategory5.find(':selected').val();

      console.debug(_id, '_id');
    };

    $selectCategory1.on('change', onChangeCategory1);
    $selectCategory2.on('change', onChangeCategory2);
    $selectCategory3.on('change', onChangeCategory3);
    $selectCategory4.on('change', onChangeCategory4);
    $selectCategory5.on('change', onChangeCategory5);
  });
});