// Custom jQuery
// -----------------------------------


(function(window, document, $, undefined){

    $(function(){
        //has uppercase
        if(window.Parsley) {
            window.Parsley.addValidator('uppercase', {
                requirementType: 'number',
                validateString: function (value, requirement) {
                    var uppercases = value.match(/[A-Z]/g) || [];
                    return uppercases.length >= requirement;
                },
                messages: {
                    en: 'Your password must contain at least %s uppercase letter.',
                    ko: '적어도 %s개 이상의 영문 대문자를 입력하세요.'
                }
            });

//has lowercase
            window.Parsley.addValidator('lowercase', {
                requirementType: 'number',
                validateString: function (value, requirement) {
                    var lowecases = value.match(/[a-z]/g) || [];
                    return lowecases.length >= requirement;
                },
                messages: {
                    en: 'Your password must contain at least %s lowercase letter.',
                    ko: '적어도 %s개 이상의 영문 소문자를 입력하세요.'
                }
            });

//has number
            window.Parsley.addValidator('number', {
                requirementType: 'number',
                validateString: function (value, requirement) {
                    var numbers = value.match(/[0-9]/g) || [];
                    return numbers.length >= requirement;
                },
                messages: {
                    en: 'Your password must contain at least %s number.',
                    ko: '적어도 %s개 이상의 숫자를 입력하세요.'
                }
            });

//has special char
            window.Parsley.addValidator('special', {
                requirementType: 'number',
                validateString: function (value, requirement) {
                    var specials = value.match(/[^a-zA-Z0-9]/g) || [];
                    return specials.length >= requirement;
                },
                messages: {
                    en: 'Your password must contain at least %s special characters.',
                    ko: '적어도 %s개 이상의 특수문자를 입력하세요.'
                }
            });
        }
    });

})(window, document, window.jQuery);
