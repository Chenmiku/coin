// Custom jQuery
// -----------------------------------

(function (window, document, $, undefined) {

  $(function () {

    $('[data-type="upload-images"]').each(function () {

      var $this = $(this);
      var template = $('#template-item-images').html();
      var target = $this.data('uploadImagesTarget');
      console.debug(target, "target");
      var images = $('[data-type="' + target + '"]');
      var inputName = $this.data('name');
      var validatorTarget = $this.data('validatorTarget');

      var bindingSortable = function () {

        var _sortable = sortable('.sortable', {
          forcePlaceholderSize: true,
          placeholder: '<div class="box-image-uploader empty-image-uploader"></div>'
        });

        _sortable.forEach(function (item) {

          item.addEventListener('sortstart', function (e) {
            // console.debug('---- sortstart ----');
            // console.debug(e.detail);

            /*

             This event is triggered when the user starts sorting and the DOM position has not yet changed.

             e.detail.item contains the current dragged element
             e.detail.placeholder contains the placeholder element
             e.detail.startparent contains the element that the dragged item comes from

             */
          });

          item.addEventListener('sortstop', function (e) {
            // console.debug('---- sortstop ----');
            // console.debug(e.detail);
            /*

             This event is triggered when the user stops sorting. The DOM position may have changed.

             e.detail.item contains the element that was dragged.
             e.detail.startparent contains the element that the dragged item came from.

             */
          });

          item.addEventListener('sortupdate', function (e) {
            console.debug('---- sortupdate ----');
            console.debug(e.detail);

            $(e.detail.endparent).find('li').each(function (index) {
              $(this).find('input[data-type="sortable-order"]').val(index);
            });

            /*

             This event is triggered when the user stopped sorting and the DOM position has changed.

             e.detail.item contains the current dragged element.
             e.detail.index contains the new index of the dragged element (considering only list items)
             e.detail.oldindex contains the old index of the dragged element (considering only list items)
             e.detail.elementIndex contains the new index of the dragged element (considering all items within sortable)
             e.detail.oldElementIndex contains the old index of the dragged element (considering all items within sortable)
             e.detail.startparent contains the element that the dragged item comes from
             e.detail.endparent contains the element that the dragged item was added to (new parent)
             e.detail.newEndList contains all elements in the list the dragged item was dragged to
             e.detail.newStartList contains all elements in the list the dragged item was dragged from
             e.detail.oldStartList contains all elements in the list the dragged item was dragged from BEFORE it was dragged from it
             */
          });
        });

      };

      var loadImages = function (images) {
        if (!isEmpty(images)) {

          // console.debug(images.length, "images");
          images.each(function (index) {
            var url = $(this).data('url');
            var dataPrecision = $(this).data('dataPrecision');
            var orientation = $(this).data('orientation');
            var width = $(this).data('width');
            var height = $(this).data('height');
            var make = $(this).data('make');
            var model = $(this).data('model');
            var software = $(this).data('software');
            var focalLength = $(this).data('focalLength');
            var size = $(this).data('size');
            var item = {
              url: url,
              dataPrecision: dataPrecision,
              orientation: orientation,
              width: width,
              height: height,
              make: make,
              model: model,
              software: software,
              focalLength: focalLength,
              size: size
            };
            loadImage(item, index);
          });

          bindingSortable();
        }
      };

      var loadImage = function (item, index) {
        if (!isEmpty(item.url)) {

          var listImage = $this.find('.list-image-uploader');
          listImage.append(Mustache.render(template, {
            image: item.url,
            dataPrecision: item.metadataEmbed.dataPrecision,
            orientation: item.metadataEmbed.orientation,
            width: item.metadataEmbed.width,
            height: item.metadataEmbed.height,
            make: item.metadataEmbed.make,
            model: item.metadataEmbed.model,
            software: item.metadataEmbed.software,
            focalLength: item.metadataEmbed.focalLength,
            size: item.size,
            index: index,
            inputName: inputName
          }));

          deleteImageBtn();
        }
      };

      var deleteImageBtn = function () {

        var btnDelete = $this.find('.btn-image-uploader-delete');
        console.debug(btnDelete, 'btnDelete');
        var inputFile = $this.find('.input-image-upload');
        btnDelete.off();
        btnDelete.on('click', function () {

          var $btnDelete = $(this);
          $btnDelete.off();
          $btnDelete.closest('.box-image-uploader').remove();
          inputFile.val('');
        });

      };

      loadImages(images);
      deleteImageBtn();

      $this.find('.wrapper-image-upload').each(function () {

        var $wrapper = $(this);
        var inputFile = $wrapper.find('.input-image-upload');
        var imageDropZone = $wrapper;
        var limit = 35;

        inputFile.fileupload({
          dataType: 'json',
          sequentialUploads: true,
          add: function (e, data) {

            // console.debug("add");
            // console.debug(data, 'data');

            if (data.files.error) {
              alert(data.files[0].error);
            }

            var uploadErrors = [];
            var acceptFileTypes = /^image\/(jpe?g|png|gif|svg|blob)$/i;

            if (data.originalFiles[0]['type'] != null && !acceptFileTypes.test(data.originalFiles[0]['type'])) {
              uploadErrors.push('"gif", "jpeg", "jpg", "png", "svg", "blob" 이미지만 등록하세요.');
            }

            if (data.originalFiles[0]['size'] != null && data.originalFiles[0]['size'] > (1048576 * limit)) {
              uploadErrors.push('파일이 너무 큽니다. ' + limit + 'MB이하로 등록하세요.');
            }

            if (uploadErrors.length > 0) {
              alert(uploadErrors.join("\n"));
            } else {
              console.debug(validatorTarget, 'validatorTarget');
              $(validatorTarget).find('.parsley-errors-list').removeClass("filled");
              data.submit();
            }
          },
          done: function (e, data) {
            console.debug("done");
            console.debug(data, 'data');

            var item = data.result;
            // loadImage(item.url, $this.find('.item-box-image').length);
            loadImage(item, $this.find('.item-box-image').length);
            bindingSortable();
          },

          progressall: function (e, data) {
            //console.debug("progressall");
            var progress = parseInt(data.loaded / data.total * 100, 10);
          },

          fail: function (e, data) {
            //console.debug(e);
            //console.debug(data);
            if (data.jqXHR.responseJSON) {
              // alert("서버와 통신 중 문제가 발생했습니다");
              $.notify(data.jqXHR.responseJSON.message, {status: "danger"});
            }
          },
          dropZone: imageDropZone
        });
      });
    });

  });

})(window, document, window.jQuery);