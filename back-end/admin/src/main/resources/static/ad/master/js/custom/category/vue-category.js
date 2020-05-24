/**
 * 카테고리 관리
 * 2019.04.05 Deokin
 * */
$(function () {
  $('[data-product-category]').each(function () {
    var $this = $(this);

    // TODO JSON Data 들어와야하는 곳 (NestableItem 객체 사용)
    // var jsonData = $("meta[name='_json']").attr("content");

    var vm_category = new Vue({
      el: $this[0],
      data: {
        language: {
          koKr: $this.data('koKr'),
          enUs: $this.data('enUs'),
          zhCn: $this.data('zhCn'),
          zhTw: $this.data('zhTw'),
          jaJp: $this.data('jaJp')
        },
        idCategory: null, // 아이디 유무로 삭제/수정/추가 구분함.
        formData: {
          name: {textKoKr: null, textEnUs: null, textZhCn: null, textZhTw: null, textJaJp: null},
          tags: {textKoKr: null, textEnUs: null, textZhCn: null, textZhTw: null, textJaJp: null},
          internationalMode: {koKr: true, enUs: true, zhCn: true, zhTw: true, jaJp: true},
          active: true
        },
        jsonData: $("meta[name='_json']").attr("content")
      },
      methods: {
        // 비동기 요청 함수.
        handleRequest: function (url, method, data, callback) {
          $.ajax({
            url: '/admin/api/category-product/' + url,
            method: method,
            contentType: "application/json",
            data: data
          }).done(function (result) {
            callback(result);
          }).fail(function (jqXHR, textStatus) {
            if (jqXHR.status.toString().startsWith("4")) {
              $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
            } else {
              $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
            }
          });
        },
        // 태그인풋 초기화.
        handleOnInitTagsInput: function () {
          var $form = $('#form-create-category');
          $form.find('[data-role="tagsinput"]').tagsinput('destroy');
          setTimeout(function () {
            $form.find('[data-role="tagsinput"]').tagsinput();
          })
        },
        // 폼 데이터 초기화.
        handleOnResetFormData: function () {

          // 데이터 초기화.
          vm_category.formData = {
            name: {textKoKr: null, textEnUs: null, textZhCn: null, textZhTw: null, textJaJp: null},
            tags: {textKoKr: null, textEnUs: null, textZhCn: null, textZhTw: null, textJaJp: null},
            internationalMode: {koKr: true, enUs: true, zhCn: true, zhTw: true, jaJp: true},
            active: true
          };
          // 태그인풋 초기화.
          vm_category.handleOnInitTagsInput();
        },
        // 카테고리 로드.
        handleOnRequestCategory: function (id) {
          vm_category.handleRequest(id, 'GET', null, function (result) {
            if (!isEmpty(result)) {
              vm_category.idCategory = result.id;
              vm_category.formData = {
                name: result.name,
                tags: result.tags ? result.tags : {textKoKr: null, textEnUs: null, textZhCn: null, textZhTw: null, textJaJp: null},
                internationalMode: result.internationalMode
              };

              // 태그 인풋 초기화.
              vm_category.handleOnInitTagsInput();
            }
          });
        },
        // 카테고리 추가 버튼.
        handleOnAddCategory: function (e) {
          e.preventDefault();
          $.notify("카테고리 정보를 입력하세요.", {status: "info"});
          vm_category.idCategory = null;
          vm_category.handleOnResetFormData();
        },
        // 카테고리 수정.
        handleOnUpdateCategory: function () {
          var $form = $('#form-create-category');
          var serializeData = $form.serializeObject();
          vm_category.formData.id = vm_category.idCategory;
          vm_category.formData.tags = {
            textKoKr: serializeData["tags.textKoKr"] ? serializeData["tags.textKoKr"] : null,
            textEnUs: serializeData["tags.textEnUs"] ? serializeData["tags.textEnUs"] : null,
            textZhCn: serializeData["tags.textZhCn"] ? serializeData["tags.textZhCn"] : null,
            textZhTw: serializeData["tags.textZhTw"] ? serializeData["tags.textZhTw"] : null,
            textJaJp: serializeData["tags.textJaJp"] ? serializeData["tags.textJaJp"] : null
          };

          vm_category.handleRequest('update', 'POST', JSON.stringify(vm_category.formData), function (result) {
            // $.notify("카테고리가 저장되었습니다.", {status: "success"});
            // 폼 데이터 초기화.
            // vm_category.handleOnResetFormData();
            // 리로드
            location.reload();
          });
        },
        // 카테고리 등록.
        handleOnCreateCategory: function () {
          var $form = $('#form-create-category');
          var serializeData = $form.serializeObject();
          vm_category.formData.tags = {
            textKoKr: serializeData["tags.textKoKr"] ? serializeData["tags.textKoKr"] : null,
            textEnUs: serializeData["tags.textEnUs"] ? serializeData["tags.textEnUs"] : null,
            textZhCn: serializeData["tags.textZhCn"] ? serializeData["tags.textZhCn"] : null,
            textZhTw: serializeData["tags.textZhTw"] ? serializeData["tags.textZhTw"] : null,
            textJaJp: serializeData["tags.textJaJp"] ? serializeData["tags.textJaJp"] : null
          };
          /*$form.attr('action')*/
          vm_category.handleRequest('create', 'POST', JSON.stringify(vm_category.formData), function (result) {
            $.notify("카테고리가 추가되었습니다.", {status: "success"});
            // 리스트에 추가.
            $('#nestable').nestable('add', {"id": result.id, "content": result.name.value});
            // 폼 데이터 초기화.
            vm_category.handleOnResetFormData();
          });
        },
        // 카테고리 삭제.
        handleOnDeleteCategory: function (e) {
          e.preventDefault();
          if (isEmpty(vm_category.idCategory)) {
            $.notify("선택 된 카테고리가 없습니다.", {status: "warning"});
          } else {
            vm_category.handleRequest("delete/" + vm_category.idCategory, 'POST', null, function (result) {
              $.notify("카테고리가 삭제되었습니다.", {status: "success"});
              // 리스트에서 제거.
              $('#nestable').nestable('remove', vm_category.idCategory);
              // 폼 데이터 초기화
              vm_category.handleOnResetFormData();
              vm_category.idCategory = null;
            });
          }
        },
        // 그룹 정렬 업데이트
        handleOnUpdateGroup: function (e) {
          e.preventDefault();
          var resultData = JSON.stringify({json: vm_category.jsonData})
          vm_category.handleRequest('group/update', 'POST', resultData, function (result) {
            $.notify("카테고리정렬이 수정되었습니다.", {status: "success"});
          })
        },
        // 그룹 정렬 버튼 기능
        handleGroupFunction: function (fncName) {
          var $nestable = $('#nestable');
          switch (fncName) {
            case'expand-all':
              $nestable.nestable('expandAll');
              break;
            case'collapse-all':
              $nestable.nestable('collapseAll');
              break;
          }
        }
      },
      mounted: function () {

        setTimeout(function () {
          // init nestable
          if (window.JSON) {
            $('#nestable').nestable({
              group: 1,
              json: vm_category.jsonData,
              callback: function (l, e) {
                vm_category.handleOnRequestCategory($(e).data('id'))
              },
              contentCallback: function (item) {
                return item.content;
              }
            }).on('change', function (e) {
              var list = e.length ? e : $(e.target);
              if (window.JSON) {
                vm_category.jsonData = window.JSON.stringify(list.nestable('serialize'))
              } else {
                alert('기능을 지원하지 않는 브라우저 입니다.');
              }
            });
          } else {
            alert('기능을 지원하지 않는 브라우저 입니다.');
          }
        })

      }
    });

    // Parsley Submit
    $('#form-create-category').parsley().on('form:submit', function () {
      vm_category.handleOnCreateCategory();
      return false;
    });


    /**
     * 기존 소스
     * */

    // var updateAjax = function (list, json) {
    //   jsonData = json;
    //   list.data('output').val(jsonData);
    // };
    //
    // var updateOutput = function (e) {
    //   var list = e.length ? e : $(e.target);
    //   if (window.JSON) {
    //     updateAjax(list, window.JSON.stringify(list.nestable('serialize')));
    //   } else {
    //     alert('기능을 지원하지 않는 브라우저 입니다.');
    //   }
    // };

    // activate Nestable for list 1
    // $('#nestable').nestable({
    //   group: 1,
    //   json: jsonData,
    //   contentCallback: function (item) {
    //     return item.content;
    //   }
    // }).on('change', updateOutput);

    // output initial serialised data
    // updateOutput($('#nestable').data('output', $('#nestable-output')));

    // $('.js-nestable-action').on('click', function (e) {
    //   var target = $(e.target), action = target.data('action');
    //   if (action === 'expand-all') {
    //     $('.dd').nestable('expandAll');
    //   }
    //   if (action === 'collapse-all') {
    //     $('.dd').nestable('collapseAll');
    //   }
    //   if (action === 'add-item') {
    //     $('#nestable').append('<li class="dd-item">' +
    //       '<div class="dd-handle">Item 1</div>' +
    //       '</li>');
    //   }
    // });

    // 카테고리 등록
    // $('#form-create-category').parsley().on('form:submit', function () {
    //
    //   var form = $('#form-create-category');
    //   var formObj = form.serializeObject();
    //
    //   // console.debug(formObj, 'formObj');
    //   var data = {
    //     name: {
    //       textKoKr: formObj["name.textKoKr"] ? formObj["name.textKoKr"] : null,
    //       textEnUs: formObj["name.textEnUs"] ? formObj["name.textEnUs"] : null,
    //       textZhCn: formObj["name.textZhCn"] ? formObj["name.textZhCn"] : null,
    //       textZhTw: formObj["name.textZhTw"] ? formObj["name.textZhTw"] : null,
    //       textJaJp: formObj["name.textJaJp"] ? formObj["name.textJaJp"] : null
    //     },
    //     tags: {
    //       textKoKr: formObj["tags.textKoKr"] ? formObj["tags.textKoKr"] : null,
    //       textEnUs: formObj["tags.textEnUs"] ? formObj["tags.textEnUs"] : null,
    //       textZhCn: formObj["tags.textZhCn"] ? formObj["tags.textZhCn"] : null,
    //       textZhTw: formObj["tags.textZhTw"] ? formObj["tags.textZhTw"] : null,
    //       textJaJp: formObj["tags.textJaJp"] ? formObj["tags.textJaJp"] : null
    //     },
    //     internationalMode: {
    //       koKr: formObj["internationalMode.koKr"] ? formObj["internationalMode.koKr"] == 'true' : false,
    //       enUs: formObj["internationalMode.enUs"] ? formObj["internationalMode.enUs"] == 'true' : false,
    //       zhCn: formObj["internationalMode.zhCn"] ? formObj["internationalMode.zhCn"] == 'true' : false,
    //       zhTw: formObj["internationalMode.zhTw"] ? formObj["internationalMode.zhTw"] == 'true' : false,
    //       jaJp: formObj["internationalMode.jaJp"] ? formObj["internationalMode.jaJp"] == 'true' : false
    //     },
    //     active: formObj['active'] ? formObj['active'] == 'true' : false
    //   };
    //
    //   // console.debug(data, 'data');
    //
    //   $.ajax({
    //     url: form.attr('action'),
    //     method: 'POST',
    //     contentType: "application/json",
    //     data: JSON.stringify(data)
    //   }).done(function (result) {
    //     $.notify("카테고리가 추가되었습니다.", {status: "success"});
    //
    //     $('#form-create-category')[0].reset();
    //     form.find('[data-role="tagsinput"]').tagsinput('destroy');
    //     form.find('[data-role="tagsinput"]').tagsinput();
    //
    //
    //     $('#nestable').nestable('add', {"id": result.id, "content": result.name.value});
    //
    //   }).fail(function (jqXHR, textStatus) {
    //     if (jqXHR.status.toString().startsWith("4")) {
    //       $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
    //     } else {
    //       $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
    //     }
    //   });
    //
    //   return false;
    // });

    // 카테고리 삭제
    // $('[data-type="delete-category"]').on('click', function () {
    //
    //   var $categoryItme = $(this);
    //
    //   var data = {
    //     id: $(this).data('id')
    //   };
    //
    //   console.debug(data, 'data');
    //
    //   $.ajax({
    //     url: "delete/" + $(this).data('id'),
    //     method: 'POST',
    //     contentType: "application/json"
    //   }).done(function (result) {
    //     $categoryItme.remove();
    //     $.notify("카테고리가 수정되었습니다.", {status: "success"});
    //
    //   }).fail(function (jqXHR, textStatus) {
    //     if (jqXHR.status.toString().startsWith("4")) {
    //       $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
    //     } else {
    //       $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
    //     }
    //   });
    // });

    // 카테고리 그룹 등록
    // $('#add-category-group').on('click', function () {
    //
    //   var data = {
    //     json: jsonData
    //   };
    //
    //   console.debug(data, 'data');
    //
    //   $.ajax({
    //     url: "group/update",
    //     method: 'POST',
    //     contentType: "application/json",
    //     data: JSON.stringify(data)
    //   }).done(function (result) {
    //     $.notify("카테고리정렬이 수정되었습니다.", {status: "success"});
    //     $('#form-create-category')[0].reset();
    //
    //   }).fail(function (jqXHR, textStatus) {
    //     if (jqXHR.status.toString().startsWith("4")) {
    //       $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
    //     } else {
    //       $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
    //     }
    //   });
    // });

  });

});