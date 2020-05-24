/**
 * 관련 상품 with VueJS
 * 2019.04.15 Kim Deok IN
 * */
$(function () {
  var vm_rp = null;
  var vm_rp_modal = null;

  // 선택 된 목록
  $('[data-relation-product]').each(function () {
    var $this = $(this);

    // Vue 인스턴스
    vm_rp = new Vue({
      el: $this[0],
      data: {
        productsData: [],
        productsIdList: [] // 중복 체크를 위한 id 배열
      },
      watch: {
        productsData: function (val) {
          var tempArray = [];
          val.forEach(function (item) {
            tempArray.push(item.id);
          });
          vm_rp.productsIdList = tempArray;
        }
      },
      methods: {
        // 관련 상품 목록 요청.
        handleRequestData: function () {
          // var idProduct = $this.data('relation-product');
          var url = $this.data('url');
          if (!isEmpty(url)) {
            $.ajax({
              url: url,
              method: 'GET',
              contentType: "application/json"
            }).done(function (result) {
              if (result.hasOwnProperty('_embedded') ||
                result.hasOwnProperty('productInfoList')) {
                vm_rp.productsData = result._embedded.productInfoList
              }
            }).fail(function (jqXHR, textStatus) {
              if (jqXHR.status.toString().startsWith("4")) {
                $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", { status: "danger" });
              } else {
                $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, { status: "danger" });
              }
            });
          }
        },
        // 관련 상품 추가.
        handleAddProducts: function (arrayProducts) {
          if (isArray(arrayProducts)) {
            var tempArray = [];
            arrayProducts.forEach(function (item) {
              // 중복 제거.
              if ($.inArray(item.id, vm_rp.productsIdList) === -1) {
                tempArray.push(item);
              }
            });
            vm_rp.productsData = vm_rp.productsData.concat(tempArray);
          }
        },
        // 목록 제거.
        handleRemoveOption: function (e, index) {
          e.preventDefault();
          vm_rp.productsData.splice(index, 1);
        },
      },
      mounted: function () {
      }
    });
    // init
    vm_rp.handleRequestData();
  });

  // 관련 상품 검색 모달
  $('[data-modal-relattion-product]').each(function () {
    var $this = $(this);

    // Vue 인스턴스
    vm_rp_modal = new Vue({
      el: $this[0],
      data: {
        isProgress: false,
        pageInfo: {},
        searchParam: {
          query: '',
          idCategory: [],
          idBrand: ''
        },
        category: {
          group1: [],
          group2: [],
          group3: [],
          group4: [],
          group5: [],
          value1: '',
          value2: '',
          value3: '',
          value4: '',
          value5: ''
        },
        productsData: [],
        checkProduct: []
      },
      watch: {
        'category.value1': function (value) {
          vm_rp_modal.category.group2 = vm_rp_modal.category.value1.children ? vm_rp_modal.category.value1.children : [];
          vm_rp_modal.category.group3 = [];
          vm_rp_modal.category.group4 = [];
          vm_rp_modal.category.group5 = [];
          vm_rp_modal.category.value2 = '';
          vm_rp_modal.category.value3 = '';
          vm_rp_modal.category.value4 = '';
          vm_rp_modal.category.value5 = '';
          vm_rp_modal.handleChangeCategoryParam();
        },
        'category.value2': function (value) {
          vm_rp_modal.category.group3 = vm_rp_modal.category.value2.children ? vm_rp_modal.category.value2.children : [];
          vm_rp_modal.category.group4 = [];
          vm_rp_modal.category.group5 = [];
          vm_rp_modal.category.value3 = '';
          vm_rp_modal.category.value4 = '';
          vm_rp_modal.category.value5 = '';
          vm_rp_modal.handleChangeCategoryParam();
        },
        'category.value3': function (value) {
          vm_rp_modal.category.group4 = vm_rp_modal.category.value3.children ? vm_rp_modal.category.value3.children : [];
          vm_rp_modal.category.group5 = [];
          vm_rp_modal.category.value4 = '';
          vm_rp_modal.category.value5 = '';
          vm_rp_modal.handleChangeCategoryParam();
        },
        'category.value4': function (value) {
          vm_rp_modal.category.group5 = vm_rp_modal.category.value4.children ? vm_rp_modal.category.value4.children : [];
          vm_rp_modal.category.value5 = '';
          vm_rp_modal.handleChangeCategoryParam();
        },
        'category.value5': function (value) {
        }
      },
      methods: {
        testFunc: function (e) {
          e.preventDefault();
          console.debug(vm_rp_modal.searchParam, 'search param')
        },

        // 카테고리 데이터 요청.
        handleRequestCategory: function (idCategory) {
          $.ajax({
            url: '/admin/api/category-product/group' + (idCategory ? '?idCategory=' + idCategory : ''),
            method: 'GET',
            contentType: "application/json"
          }).done(function (result) {
            if (!isEmpty(result)) {
              vm_rp_modal.category.group1 = result
            }
          }).fail(function (jqXHR, textStatus) {
            if (jqXHR.status.toString().startsWith("4")) {
              $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", { status: "danger" });
            } else {
              $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, { status: "danger" });
            }
          });
        },
        // 상품 데이터 요청.
        handleRequestProduct: function (isMore) {
          vm_rp_modal.isProgress = true;
          var query = '?query=' + vm_rp_modal.searchParam.query;
          var idCategory = '&idCategory=' + vm_rp_modal.searchParam.idCategory.toString();
          var idBrand = '&idBrand=' + vm_rp_modal.searchParam.idBrand;
          var idProduct = '&idProduct=' + $this.data('modal-relattion-product');
          var page = '&page=' + (vm_rp_modal.pageInfo.number ? vm_rp_modal.pageInfo.number : 0);
          console.debug(idProduct, 'idProduct');
          // var size = '&size=2';
          $.ajax({
            url: '/admin/api/product' + query + idCategory + idBrand + idProduct + page,
            method: 'GET',
            contentType: "application/json"
          }).done(function (res) {
            if (res.hasOwnProperty("_embedded")
              && res._embedded.hasOwnProperty('productInfoList')
              && res._embedded.productInfoList.length > 0) {

              if (isMore) {
                vm_rp_modal.productsData = vm_rp_modal.productsData.concat(res._embedded.productInfoList);
              } else {
                vm_rp_modal.productsData = res._embedded.productInfoList
              }

              vm_rp_modal.pageInfo = res.page
            } else {
              vm_rp_modal.productsData = []
            }
            vm_rp_modal.isProgress = false;
          }).fail(function (jqXHR, textStatus) {
            vm_rp_modal.isProgress = false;
            if (jqXHR.status.toString().startsWith("4")) {
              $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", { status: "danger" });
            } else {
              $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, { status: "danger" });
            }
          });
        },
        // 카테고리 파라미터 데이터 셋팅
        handleChangeCategoryParam: function () {
          var tempCategory = [];
          vm_rp_modal.category.value1 ? tempCategory.push(vm_rp_modal.category.value1.id) : '';
          vm_rp_modal.category.value2 ? tempCategory.push(vm_rp_modal.category.value2.id) : '';
          vm_rp_modal.category.value3 ? tempCategory.push(vm_rp_modal.category.value3.id) : '';
          vm_rp_modal.category.value4 ? tempCategory.push(vm_rp_modal.category.value4.id) : '';
          vm_rp_modal.category.value5 ? tempCategory.push(vm_rp_modal.category.value5.id) : '';
          vm_rp_modal.searchParam.idCategory = tempCategory;
        },
        // 필터 검색
        handleOnClickSearch: function (e) {
          e.preventDefault();
          vm_rp_modal.pageInfo = {};
          vm_rp_modal.handleRequestProduct(false);
        },
        // 더 보기 버튼 클릭.
        handleOnClickMore: function (e) {
          if (vm_rp_modal.pageInfo.totalPages > (vm_rp_modal.pageInfo.number + 1)) {
            vm_rp_modal.pageInfo.number++;
            vm_rp_modal.handleRequestProduct(true);
          }
        },
        // 필터 초기화.
        handleOnClickResetCategory: function () {
          vm_rp_modal.searchParam = { query: '', idCategory: [], idBrand: '' }
          vm_rp_modal.category = { group1: [], group2: [], group3: [], group4: [], group5: [], value1: '', value2: '', value3: '', value4: '', value5: '' }
        },
        // 데이터 리셋.
        handleOnClickResetData: function () {
          vm_rp_modal.handleOnClickResetCategory();
          vm_rp_modal.pageInfo = {};
          vm_rp_modal.productsData = [];
          vm_rp_modal.checkProduct = [];
          vm_rp_modal.isProgress = false;
        },
        // 전체 체크.
        handleOnChangeCheckAll: function (e) {
          e.target.checked ? vm_rp_modal.checkProduct = vm_rp_modal.productsData : vm_rp_modal.checkProduct = [];
        },
        // 상품 선택 완료.
        handleOnClickApply: function () {
          vm_rp.handleAddProducts(vm_rp_modal.checkProduct);
          $('[data-modal-relattion-product]').modal('hide');
        },
        // 셀렉트박스 라이브러리 초기화.
        handleInitSelectBox: function () {
          var $this_modal = $('[data-modal-relattion-product]');
          var $selectBox = $this_modal.find('[data-type="chosen-select"]');
          $selectBox.chosen('destroy');
          setTimeout(function(){
            $selectBox.chosen();
            $selectBox.on('change', function (e) {
              vm_rp_modal.searchParam.idBrand = e.target.value;
            })
          });
        }
      },
      mounted: function () {
        // jquery select box library binding
        this.handleInitSelectBox();
      }
    });
    // init
    vm_rp_modal.handleRequestCategory();

    // 모달 핸들
    var modalRelationProduct = $('[data-modal-relattion-product]');
    // 모달 실행
    modalRelationProduct.on('show.bs.modal', function () {
    });
    // 모달 닫기
    modalRelationProduct.on('hidden.bs.modal', function () {
      vm_rp_modal.handleOnClickResetData();
      vm_rp_modal.handleInitSelectBox();
    });

  });

});