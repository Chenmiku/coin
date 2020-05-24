(function (window, document, $, undefined) {

    $(function () {

        /**
         * SEARCH ADDRESS
         */
        $('[data-type="search-address"]').each(function () {

            var _this = $(this);
            var currentPage = 0;
            var inputSearchAddress = _this.find('[data-type="input-search-address"]');
            var btnSearchAddress = _this.find('[data-type="btn-search-address"]');
            var btnNext = $('.list-next');
            var listJusos = $('#list-jusos');

            var $address1 = $('[data-type="address1"]');
            var $address2 = $('[data-type="address2"]');


            var searchAddress = function () {

                btnNext.hide();

                var search = function () {

                    var keyword = inputSearchAddress.val();
                    if (keyword === undefined || keyword.length === 0) {
                        $.notify("검색어를 입력하세요.");
                        return;
                    }

                    btnSearchAddress.html("<i class='fa fa-spinner fa-spin'></i>");

                    $.ajax({
                        url: '/admin/api/juso',
                        method: 'GET',
                        contentType: "application/json",
                        data: {keyword: keyword, page: currentPage}
                    }).done(function (result) {
                        btnSearchAddress.html("검색");
                        if (result && result.common.errorCode == '0') {
                            currentPage = result.common.currentPage;

                            var totalCount = result.common.totalCount;
                            if (totalCount == 0) {
                                $.notify("검색된 주소가 없습니다.<br/> 검색어를 확인해보세요.", {status: "warning"});
                                $('.list-jusos').fadeOut();
                                btnNext.hide();
                                return;
                            } else if (currentPage == 1) {
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
                                // console.debug(jusos);
                                jusos.forEach(function (item) {
                                    // console.debug(item);
                                    var juso = {
                                        postalCode: item.zipNo,
                                        roadAddr: (item.roadAddrPart1 + item.roadAddrPart2),
                                        jibunAddr: item.jibunAddr,
                                        engAddr: item.engAddr,
                                        siNm: item.siNm,
                                        sggNm: item.sggNm,
                                        emdNm: item.emdNm,
                                        liNm: item.liNm
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
                                    var jibunAddr = item.data('jibunAddr');
                                    var engAddr = item.data('engAddr');
                                    var siNm = item.data('siNm');
                                    var sggNm = item.data('sggNm');
                                    var emdNm = item.data('emdNm');
                                    var liNm = item.data('liNm');

                                    $('[data-type="postalCode"]').val(postalCode);
                                    $('[data-type="address1"]').val(roadAddr);
                                    $('[data-type="jibunAddr"]').val(jibunAddr);
                                    $('[data-type="engAddr"]').val(engAddr);
                                    $('[data-type="siNm"]').val(siNm);
                                    $('[data-type="sggNm"]').val(sggNm);
                                    $('[data-type="emdNm"]').val(emdNm);
                                    $('[data-type="liNm"]').val(liNm);

                                    searchAddressToCoordinate(roadAddr);

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


            };

            // Naver Map

            var map = new naver.maps.Map("map", {
                center: new naver.maps.LatLng(37.5486838, 126.9197463),
                zoom: 10,
                mapTypeControl: true
            });

            var infoWindow = new naver.maps.InfoWindow({
                anchorSkew: true
            });

            map.setCursor('pointer');

// result by latlng coordinate
            function searchAddressToCoordinate(address) {

                naver.maps.Service.geocode({
                    address: address
                }, function (status, response) {
                    if (status === naver.maps.Service.Status.ERROR) {
                        return alert('Something Wrong!');
                    }

                    var item = response.result.items[0],
                        addrType = item.isRoadAddress ? '[도로명 주소]' : '[지번 주소]',
                        point = new naver.maps.Point(item.point.x, item.point.y);

                    $('[name="address.gps.latitude"]').val(point.y);
                    $('[name="address.gps.longitude"]').val(point.x);
                    infoWindow.setContent([
                        '<div style="padding:10px;min-width:200px;line-height:150%;">',
                        '<h4 style="margin-top:5px;">검색 주소 : ' + response.result.userquery + '</h4><br />',
                        addrType + ' ' + item.address + '<br />',
                        '위도 : ' + point.y + ', 경도 :' + point.x,
                        '</div>'
                    ].join('\n'));


                    map.setCenter(point);
                    infoWindow.open(map, point);
                });
            }

            naver.maps.onJSContentLoaded = searchAddress;

            if ($address1.val()) {
                searchAddressToCoordinate($address1.val() + " " + $address2.val());
            }

        });
    });

})(window, document, window.jQuery);