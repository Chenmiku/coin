$(function () {
  $('[data-type="autocomplete-ajax"]').each(function () {

    var $this = $(this);
    var formGroup = $this.closest('.form-group');
    var url = $this.data('url');
    var paramName = $this.data('paramName');
    var fnName = $this.data('fnName');

    $this.on('keydown', function (e) {

      if (e.keyCode === 13) {
        e.preventDefault();
      }
    });

    console.debug(url, 'url');
    console.debug(paramName, 'paramName');

    $this.autocomplete({
      serviceUrl: url,
      paramName: paramName,
      dataType: "json",
      minChars: 1,
      onSearchStart: function (params) {
        console.debug(params, 'onSearchStart:::params');
      },
      onHint: function (container) {
        console.debug(container, 'onHint:::container');
      },
      onSearchComplete: function (query, suggestions) {
        console.debug(query, 'onSearchComplete:::query');
        console.debug(suggestions, 'onSearchComplete:::suggestions');
        formGroup.addClass('has-success');
        formGroup.removeClass('has-warning');
      },
      transformResult: function (response, originalQuery) {
        console.debug(response, 'transformResult:::response');
        console.debug(originalQuery, 'transformResult:::originalQuery');
        var suggestions = [];

        if (response) {
          suggestions = response.map(function (item) {
            return {value: item.value, data: item.data};
          });
        }
        console.debug(suggestions, 'suggestions');
        return {suggestions: suggestions};
      },
      onSelect: function (suggestion) {
        console.debug(suggestion, 'onSelect:::suggestion');
        var data = suggestion.data;
        window[fnName](data);
        // callbackAutocompleteAjax(data);
        // $('[name="relativeUser"]').val(data.id);
      },
      onSearchError: function (query, jqXHR, textStatus, errorThrown) {
        console.debug(query, 'onSearchError:::query');
        console.debug(jqXHR, 'onSearchError:::jqXHR');
        console.debug(textStatus, 'onSearchError:::textStatus');
        console.debug(errorThrown, 'onSearchError:::errorThrown');
        formGroup.removeClass('has-success');
        formGroup.addClass('has-warning');

      },
      onHide: function (container) {
        console.debug(container, 'onHide:::container');
      },
      onFocus: function (e) {
        console.debug("onFocus");
      }
    });
  });


});