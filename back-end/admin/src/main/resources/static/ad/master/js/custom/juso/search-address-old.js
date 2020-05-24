(function (window, document, $, undefined) {

    $(function () {

        /**
         * SEARCH ADDRESS
         */
        $('[data-type="search-address-old"]').each(function () {

            var _this = $(this);
            var currentPage = 0;
            var inputSearchAddress = _this.find('[data-type="input-search-address"]');
            var btnSearchAddress = _this.find('[data-type="btn-search-address"]');
            var btnNext = $('.list-next');
            var listJusos = $('#list-jusos');

            btnNext.hide();

            var search = function () {

                var keyword = inputSearchAddress.val();
                if (keyword === undefined || keyword.length === 0) {
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
                                var jibunAddr = item.data('jibunAddr');
                                var engAddr = item.data('engAddr');

                                $('[data-type="postalCode"]').val(postalCode);
                                $('[data-type="address1"]').val(roadAddr);
                                $('[data-type="jibunAddr"]').val(jibunAddr);
                                $('[data-type="engAddr"]').val(engAddr);

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