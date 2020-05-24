/**
 * 상품 옵션 with VueJS
 * 2019.04.02 deokin
 * */
$(function () {
  $('[data-type="vue-product-option"]').each(function () {
    var $this = $(this);
    var idProduct = $this.data('idProduct');

    var vm_option = new Vue({
      el: $this[0],
      data: {
        testData: '',
        isUpdate: $this.data('isUpdate'),
        language: {
          koKR: $this.data('koKr'),
          enUS: $this.data('enUs'),
          zhCN: $this.data('zhCn'),
          zhTW: $this.data('zhTw'),
          jaJP: $this.data('jaJp')
        },
        // optionMode: true,
        optionMode: $('#optionModeValue').val() === 'true',
        skuField: [
          // TODO 임시 데이터
          // {
          //   optionName: { textKoKr: '사이즈', textEnUs: 'Size', textZhCn: '', textZhTw: '', textJaJp: '' },
          //   optionValue: [
          //     { textKoKr: 'S', textEnUs: 'S', textZhCn: '', textZhTw: '', textJaJp: '' },
          //     { textKoKr: 'M', textEnUs: 'M', textZhCn: '', textZhTw: '', textJaJp: '' }
          //   ]
          // },
          // {
          //   optionName: { textKoKr: '색상', textEnUs: 'Color', textZhCn: '', textZhTw: '', textJaJp: '' },
          //   optionValue: [
          //     { textKoKr: '블랙', textEnUs: 'Black', textZhCn: '', textZhTw: '', textJaJp: '' },
          //     { textKoKr: '네이비', textEnUs: 'Navy', textZhCn: '', textZhTw: '', textJaJp: '' }
          //   ]
          // },
          {
            optionName: { textKoKr: '', textEnUs: '', textZhCn: '', textZhTw: '', textJaJp: '' },
            optionValue: []
          },
          {
            optionName: { textKoKr: '', textEnUs: '', textZhCn: '', textZhTw: '', textJaJp: '' },
            optionValue: []
          },
          {
            optionName: { textKoKr: '', textEnUs: '', textZhCn: '', textZhTw: '', textJaJp: '' },
            optionValue: []
          }
        ],
        resultOptionData: [
          {
            addPrice: '',
            barCode: '',
            idProduct: '',
            value: {
              textKoKr: ' ',
              textEnUs: ' ',
              textZhCn: ' ',
              textZhTw: ' ',
              textJaJp: ' '
            }
          }
        ],
        hasError: false
      },
      watch: {
        optionMode: function (value) {
          vm_option.handleRequestSkuList();
          if (value) {
            // 활성 시.
          } else {
            // 비활성 시.
            vm_option.handleOnInactiveMode();
            vm_option.handleOnInitTagsInput();
          }
        }
      },
      methods: {
        test: function (e) {
          e.preventDefault();
          console.debug(vm_option.optionMode, '모드')
          console.debug(vm_option.resultOptionData, '테스트')
        },
        /*
        * 공통
        * */
        // 옵션값 추가 버튼 OK
        handleAddOptionValue: function (e, indexSku) {
          e.preventDefault();
          vm_option.skuField[indexSku].optionValue.push({ textKoKr: '', textEnUs: '', textZhCn: '', textZhTw: '', textJaJp: '' })
        },
        // 옵션값 삭제 버튼 OK
        handleRemoveOptionValue: function (e, indexSku, indexOptionValue) {
          e.preventDefault();
          vm_option.skuField[indexSku].optionValue.splice(indexOptionValue, 1)
        },
        // 옵션명/옵션값 언어 유효성 체크
        handleOnValidLanguage: function (item) {
          if (isEmpty(item.textKoKr) && vm_option.language.koKR) {
            $.notify("옵션 정보(국문)를 모두 입력하세요.", { status: "warning" });
            return false
          }
          if (isEmpty(item.textEnUs) && vm_option.language.enUS) {
            $.notify("옵션 정보(영문)를 모두 입력하세요.", { status: "warning" });
            return false
          }
          if (isEmpty(item.textZhCn) && vm_option.language.zhCN) {
            $.notify("옵션 정보(중문(간체))를 모두 입력하세요.", { status: "warning" });
            return false
          }
          if (isEmpty(item.textZhTw) && vm_option.language.zhTW) {
            $.notify("옵션 정보(중문(번체))를 모두 입력하세요.", { status: "warning" });
            return false
          }
          if (isEmpty(item.textJaJp) && vm_option.language.jaJP) {
            $.notify("옵션 정보(일문)를 모두 입력하세요.", { status: "warning" });
            return false
          }
          return true;
        },
        // 옵션 조합 결과 데이터 포맷.
        handleFormatSKU: function (option1, option2, option3) {
          var valueKokr = '';
          var valueEnUs = '';
          var valueJaJp = '';
          var valueZhCn = '';
          var valueZhTw = '';
          if (option1 && option2 && option3) {
            valueKokr = (option1.textKoKr && option2.textKoKr && option3.textKoKr) ? option1.textKoKr + " | " + option2.textKoKr + " | " + option3.textKoKr : '';
            valueEnUs = (option1.textKoKr && option2.textKoKr && option3.textKoKr) ? option1.textEnUs + " | " + option2.textEnUs + " | " + option3.textEnUs : '';
            valueJaJp = (option1.textKoKr && option2.textKoKr && option3.textKoKr) ? option1.textZhCn + " | " + option2.textZhCn + " | " + option3.textZhCn : '';
            valueZhCn = (option1.textKoKr && option2.textKoKr && option3.textKoKr) ? option1.textZhTw + " | " + option2.textZhTw + " | " + option3.textZhTw : '';
            valueZhTw = (option1.textKoKr && option2.textKoKr && option3.textKoKr) ? option1.textJaJp + " | " + option2.textJaJp + " | " + option3.textJaJp : '';
          } else if (option1 && option2 && !option3) {
            valueKokr = (option1.textKoKr && option2.textKoKr) ? option1.textKoKr + " | " + option2.textKoKr : '';
            valueEnUs = (option1.textEnUs && option2.textEnUs) ? option1.textEnUs + " | " + option2.textEnUs : '';
            valueJaJp = (option1.textZhCn && option2.textZhCn) ? option1.textZhCn + " | " + option2.textZhCn : '';
            valueZhCn = (option1.textZhTw && option2.textZhTw) ? option1.textZhTw + " | " + option2.textZhTw : '';
            valueZhTw = (option1.textJaJp && option2.textJaJp) ? option1.textJaJp + " | " + option2.textJaJp : '';
          } else if (option1 && !option2 && !option3) {
            valueKokr = option1.textKoKr;
            valueEnUs = option1.textEnUs;
            valueJaJp = option1.textZhCn;
            valueZhCn = option1.textZhTw;
            valueZhTw = option1.textJaJp;
          }
          return {
            addPrice: '',
            barCode: '',
            idProduct: idProduct ? idProduct : '',
            value: {
              textKoKr: valueKokr,
              textEnUs: valueEnUs,
              textZhCn: valueJaJp,
              textZhTw: valueZhCn,
              textJaJp: valueZhTw
            }
          }
        },
        // 옵션명/옵션값 유효성 체크
        handleOnValidSKU: function () {
          var result = vm_option.skuField.some(function (sku) {
            if (sku.optionValue.length > 0) {
              if (vm_option.handleOnValidLanguage(sku.optionName)) {
                // 빈 필드가 없을 경우 하위 옵션 체크.
                return sku.optionValue.some(function (option) {
                  return !vm_option.handleOnValidLanguage(option);
                })
              } else {
                // 빈 필드가 있을 경우 중단.
                return true
              }
            } else {
              return false
            }
          });
          vm_option.hasError = result;
          return !result
        },
        // 옵션/재고 항목 생성 버튼
        handleCreateSKU: function (e) {
          e.preventDefault();
          if (vm_option.handleOnValidSKU()) {

            var tempResultOption = [];

            // 옵션 3개 까지만 계산 됨.
            if (vm_option.skuField[0].optionValue.length > 0) {
              vm_option.skuField[0].optionValue.forEach(function (item1) {
                if (vm_option.skuField[1].optionValue.length > 0) {
                  vm_option.skuField[1].optionValue.forEach(function (item2) {
                    if (vm_option.skuField[2].optionValue.length > 0) {
                      vm_option.skuField[2].optionValue.forEach(function (item3) {
                        tempResultOption.push(vm_option.handleFormatSKU(item1, item2, item3))
                      })
                    } else {
                      tempResultOption.push(vm_option.handleFormatSKU(item1, item2))
                    }
                  })
                } else {
                  tempResultOption.push(vm_option.handleFormatSKU(item1))
                }
              });

              // 수정/추가 분기
              if (vm_option.isUpdate) {
                // 수정
                vm_option.handleRequestSkuCreate(tempResultOption);
              } else {
                // 추가
                vm_option.resultOptionData = tempResultOption;
                $.notify("옵션이 생성되었습니다.", { status: "success" });
              }

              vm_option.handleOnInitTagsInput();

            } else {
              $.notify("옵션 값을 추가하세요.", { status: "warning" });
            }
          }
        },
        // 옵션/재고 항목 삭제 버튼
        handleRemoveSKU: function (e, index, id) {
          e.preventDefault();
          if (vm_option.isUpdate) {
            vm_option.handleRequestSkuDelete(e, id);
          } else {
            vm_option.resultOptionData.splice(index, 1);
            vm_option.handleOnInitTagsInput();
          }
        },
        // 태그인풋 초기화.
        handleOnInitTagsInput: function () {
          var $this = $('[data-type="vue-product-option"]');
          $this.find('[data-tool="tagsinput"]').tagsinput('destroy');
          $this.find('[data-tool="tagsinput"]').off('change');
          setTimeout(function () {
            $this.find('[data-tool="tagsinput"]').tagsinput();
            if (vm_option.optionMode) {
              $this.find('[data-tool="tagsinput"]').on('change', function (e) {
                var target = $(e.target);
                vm_option.resultOptionData[target.data('index')].barCode = target.val();
              })
            }
          })
        },
        // 비활성 선택 시, 최신 데이터로 셋.
        handleOnInactiveMode: function () {
          if (vm_option.resultOptionData.length > 0) {
            if (vm_option.isUpdate) {
              $('[name="skus[0].id"]').val(vm_option.resultOptionData[0].id);
            }
            $('[name="skus[0].barCode"]').val(vm_option.resultOptionData[0].barCode);
            $('[name="skus[0].addPrice"]').val(vm_option.resultOptionData[0].addPrice);
          } else {
            $('[name="skus[0].id"]').val('');
            $('[name="skus[0].barCode"]').val('');
            $('[name="skus[0].addPrice"]').val('');
          }
        },
        /*
        * 업데이트 용도.
        * */
        // 옵션 목록 불러오기.
        handleRequestSkuList: function () {
          $.ajax({
            url: '/admin/api/sku-product/list?idProduct=' + idProduct,
            method: 'GET',
            contentType: "application/json"
          }).done(function (result) {
            vm_option.resultOptionData = result;

            // TODO 에러방지 임시 방편.
            // 비활성 모드에서 등록 된경우 없는 필드가 생김.
            if (result.length > 0) {
              vm_option.resultOptionData.forEach(function (item, index) {
                if (isEmpty(item.value)) {
                  vm_option.resultOptionData[index].value = {
                    textKoKr: '',
                    textEnUs: '',
                    textZhCn: '',
                    textZhTw: '',
                    textJaJp: ''
                  }
                }
              });
            }
            vm_option.handleOnInitTagsInput();
          }).fail(function (jqXHR, textStatus) {
            if (jqXHR.status.toString().startsWith("4")) {
              $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", { status: "danger" });
            } else {
              $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, { status: "danger" });
            }
          });
        },
        // 옵션 수정하기.
        handleRequestSkuUpdate: function (optionData) {
          console.debug(optionData)
          $.ajax({
            url: '/admin/api/sku-product/update-list',
            method: 'POST',
            contentType: "application/json",
            data: JSON.stringify(optionData)
          }).done(function (result) {
            swal.close();
            $.notify("변경사항이 저장되었습니다.", { status: "success" });
            vm_option.handleRequestSkuList();
          }).fail(function (jqXHR, textStatus) {
            if (jqXHR.status.toString().startsWith("4")) {
              $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", { status: "danger" });
            } else {
              $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, { status: "danger" });
            }
          });
        },
        // 옵션 생성하기.
        handleRequestSkuCreate: function (optionData) {
          $.ajax({
            url: '/admin/api/sku-product/create-list',
            method: 'POST',
            contentType: "application/json",
            data: JSON.stringify(optionData)
          }).done(function (result) {
            swal.close();
            $.notify("옵션이 추가되었습니다.", { status: "success" });
            vm_option.handleRequestSkuList();
          }).fail(function (jqXHR, textStatus) {
            if (jqXHR.status.toString().startsWith("4")) {
              $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", { status: "danger" });
            } else {
              $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, { status: "danger" });
            }
          });
        },
        // 옵션 삭제하기
        handleRequestSkuDelete: function (e, id) {
          if (!isEmpty(id)) {
            var swal_delete = swal({
              title: "옵션을 삭제하시겠습니까?",
              text: "삭제 후 복구가 불가하며, 기존에 고객이 장바구니에 담아둔 상품의 옵션 값도 삭제됩니다.",
              type: "warning",
              showCancelButton: true,
              confirmButtonColor: "#DD6B55",
              confirmButtonText: "네, 삭제합니다.",
              cancelButtonText: "아니오.",
              closeOnConfirm: false,
              closeOnCancel: false
            }, function (isConfirm) {
              if (isConfirm) {
                $.ajax({
                  url: '/admin/api/sku-product/delete',
                  method: 'POST',
                  contentType: "application/json",
                  data: JSON.stringify({ id: id })
                }).done(function (result) {
                  swal.close();
                  $.notify("옵션이 삭제되었습니다.", { status: "success" });
                  vm_option.handleRequestSkuList();
                }).fail(function (jqXHR, textStatus) {
                  if (jqXHR.status.toString().startsWith("4")) {
                    $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", { status: "danger" });
                  } else {
                    $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, { status: "danger" });
                  }
                });
              } else {
                swal("취소되었습니다.", "이 데이터는 그대로 유지됩니다.", "error");
              }
            });
          }
        }
      },
      updated:

        function () {
        }

      ,
      mounted: function () {
        // spring 동기 데이터 초기화
        this.handleRequestSkuList();
        this.handleOnInitTagsInput();

        if (this.isUpdate && this.optionMode) {

          this.skuField[0].optionName = {
            textKoKr: this.$refs['skuField1.textKoKr'].value,
            textEnUs: this.$refs['skuField1.textEnUs'].value,
            textZhCn: this.$refs['skuField1.textZhCn'].value,
            textZhTw: this.$refs['skuField1.textZhTw'].value,
            textJaJp: this.$refs['skuField1.textJaJp'].value
          };
          this.skuField[1].optionName = {
            textKoKr: this.$refs['skuField2.textKoKr'].value,
            textEnUs: this.$refs['skuField2.textEnUs'].value,
            textZhCn: this.$refs['skuField2.textZhCn'].value,
            textZhTw: this.$refs['skuField2.textZhTw'].value,
            textJaJp: this.$refs['skuField2.textJaJp'].value
          };
          this.skuField[2].optionName = {
            textKoKr: this.$refs['skuField3.textKoKr'].value,
            textEnUs: this.$refs['skuField3.textEnUs'].value,
            textZhCn: this.$refs['skuField3.textZhCn'].value,
            textZhTw: this.$refs['skuField3.textZhTw'].value,
            textJaJp: this.$refs['skuField3.textJaJp'].value
          };
        }
      }
    });
  });
})
;