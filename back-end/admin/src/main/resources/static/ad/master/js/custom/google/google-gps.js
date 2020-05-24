// Custom jQuery
// -----------------------------------

(function (window, document, $, undefined) {

    $(function () {

        /**
         * SEARCH ADDRESS & GET LOCATION
         */
        $('[data-type="google-location"]').each(function () {

            var _this = $(this);
            var inputSearchAddress = _this.find('[data-type="input-google-location"]');
            var btnSearchAddress = _this.find('[data-type="btn-google-location"]');

            var latitude = $('[data-type="latitude"]');
            var longitude = $('[data-type="longitude"]');

            var marker = null;

            window.initMap = function () {

                var myLatlng = new google.maps.LatLng(22.3230454, 114.2076429);
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

            var getPlaceSearch = function (query) {

                if (marker != null) {
                    marker.setMap(null);
                }

                $.ajax({
                    url: '/api/v1/google/placesearch?query=' + query + '&language=en',
                    method: 'GET',
                    contentType: "application/json"
                }).done(function (data) {

                    if (data && data.status) {

                        if (data.status === 'OVER_QUERY_LIMIT') {
                            $.notify("Google API free quota exceeded.", {status: "warning"});
                        }
                    }

                    // console.debug(data, 'data');
                    if (data && data.results && data.results.length > 0) {

                        var result = data.results[0];
                        // console.debug(result, 'result');

                        if (result && result.place_id) {
                            // console.debug(result.place_id,' result.place_id');
                            placedetail(result.place_id);
                        }
                    }
                }).fail(function (jqXHR, textStatus) {

                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(jqXHR.responseJSON.message, {status: "danger"});
                    }
                });
            };

            var placedetail = function (placeId) {

                $.ajax({
                    url: '/api/v1/google/placedetail?placeId=' + placeId + '&language=en',
                    method: 'GET',
                    contentType: "application/json"
                }).done(function (data) {

                    if (data && data.result) {
                        // console.debug(data, 'data placedetail');

                        var result = data.result;

                        if (result && result.geometry && result.geometry.location) {

                            var latitude = result.geometry.location.lat;
                            var longitude = result.geometry.location.lng;

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

                        if (result && result.formatted_address) {
                            $('[data-type="address"]').val(result.formatted_address);
                        }

                        if (result && result.postalCode) {
                            $('[data-type="postalCode"]').val(result.postalCode);
                        }

                        if (result && result.formatted_phone_number) {
                            $('[data-type="phone"]').val(result.formatted_phone_number);
                        }

                        if (result && result.website) {
                            $('[data-type="website"]').val(result.website);
                        }

                    }

                }).fail(function (jqXHR, textStatus) {

                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(jqXHR.responseJSON.message, {status: "danger"});
                    }
                });

                $.ajax({
                    url: '/api/v1/google/placedetail?placeId=' + placeId + '&language=zh',
                    method: 'GET',
                    contentType: "application/json"
                }).done(function (data) {

                    if (data && data.result) {
                        // console.debug(data, 'data placedetail');

                        var result = data.result;

                        if (result && result.formatted_address) {
                            // console.debug(result.formatted_address,' result.formatted_address');
                            $('[name="addressCn"]').val(result.formatted_address);
                        }
                    }

                }).fail(function (jqXHR, textStatus) {

                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(jqXHR.responseJSON.message, {status: "danger"});
                    }
                });
            };


            var options = {
                serviceUrl: "/api/v1/google/autocomplete",
                paramName: "query",
                dataType: "json",
                onSearchStart: function (params) {
                    // console.debug(params, 'onSearchStart:::params');
                },
                onHint: function (container) {
                    // console.debug(container, 'onHint:::container');
                },
                onSearchComplete: function (query, suggestions) {
                    // console.debug(query, 'onSearchComplete:::query');
                    // console.debug(suggestions, 'onSearchComplete:::suggestions');
                },
                transformResult: function (response, originalQuery) {
                    // console.debug(response, 'transformResult:::response');
                    // console.debug(originalQuery, 'transformResult:::originalQuery');
                    var suggestions = [];

                    if (response && response.predictions) {
                        suggestions = response.predictions.map(function (item) {
                            return {value: item.description, data: item.place_id};
                        });
                    }
                    // console.debug(suggestions, 'suggestions');
                    return {suggestions: suggestions};
                },
                onSelect: function (suggestion) {
                    // console.debug(suggestion, 'onSelect:::suggestion');
                    var place_id = suggestion.data;
                    placedetail(place_id);
                },
                onSearchError: function (query, jqXHR, textStatus, errorThrown) {
                    // console.debug(query, 'onSearchError:::query');
                    // console.debug(jqXHR, 'onSearchError:::jqXHR');
                    // console.debug(textStatus, 'onSearchError:::textStatus');
                    // console.debug(errorThrown, 'onSearchError:::errorThrown');
                },
                onHide: function (container) {
                    // console.debug(container, 'onHide:::container');
                }
            };

            var searchAddress = function () {
                var $query = inputSearchAddress.val();
                if ($query) {
                    getPlaceSearch($query);
                }
            }

            inputSearchAddress.on('keydown', function (e) {

                if (e.keyCode === 13) {
                    e.preventDefault();
                    searchAddress();
                }
            });

            inputSearchAddress.autocomplete(options);

            btnSearchAddress.on('click', function (e) {

                e.preventDefault();
                searchAddress();
            })
        });

    });

})(window, document, window.jQuery);