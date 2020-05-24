(function (window, document, $, undefined) {

  $(function () {

    /**
     * SEARCH ADDRESS & GET LOCATION
     */
    $('[data-type="search-address-map"]').each(function () {

      var _this = $(this);
      var currentPage = 0;
      var inputSearchAddress = _this.find('[data-type="input-search-address"]');
      var btnSearchAddress = _this.find('[data-type="btn-search-address"]');
      var btnNext = $('.list-next');
      var listJusos = $('#list-jusos');

      var latitude = $('[data-type="latitude"]');
      var longitude = $('[data-type="longitude"]');

      btnNext.hide();

      var marker = null;

      window.initMap = function () {

        var myLatlng = new google.maps.LatLng(37.496711, 127.023578);
        var mapOptions = {
          zoom: 16,
          center: myLatlng
        };

        map = new google.maps.Map(document.getElementById("map"), mapOptions);

        if (!isEmpty(latitude.val()) && !isEmpty(longitude.val())) {

          marker = new google.maps.Marker({
            position: {lat: Number(latitude.val()), lng: Number(longitude.val())},
            map: map
          });

          map.setCenter(marker.getPosition());
        }
      };

      var getLocation = function (address) {
        if (marker != null) {
          marker.setMap(null);
        }

        $.ajax({
          url: '/api/v1/location',
          method: 'GET',
          contentType: "application/json",
          data: {address: address}
        }).done(function (result) {

          btnSearchAddress.html("검색");
          if (result) {

            var latitude = result.latitude;
            var longitude = result.longitude;

            $('[data-type="latitude"]').val(latitude);
            $('[data-type="longitude"]').val(longitude);


            var myLatlng = {lat: Number(latitude), lng: Number(longitude)};
            marker = new google.maps.Marker({
              position: myLatlng,
              map: map,
              title: 'Click to zoom'
            });

            map.setCenter(marker.getPosition());
          }

        }).fail(function (jqXHR, textStatus) {
          btnSearchAddress.html("검색");
          if (jqXHR.status.toString().startsWith("4")) {
            $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
          } else {
            $.notify(jqXHR.responseJSON.message, {status: "danger"});
          }
        });
      };

      var search = function () {

        var keyword = inputSearchAddress.val();
        if (keyword === undefined || keyword.length == 0) {
          $.notify("검색어를 입력하세요.");
          return;
        }

        btnSearchAddress.html("<i class='fa fa-spinner fa-spin'></i>");

        $.ajax({
          url: '/api/v1/juso',
          method: 'GET',
          contentType: "application/json",
          data: {keyword: keyword, page: currentPage}
        }).done(function (result) {
          btnSearchAddress.html("검색");
          if (result && result.common.errorCode == '0') {
            currentPage = result.common.currentPage;

            var totalCount = result.common.totalCount;
            if (totalCount === 0) {
              $.notify("검색된 주소가 없습니다.<br/> 검색어를 확인해보세요.", {status: "warning"});
              $('.list-jusos').fadeOut();
              btnNext.hide();
              return;
            } else if (currentPage === 1) {
              $.notify("총 " + totalCount + "개의 주소가 검색되었습니다.", {status: "success"});
            }

            var countPerPage = result.common.countPerPage;
            var jusos = result.jusos;
            var isNext = false;

            if (countPerPage * currentPage < totalCount) {
              isNext = true;
            }

            if (jusos && jusos.length > 0) {
              var listJusos = $('#list-jusos');
              $('.list-jusos').fadeIn();
              jusos.forEach(function (item) {

                var juso = {
                  postalCode: item.zipNo,
                  roadAddr: (item.roadAddrPart1 + item.roadAddrPart2),
                  jibunAddr: item.jibunAddr,
                  engAddr: item.engAddr
                };

                var template = $('#template-juso').html();
                Mustache.parse(template);
                listJusos.append(Mustache.render(template, juso));
              });

              if (isNext) {
                btnNext.show();
              } else {
                btnNext.hide();
              }

              $('.item-juso').on('click', function () {

                var item = $(this);
                var postalCode = item.data('postalCode');
                var roadAddr = item.data('roadAddr');
                var engAddr = item.data('engAddr');

                $('[data-type="postalCode"]').val(postalCode);
                $('[data-type="address1"]').val(roadAddr);
                $('[data-type="engAddr"]').val(engAddr);


                getLocation(roadAddr);

                $('.list-jusos').fadeOut('fast');
                inputSearchAddress.val('');
              });

            } else {
              $('.list-jusos').fadeOut();
            }
          }

        }).fail(function (jqXHR, textStatus) {
          btnSearchAddress.html("검색");
          if (jqXHR.status.toString().startsWith("4")) {
            $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
          } else {
            $.notify(jqXHR.responseJSON.message, {status: "danger"});
          }
        });
      };

      btnNext.on('click', function () {
        currentPage++;
        search();
      });

      inputSearchAddress.on('keydown', function (event) {

        if (event.keyCode === 13) {
          listJusos.children().off();
          listJusos.html('');
          currentPage = 0;
          event.preventDefault();
          search();
        }

      });

      btnSearchAddress.on('click', function () {

        listJusos.children().off();
        listJusos.html('');
        currentPage = 0;
        search();
      });

    });

  });

})(window, document, window.jQuery);