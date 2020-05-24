// Custom jQuery
// -----------------------------------

(function (window, document, $, undefined) {

    $(function () {

        $('#create-sample').each(function () {
// TODO http://d2.naver.com/helloworld/76650 -- 한글 변경작업
            var informationOption = {
                language: 'ko',
                zIndex: 2501,
                height: 600,
                heightMax: 1200,
                placeholderText: '상세설명을 입력하세요.',
                htmlExecuteScripts: false,

                paragraphFormat: {
                    N: '본문',
                    H1: '제목 1',
                    H2: '제목 2',
                    H3: '제목 3',
                    H4: '제목 4',
                    H5: '제목 5',
                    H6: '제목 6',
                    SMALL: '작게',
                    PRE: '코드'
                },

                paragraphStyles: {
                    lead: 'Lead'
                },

                fontFamily: {
                    "'Yoon Gothic 700',sans-serif": '윤고딕700',
                    "'Nanum Gothic',sans-serif": '나눔고딕',
                    "'Bon Gothic',sans-serif": '본고딕',
                    "'Spoqa Han Sans',sans-serif": '스포카 한 산스',
                    "Arial,Helvetica,sans-serif": 'Arial',
                    "Georgia,serif": 'Georgia',
                    "Impact,Charcoal,sans-serif": 'Impact',
                    "Tahoma,Geneva,sans-serif": 'Tahoma',
                    "'Times New Roman',Times,serif": 'Times New Roman',
                    "Verdana,Geneva,sans-serif": 'Verdana'
                },

                inlineStyles: {
                    'Big Red': 'font-size: 20px; color: red;',
                    'Small Blue': 'font-size: 14px; color: blue;'
                },

                imageInsertButtons: ['imageBack', '|', 'imageUpload', 'imageByURL', 'imageManager'],
                imageUploadURL: '/admin/api/wysiwyg',
                imageAllowedTypes: ['gif', 'jpeg', 'jpg', 'png', 'svg+xml', 'blob'],
                imageDefaultWidth: 450,
                imageMaxSize: 1024 * 1024 * 2,
                imageStyles: {
                    'img-responsive': '반응형 이미지',
                    'img-rounded': '둥근',
                    'img-thumbnail': '테두리'
                },

                imageManagerLoadURL: '/admin/api/wysiwyg',
                imageManagerLoadMethod: 'GET',

                imageManagerDeleteURL: '/admin/api/wysiwyg',
                imageManagerDeleteMethod: 'DELETE',

                videoInsertButtons: ['videoBack', '|', 'videoByURL', 'videoEmbed'],
                requestHeaders: {}
            };
            informationOption.requestHeaders[window.csrf.header] = window.csrf.token;

            //https://www.froala.com/wysiwyg-editor/docs/server/java/image-upload
            $('#froala-infomation').on('froalaEditor.contentChanged froalaEditor.initialized', function (e, editor) {
                //console.debug(editor.html.get());
                window.checkUnload = false;
//            $('pre#eg-previewer').text(editor.codeBeautifier.run(editor.html.get()))
//            $('pre#eg-previewer').removeClass('prettyprinted');
//        prettyPrint()
            }).froalaEditor(informationOption);
        });
    });

})(window, document, window.jQuery);