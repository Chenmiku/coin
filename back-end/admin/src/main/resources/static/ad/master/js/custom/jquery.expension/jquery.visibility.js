
// Custom jQuery
// -----------------------------------


(function(window, document, $, undefined){

    $(function(){
        $.fn.visible = function() {
            return this.css('visibility', 'visible');
        };

        $.fn.invisible = function() {
            return this.css('visibility', 'hidden');
        };

        $.fn.visibilityToggle = function() {
            return this.css('visibility', function(i, visibility) {
                return (visibility === 'visible') ? 'hidden' : 'visible';
            });
        };
    });

})(window, document, window.jQuery);
