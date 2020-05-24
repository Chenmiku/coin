$(function () {
  $('.entry-work-create-update').each(function () {


    var $panelTypeImage = $('#panel-type-image');
    var $panelTypeVideo = $('#panel-type-video');

    var view = function (type) {

      if (type === 'VIDEO') {
        $panelTypeImage.hide();
        $panelTypeVideo.show();
      } else {
        $panelTypeVideo.hide();
        $panelTypeImage.show();
      }
      if ($('[name="pod"]:checked').val() === 'OUTDOOR') {
        $('#image-outdoor').show();
      } else {
        $('#image-outdoor').hide();
      }
    };

    view($('[name="type"]:checked').val());

    $('[name="type"]').on('change', function () {

      view($(this).val());
    });


    var $imageSpaceZoom = $('#image-space-zoom');
    var $imageUltra = $('#image-ultra');
    var $imageOutdoor = $('#image-outdoor');
    var $imageAI = $('#image-ai');

    var imageView = function (pod) {

      $imageSpaceZoom.hide();
      $imageUltra.hide();
      $imageOutdoor.hide();
      $imageAI.hide();

      if ($('[name="type"]:checked').val() === 'IMAGE') {
        if (pod === 'SPACE_ZOOM_100') {
          $imageSpaceZoom.show();
        } else if (pod === 'ULTRA') {
          $imageUltra.show();
        }
      } else {
        if (pod === 'OUTDOOR') {
          $imageOutdoor.show();
        } else if (pod === 'AI') {
          $imageAI.show();
        }
      }
    };

    imageView($('[name="pod"]:checked').val());

    $('[name="pod"]').on('change', function () {

      imageView($(this).val());
    });


    // ytid(video_url); //GET ID

// Get Youtube ID
    var youtubeId = function (video_url) {
      var video_id = video_url.split('v=')[1];
      var ampersandPosition = video_id.indexOf('&');
      if (ampersandPosition != -1) {
        video_id = video_id.substring(0, ampersandPosition);
      }
      return video_id;
    };

    // yturl(video_url); // IF Youtube

    var validYoutube = function (video_url) {
      return video_url.match(/watch\?v=([a-zA-Z0-9\-_]+)/);
    };


    // $('[name="youtube"]').focusout(function () {
    //   var url = $(this).val();
    //
    //   if (url && validYoutube(url)) {
    //     $('[name="youtubeId"]').val(youtubeId(url));
    //   } else {
    //     alert('올바른 Youtube url이 아닙니다.');
    //   }
    // })

  });

  $('.entry-work-list').each(function () {

    // 영감 카테고리
    $('[name="allInspCategories"]').on('change', function () {
      var $this = $(this);

      var idInspCategories = $('[name="idInspCategories"]');
      idInspCategories.prop('checked', $this.is(":checked"));
    });

    $('[name="idInspCategories"]').each(function () {
      $(this).on('change', function () {
        if ($(this).is(":checked") === false) {
          $('[name="allInspCategories"]').prop('checked', false);
        }
      })
    });

    // 기능 카테고리
    $('[name="allFuncCategories"]').on('change', function () {
      var $this = $(this);
      var idCategories = $('[name="idFuncCategories"]');
      idCategories.prop('checked', $this.is(":checked"));
    });

    $('[name="idFuncCategories"]').each(function () {
      $(this).on('change', function () {
        if ($(this).is(":checked") === false) {
          $('[name="allFuncCategories"]').prop('checked', false);
        }
      })
    });

    // 이벤트
    $('[name="allCheckedEvents"]').on('change', function () {
      var $this = $(this);
      var list = $('[name="checkedEvents"]');
      list.prop('checked', $this.is(":checked"));
    });

    $('[name="checkedEvents"]').each(function () {
      $(this).on('change', function () {
        if ($(this).is(":checked") === false) {
          $('[name="allCheckedEvents"]').prop('checked', false);
        }
      })
    });

    // 지원부문
    $('[name="allCheckedTypes"]').on('change', function () {
      var $this = $(this);
      var list = $('[name="checkedTypes"]');
      list.prop('checked', $this.is(":checked"));
    });

    $('[name="checkedTypes"]').each(function () {
      $(this).on('change', function () {
        if ($(this).is(":checked") === false) {
          $('[name="allCheckedTypes"]').prop('checked', false);
        }
      })
    });

    // POD
    $('[name="allCheckedPods"]').on('change', function () {
      var $this = $(this);
      var list = $('[name="checkedPods"]');
      list.prop('checked', $this.is(":checked"));
    });

    $('[name="checkedPods"]').each(function () {
      $(this).on('change', function () {
        if ($(this).is(":checked") === false) {
          $('[name="allCheckedPods"]').prop('checked', false);
        }
      })
    });

    // 응모작 수상 상태
    $('[name="allCheckedStatuses"]').on('change', function () {
      var $this = $(this);
      var list = $('[name="checkedStatuses"]');
      list.prop('checked', $this.is(":checked"));
    });

    $('[name="checkedStatuses"]').each(function () {
      $(this).on('change', function () {
        if ($(this).is(":checked") === false) {
          $('[name="allCheckedStatuses"]').prop('checked', false);
        }
      })
    });
  });


});