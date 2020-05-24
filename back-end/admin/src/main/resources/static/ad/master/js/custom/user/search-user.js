(function (window, document, $, undefined) {

  $(function () {

    $('[data-type="search-user"]').each(function () {

      var _this = $(this);
      var currentPage = 0;
      var inputSearchUser = _this.find('[data-type="input-search-user"]');
      var btnSearchUser = _this.find('[data-type="btn-search-user"]');
      var btnNext = $('.list-next');
      var listUsers = $('#list-jusos');
      var divListUsers = $('.list-users');

      btnNext.hide();

      var search = function () {

        var keyword = inputSearchUser.val();
        if (keyword === undefined || keyword.length === 0) {
          $.notify("검색어를 입력하세요.");
          return;
        }

        btnSearchUser.html("<i class='fa fa-spinner fa-spin'></i>");

        $.ajax({
          url: '/admin/api/user/search',
          method: 'GET',
          contentType: "application/json",
          data: {keyword: keyword, page: currentPage}
        }).done(function (result) {
          console.debug(result, 'result');
          btnSearchUser.html("검색");
          if (result) {
            currentPage = result.page.number;

            var totalCount = result.page.totalElements;
            if (totalCount === 0) {
              $.notify("검색된 회원이 없습니다.<br/> 검색어를 확인해보세요.", {status: "warning"});
              divListUsers.fadeOut();
              btnNext.hide();
              return;
            } else if (currentPage === 0) {
              $.notify("총 " + totalCount + "명의 회원이 검색되었습니다.", {status: "success"});
            }

            var countPerPage = result.page.size;
            var users = result._embedded.searchUserList;
            var isNext = false;

            if (countPerPage * (currentPage + 1) < totalCount) {
              isNext = true;
            }

            if (users && users.length > 0) {
              divListUsers.fadeIn();
              users.forEach(function (item) {

                var user = {
                  id: item.id,
                  email: item.email,
                  fullname: item.fullname
                };

                var template = $('#template-search-user').html();
                Mustache.parse(template);
                listUsers.append(Mustache.render(template, user));
              });

              if (isNext) {
                btnNext.show();
              } else {
                btnNext.hide();
              }

              $('.item-search-user').on('click', function () {

                var item = $(this);
                var id = item.data('id');
                var email = item.data('email');
                var fullname = item.data('fullname');

                $('[data-type="id"]').val(id);
                $('[data-type="userEmail"]').val(email);
                $('[data-type="userFullname"]').val(fullname);

                divListUsers.fadeOut('fast');
                inputSearchUser.val('');
              });

            } else {
              divListUsers.fadeOut();
            }
          }

        }).fail(function (jqXHR, textStatus) {
          btnSearchUser.html("검색");
          var message = "(" + jqXHR.status + ") ";
          if (jqXHR.responseJSON && jqXHR.responseJSON.message) {
            message = message + jqXHR.responseJSON.message;
          }
          $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요.<br>Message: " + message, {status: "danger"});
        });
      };

      btnNext.on('click', function () {
        currentPage++;
        search();
      });

      inputSearchUser.on('keydown', function (event) {
        if (event.keyCode === 13) {
          listUsers.children().off();
          listUsers.html('');
          currentPage = 0;
          event.preventDefault();
          search();
        }
      });

      btnSearchUser.on('click', function () {
        listUsers.children().off();
        listUsers.html('');
        currentPage = 0;
        search();
      });

    });

  });

})(window, document, window.jQuery);