/*!
 *
 * Angle - Bootstrap Admin App + jQuery
 *
 * Version: 3.7.5
 * Author: @themicon_co
 * Website: http://themicon.co
 * License: https://wrapbootstrap.com/help/licenses
 *
 */

(function (window, document, $, undefined) {

    if (typeof $ === 'undefined') {
        throw new Error('This application\'s JavaScript requires jQuery');
    }

    // Serialize
    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        console.debug(a);
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    // AJAX CSRF
    $(function () {
        window.csrf = {};
        window.csrf.token = $("meta[name='_csrf']").attr("content");
        window.csrf.header = $("meta[name='_csrf_header']").attr("content");

        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(window.csrf.header, window.csrf.token);
        });
    });

    $(function () {

        // Restore body classes
        // -----------------------------------
        var $body = $('body');
        new StateToggler().restoreState($body);

        // enable settings toggle after restore
        $('#chk-fixed').prop('checked', $body.hasClass('layout-fixed'));
        $('#chk-collapsed').prop('checked', $body.hasClass('aside-collapsed'));
        $('#chk-collapsed-text').prop('checked', $body.hasClass('aside-collapsed-text'));
        $('#chk-boxed').prop('checked', $body.hasClass('layout-boxed'));
        $('#chk-float').prop('checked', $body.hasClass('aside-float'));
        $('#chk-hover').prop('checked', $body.hasClass('aside-hover'));

        // When ready display the offsidebar
        $('.offsidebar.hide').removeClass('hide');

        // Disable warning "Synchronous XMLHttpRequest on the main thread is deprecated.."
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            options.async = true;
        });

    }); // doc ready

    (function () {
        if (String) {
            // Trim
            String.prototype.trimEnd = function () {
                return this.replace(/\s+$/, '');
            };
            String.prototype.trimStart = function () {
                return this.replace(/^\s+/, '');
            };

            String.prototype.replaceAll = function (org, dest) {
                return this.split(org).join(dest);
            };

            String.prototype.contains = String.prototype.contains || function (str) {
                    return this.indexOf(str) >= 0;
                };

            String.prototype.startsWith = String.prototype.startsWith || function (prefix) {
                    return this.indexOf(prefix) === 0;
                };

            String.prototype.endsWith = String.prototype.endsWith || function (suffix) {
                    return this.indexOf(suffix, this.length - suffix.length) >= 0;
                };
        }
    }());

})(window, document, window.jQuery);
