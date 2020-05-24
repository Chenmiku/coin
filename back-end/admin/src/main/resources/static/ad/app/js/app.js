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

// Start Bootstrap JS
// ----------------------------------- 

(function(window, document, $, undefined){

  $(function(){

    // POPOVER
    // ----------------------------------- 

    $('[data-toggle="popover"]').popover();

    // TOOLTIP
    // ----------------------------------- 

    $('[data-toggle="tooltip"]').tooltip({
      container: 'body'
    });

    // DROPDOWN INPUTS
    // ----------------------------------- 
    $('.dropdown input').on('click focus', function(event){
      event.stopPropagation();
    });

  });

})(window, document, window.jQuery);

// Custom jQuery
// ----------------------------------- 


(function(window, document, $, undefined){

  if(!$.fn.fullCalendar) return;

  // When dom ready, init calendar and events
  $(function() {

      // The element that will display the calendar
      var calendar = $('#calendar');

      var demoEvents = createDemoEvents();

      initExternalEvents(calendar);

      initCalendar(calendar, demoEvents);

  });


  // global shared var to know what we are dragging
  var draggingEvent = null;

  /**
   * ExternalEvent object
   * @param jQuery Object elements Set of element as jQuery objects
   */
  var ExternalEvent = function (elements) {
      
      if (!elements) return;
      
      elements.each(function() {
          var $this = $(this);
          // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
          // it doesn't need to have a start or end
          var calendarEventObject = {
              title: $.trim($this.text()) // use the element's text as the event title
          };

          // store the Event Object in the DOM element so we can get to it later
          $this.data('calendarEventObject', calendarEventObject);

          // make the event draggable using jQuery UI
          $this.draggable({
              zIndex: 1070,
              revert: true, // will cause the event to go back to its
              revertDuration: 0  //  original position after the drag
          });

      });
  };

  /**
   * Invoke full calendar plugin and attach behavior
   * @param  jQuery [calElement] The calendar dom element wrapped into jQuery
   * @param  EventObject [events] An object with the event list to load when the calendar displays
   */
  function initCalendar(calElement, events) {

      // check to remove elements from the list
      var removeAfterDrop = $('#remove-after-drop');

      calElement.fullCalendar({
          // isRTL: true,
          header: {
              left:   'prev,next today',
              center: 'title',
              right:  'month,agendaWeek,agendaDay'
          },
          buttonIcons: { // note the space at the beginning
              prev:    ' fa fa-caret-left',
              next:    ' fa fa-caret-right'
          },
          buttonText: {
              today: 'today',
              month: 'month',
              week:  'week',
              day:   'day'
          },
          editable: true,
          droppable: true, // this allows things to be dropped onto the calendar 
          drop: function(date, allDay) { // this function is called when something is dropped
              
              var $this = $(this),
                  // retrieve the dropped element's stored Event Object
                  originalEventObject = $this.data('calendarEventObject');

              // if something went wrong, abort
              if(!originalEventObject) return;

              // clone the object to avoid multiple events with reference to the same object
              var clonedEventObject = $.extend({}, originalEventObject);

              // assign the reported date
              clonedEventObject.start = date;
              clonedEventObject.allDay = allDay;
              clonedEventObject.backgroundColor = $this.css('background-color');
              clonedEventObject.borderColor = $this.css('border-color');

              // render the event on the calendar
              // the last `true` argument determines if the event "sticks" 
              // (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
              calElement.fullCalendar('renderEvent', clonedEventObject, true);
              
              // if necessary remove the element from the list
              if(removeAfterDrop.is(':checked')) {
                $this.remove();
              }
          },
          eventDragStart: function (event, js, ui) {
            draggingEvent = event;
          },
          // This array is the events sources
          events: events
      });
  }

  /**
   * Inits the external events panel
   * @param  jQuery [calElement] The calendar dom element wrapped into jQuery
   */
  function initExternalEvents(calElement){
    // Panel with the external events list
    var externalEvents = $('.external-events');

    // init the external events in the panel
    new ExternalEvent(externalEvents.children('div'));

    // External event color is danger-red by default
    var currColor = '#f6504d';
    // Color selector button
    var eventAddBtn = $('.external-event-add-btn');
    // New external event name input
    var eventNameInput = $('.external-event-name');
    // Color switchers
    var eventColorSelector = $('.external-event-color-selector .circle');

    // Trash events Droparea 
    $('.external-events-trash').droppable({
      accept:       '.fc-event',
      activeClass:  'active',
      hoverClass:   'hovered',
      tolerance:    'touch',
      drop: function(event, ui) {
        
        // You can use this function to send an ajax request
        // to remove the event from the repository
        
        if(draggingEvent) {
          var eid = draggingEvent.id || draggingEvent._id;
          // Remove the event
          calElement.fullCalendar('removeEvents', eid);
          // Remove the dom element
          ui.draggable.remove();
          // clear
          draggingEvent = null;
        }
      }
    });

    eventColorSelector.click(function(e) {
        e.preventDefault();
        var $this = $(this);

        // Save color
        currColor = $this.css('background-color');
        // De-select all and select the current one
        eventColorSelector.removeClass('selected');
        $this.addClass('selected');
    });

    eventAddBtn.click(function(e) {
        e.preventDefault();
        
        // Get event name from input
        var val = eventNameInput.val();
        // Dont allow empty values
        if ($.trim(val) === '') return;
        
        // Create new event element
        var newEvent = $('<div/>').css({
                            'background-color': currColor,
                            'border-color':     currColor,
                            'color':            '#fff'
                        })
                        .html(val);

        // Prepends to the external events list
        externalEvents.prepend(newEvent);
        // Initialize the new event element
        new ExternalEvent(newEvent);
        // Clear input
        eventNameInput.val('');
    });
  }

  /**
   * Creates an array of events to display in the first load of the calendar
   * Wrap into this function a request to a source to get via ajax the stored events
   * @return Array The array with the events
   */
  function createDemoEvents() {
    // Date for the calendar events (dummy data)
    var date = new Date();
    var d = date.getDate(),
        m = date.getMonth(),
        y = date.getFullYear();

    return  [
              {
                  title: 'All Day Event',
                  start: new Date(y, m, 1),
                  backgroundColor: '#f56954', //red 
                  borderColor: '#f56954' //red
              },
              {
                  title: 'Long Event',
                  start: new Date(y, m, d - 5),
                  end: new Date(y, m, d - 2),
                  backgroundColor: '#f39c12', //yellow
                  borderColor: '#f39c12' //yellow
              },
              {
                  title: 'Meeting',
                  start: new Date(y, m, d, 10, 30),
                  allDay: false,
                  backgroundColor: '#0073b7', //Blue
                  borderColor: '#0073b7' //Blue
              },
              {
                  title: 'Lunch',
                  start: new Date(y, m, d, 12, 0),
                  end: new Date(y, m, d, 14, 0),
                  allDay: false,
                  backgroundColor: '#00c0ef', //Info (aqua)
                  borderColor: '#00c0ef' //Info (aqua)
              },
              {
                  title: 'Birthday Party',
                  start: new Date(y, m, d + 1, 19, 0),
                  end: new Date(y, m, d + 1, 22, 30),
                  allDay: false,
                  backgroundColor: '#00a65a', //Success (green)
                  borderColor: '#00a65a' //Success (green)
              },
              {
                  title: 'Open Google',
                  start: new Date(y, m, 28),
                  end: new Date(y, m, 29),
                  url: '//google.com/',
                  backgroundColor: '#3c8dbc', //Primary (light-blue)
                  borderColor: '#3c8dbc' //Primary (light-blue)
              }
          ];
  }

})(window, document, window.jQuery);



// Easypie chart
// -----------------------------------

(function(window, document, $, undefined) {

    $(function() {

        if(! $.fn.easyPieChart ) return;

        var pieOptions1 = {
            animate: {
                duration: 800,
                enabled: true
            },
            barColor: APP_COLORS['success'],
            trackColor: false,
            scaleColor: false,
            lineWidth: 10,
            lineCap: 'circle'
        };
        $('#easypie1').easyPieChart(pieOptions1);

        var pieOptions2 = {
            animate: {
                duration: 800,
                enabled: true
            },
            barColor: APP_COLORS['warning'],
            trackColor: false,
            scaleColor: false,
            lineWidth: 4,
            lineCap: 'circle'
        };
        $('#easypie2').easyPieChart(pieOptions2);

        var pieOptions3 = {
            animate: {
                duration: 800,
                enabled: true
            },
            barColor: APP_COLORS['danger'],
            trackColor: false,
            scaleColor: APP_COLORS['gray'],
            lineWidth: 15,
            lineCap: 'circle'
        };
        $('#easypie3').easyPieChart(pieOptions3);

        var pieOptions4 = {
            animate: {
                duration: 800,
                enabled: true
            },
            barColor: APP_COLORS['danger'],
            trackColor: APP_COLORS['yellow'],
            scaleColor: APP_COLORS['gray-dark'],
            lineWidth: 15,
            lineCap: 'circle'
        };
        $('#easypie4').easyPieChart(pieOptions4);

    });

})(window, document, window.jQuery);
// Knob chart
// -----------------------------------

(function(window, document, $, undefined){

  $(function(){

        if(! $.fn.knob ) return;

        var knobLoaderOptions1 = {
            width: '50%', // responsive
            displayInput: true,
            fgColor: APP_COLORS['info']
        };
        $('#knob-chart1').knob(knobLoaderOptions1);

        var knobLoaderOptions2 = {
            width: '50%', // responsive
            displayInput: true,
            fgColor: APP_COLORS['purple'],
            readOnly: true
        };
        $('#knob-chart2').knob(knobLoaderOptions2);

        var knobLoaderOptions3 = {
            width: '50%', // responsive
            displayInput: true,
            fgColor: APP_COLORS['info'],
            bgColor: APP_COLORS['gray'],
            angleOffset: -125,
            angleArc: 250
        };
        $('#knob-chart3').knob(knobLoaderOptions3);

        var knobLoaderOptions4 = {
            width: '50%', // responsive
            displayInput: true,
            fgColor: APP_COLORS['pink'],
            displayPrevious: true,
            thickness: 0.1,
            lineCap: 'round'
        };
        $('#knob-chart4').knob(knobLoaderOptions4);

  });

})(window, document, window.jQuery);

// Start Bootstrap JS
// -----------------------------------

(function(window, document, $, undefined) {

    $(function() {

        if (typeof Chart === 'undefined') return;

        // random values for demo
        var rFactor = function() {
            return Math.round(Math.random() * 100);
        };

        // Line chart
        // -----------------------------------

        var lineData = {
            labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
            datasets: [{
                label: 'My First dataset',
                backgroundColor: 'rgba(114,102,186,0.2)',
                borderColor: 'rgba(114,102,186,1)',
                pointBorderColor: '#fff',
                data: [rFactor(), rFactor(), rFactor(), rFactor(), rFactor(), rFactor(), rFactor()]
            }, {
                label: 'My Second dataset',
                backgroundColor: 'rgba(35,183,229,0.2)',
                borderColor: 'rgba(35,183,229,1)',
                pointBorderColor: '#fff',
                data: [rFactor(), rFactor(), rFactor(), rFactor(), rFactor(), rFactor(), rFactor()]
            }]
        };

        var lineOptions = {
            legend: {
                display: false
            }
        };
        var linectx = document.getElementById('chartjs-linechart').getContext('2d');
        var lineChart = new Chart(linectx, {
            data: lineData,
            type: 'line',
            options: lineOptions
        });

        // Bar chart
        // -----------------------------------

        var barData = {
            labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
            datasets: [{
                backgroundColor: '#23b7e5',
                borderColor: '#23b7e5',
                data: [rFactor(), rFactor(), rFactor(), rFactor(), rFactor(), rFactor(), rFactor()]
            }, {
                backgroundColor: '#5d9cec',
                borderColor: '#5d9cec',
                data: [rFactor(), rFactor(), rFactor(), rFactor(), rFactor(), rFactor(), rFactor()]
            }]
        };

        var barOptions = {
            legend: {
                display: false
            }
        };
        var barctx = document.getElementById('chartjs-barchart').getContext('2d');
        var barChart = new Chart(barctx, {
            data: barData,
            type: 'bar',
            options: barOptions
        });

        //  Doughnut chart
        // -----------------------------------

        var doughnutData = {
            labels: [
                'Purple',
                'Yellow',
                'Blue'
            ],
            datasets: [{
                data: [300, 50, 100],
                backgroundColor: [
                    '#7266ba',
                    '#fad732',
                    '#23b7e5'
                ],
                hoverBackgroundColor: [
                    '#7266ba',
                    '#fad732',
                    '#23b7e5'
                ]
            }]
        };

        var doughnutOptions = {
            legend: {
                display: false
            }
        };
        var doughnutctx = document.getElementById('chartjs-doughnutchart').getContext('2d');
        var doughnutChart = new Chart(doughnutctx, {
            data: doughnutData,
            type: 'doughnut',
            options: doughnutOptions
        });

        // Pie chart
        // -----------------------------------

        var pieData = {
            labels: [
                'Purple',
                'Yellow',
                'Blue'
            ],
            datasets: [{
                data: [300, 50, 100],
                backgroundColor: [
                    '#7266ba',
                    '#fad732',
                    '#23b7e5'
                ],
                hoverBackgroundColor: [
                    '#7266ba',
                    '#fad732',
                    '#23b7e5'
                ]
            }]
        };

        var pieOptions = {
            legend: {
                display: false
            }
        };
        var piectx = document.getElementById('chartjs-piechart').getContext('2d');
        var pieChart = new Chart(piectx, {
            data: pieData,
            type: 'pie',
            options: pieOptions
        });

        // Polar chart
        // -----------------------------------

        var polarData = {
            datasets: [{
                data: [
                    11,
                    16,
                    7,
                    3
                ],
                backgroundColor: [
                    '#f532e5',
                    '#7266ba',
                    '#f532e5',
                    '#7266ba'
                ],
                label: 'My dataset' // for legend
            }],
            labels: [
                'Label 1',
                'Label 2',
                'Label 3',
                'Label 4'
            ]
        };

        var polarOptions = {
            legend: {
                display: false
            }
        };
        var polarctx = document.getElementById('chartjs-polarchart').getContext('2d');
        var polarChart = new Chart(polarctx, {
            data: polarData,
            type: 'polarArea',
            options: polarOptions
        });

        // Radar chart
        // -----------------------------------

        var radarData = {
            labels: ['Eating', 'Drinking', 'Sleeping', 'Designing', 'Coding', 'Cycling', 'Running'],
            datasets: [{
                label: 'My First dataset',
                backgroundColor: 'rgba(114,102,186,0.2)',
                borderColor: 'rgba(114,102,186,1)',
                data: [65, 59, 90, 81, 56, 55, 40]
            }, {
                label: 'My Second dataset',
                backgroundColor: 'rgba(151,187,205,0.2)',
                borderColor: 'rgba(151,187,205,1)',
                data: [28, 48, 40, 19, 96, 27, 100]
            }]
        };

        var radarOptions = {
            legend: {
                display: false
            }
        };
        var radarctx = document.getElementById('chartjs-radarchart').getContext('2d');
        var radarChart = new Chart(radarctx, {
            data: radarData,
            type: 'radar',
            options: radarOptions
        });

    });

})(window, document, window.jQuery);
// Chartist
// ----------------------------------- 

(function(window, document, $, undefined){

  $(function(){

    if ( typeof Chartist === 'undefined' ) return;

    // Bar bipolar
    // ----------------------------------- 
    var data1 = {
      labels: ['W1', 'W2', 'W3', 'W4', 'W5', 'W6', 'W7', 'W8', 'W9', 'W10'],
      series: [
        [1, 2, 4, 8, 6, -2, -1, -4, -6, -2]
      ]
    };

    var options1 = {
      high: 10,
      low: -10,
      height: 280,
      axisX: {
        labelInterpolationFnc: function(value, index) {
          return index % 2 === 0 ? value : null;
        }
      }
    };

    new Chartist.Bar('#ct-bar1', data1, options1);

    // Bar Horizontal
    // ----------------------------------- 
    new Chartist.Bar('#ct-bar2', {
      labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
      series: [
        [5, 4, 3, 7, 5, 10, 3],
        [3, 2, 9, 5, 4, 6, 4]
      ]
    }, {
      seriesBarDistance: 10,
      reverseData: true,
      horizontalBars: true,
      height: 280,
      axisY: {
        offset: 70
      }
    });

    // Line
    // ----------------------------------- 
    new Chartist.Line('#ct-line1', {
      labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
      series: [
        [12, 9, 7, 8, 5],
        [2, 1, 3.5, 7, 3],
        [1, 3, 4, 5, 6]
      ]
    }, {
      fullWidth: true,
      height: 280,
      chartPadding: {
        right: 40
      }
    });


    // SVG Animation
    // ----------------------------------- 

    var chart1 = new Chartist.Line('#ct-line3', {
      labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
      series: [
        [1, 5, 2, 5, 4, 3],
        [2, 3, 4, 8, 1, 2],
        [5, 4, 3, 2, 1, 0.5]
      ]
    }, {
      low: 0,
      showArea: true,
      showPoint: false,
      fullWidth: true,
      height: 300
    });

    chart1.on('draw', function(data) {
      if(data.type === 'line' || data.type === 'area') {
        data.element.animate({
          d: {
            begin: 2000 * data.index,
            dur: 2000,
            from: data.path.clone().scale(1, 0).translate(0, data.chartRect.height()).stringify(),
            to: data.path.clone().stringify(),
            easing: Chartist.Svg.Easing.easeOutQuint
          }
        });
      }
    });


    // Slim animation
    // ----------------------------------- 


    var chart = new Chartist.Line('#ct-line2', {
      labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
      series: [
        [12, 9, 7, 8, 5, 4, 6, 2, 3, 3, 4, 6],
        [4,  5, 3, 7, 3, 5, 5, 3, 4, 4, 5, 5],
        [5,  3, 4, 5, 6, 3, 3, 4, 5, 6, 3, 4],
        [3,  4, 5, 6, 7, 6, 4, 5, 6, 7, 6, 3]
      ]
    }, {
      low: 0,
      height: 300
    });

    // Let's put a sequence number aside so we can use it in the event callbacks
    var seq = 0,
      delays = 80,
      durations = 500;

    // Once the chart is fully created we reset the sequence
    chart.on('created', function() {
      seq = 0;
    });

    // On each drawn element by Chartist we use the Chartist.Svg API to trigger SMIL animations
    chart.on('draw', function(data) {
      seq++;

      if(data.type === 'line') {
        // If the drawn element is a line we do a simple opacity fade in. This could also be achieved using CSS3 animations.
        data.element.animate({
          opacity: {
            // The delay when we like to start the animation
            begin: seq * delays + 1000,
            // Duration of the animation
            dur: durations,
            // The value where the animation should start
            from: 0,
            // The value where it should end
            to: 1
          }
        });
      } else if(data.type === 'label' && data.axis === 'x') {
        data.element.animate({
          y: {
            begin: seq * delays,
            dur: durations,
            from: data.y + 100,
            to: data.y,
            // We can specify an easing function from Chartist.Svg.Easing
            easing: 'easeOutQuart'
          }
        });
      } else if(data.type === 'label' && data.axis === 'y') {
        data.element.animate({
          x: {
            begin: seq * delays,
            dur: durations,
            from: data.x - 100,
            to: data.x,
            easing: 'easeOutQuart'
          }
        });
      } else if(data.type === 'point') {
        data.element.animate({
          x1: {
            begin: seq * delays,
            dur: durations,
            from: data.x - 10,
            to: data.x,
            easing: 'easeOutQuart'
          },
          x2: {
            begin: seq * delays,
            dur: durations,
            from: data.x - 10,
            to: data.x,
            easing: 'easeOutQuart'
          },
          opacity: {
            begin: seq * delays,
            dur: durations,
            from: 0,
            to: 1,
            easing: 'easeOutQuart'
          }
        });
      } else if(data.type === 'grid') {
        // Using data.axis we get x or y which we can use to construct our animation definition objects
        var pos1Animation = {
          begin: seq * delays,
          dur: durations,
          from: data[data.axis.units.pos + '1'] - 30,
          to: data[data.axis.units.pos + '1'],
          easing: 'easeOutQuart'
        };

        var pos2Animation = {
          begin: seq * delays,
          dur: durations,
          from: data[data.axis.units.pos + '2'] - 100,
          to: data[data.axis.units.pos + '2'],
          easing: 'easeOutQuart'
        };

        var animations = {};
        animations[data.axis.units.pos + '1'] = pos1Animation;
        animations[data.axis.units.pos + '2'] = pos2Animation;
        animations['opacity'] = {
          begin: seq * delays,
          dur: durations,
          from: 0,
          to: 1,
          easing: 'easeOutQuart'
        };

        data.element.animate(animations);
      }
    });

    // For the sake of the example we update the chart every time it's created with a delay of 10 seconds
    chart.on('created', function() {
      if(window.__exampleAnimateTimeout) {
        clearTimeout(window.__exampleAnimateTimeout);
        window.__exampleAnimateTimeout = null;
      }
      window.__exampleAnimateTimeout = setTimeout(chart.update.bind(chart), 12000);
    });


  });

})(window, document, window.jQuery);

/**=========================================================
 * Module: clear-storage.js
 * Removes a key from the browser storage via element click
 =========================================================*/

(function($, window, document){
  'use strict';

  var Selector = '[data-reset-key]';

  $(document).on('click', Selector, function (e) {
      e.preventDefault();
      var key = $(this).data('resetKey');
      
      if(key) {
        $.localStorage.remove(key);
        // reload the page
        window.location.reload();
      }
      else {
        $.error('No storage key specified for reset.');
      }
  });

}(jQuery, window, document));

// Color picker
// -----------------------------------

(function(window, document, $, undefined){

  $(function(){

    if(!$.fn.colorpicker) return;

    $('.demo-colorpicker').colorpicker();

    $('#demo_selectors').colorpicker({
      colorSelectors: {
        'default': '#777777',
        'primary': APP_COLORS['primary'],
        'success': APP_COLORS['success'],
        'info':    APP_COLORS['info'],
        'warning': APP_COLORS['warning'],
        'danger':  APP_COLORS['danger']
      }
    });

  });

})(window, document, window.jQuery);

// GLOBAL CONSTANTS
// ----------------------------------- 


(function(window, document, $, undefined){

  window.APP_COLORS = {
    'primary':                '#5d9cec',
    'success':                '#27c24c',
    'info':                   '#23b7e5',
    'warning':                '#ff902b',
    'danger':                 '#f05050',
    'inverse':                '#131e26',
    'green':                  '#37bc9b',
    'pink':                   '#f532e5',
    'purple':                 '#7266ba',
    'dark':                   '#3a3f51',
    'yellow':                 '#fad732',
    'gray-darker':            '#232735',
    'gray-dark':              '#3a3f51',
    'gray':                   '#dde6e9',
    'gray-light':             '#e4eaec',
    'gray-lighter':           '#edf1f2'
  };
  
  window.APP_MEDIAQUERY = {
    'desktopLG':             1200,
    'desktop':                992,
    'tablet':                 768,
    'mobile':                 480
  };

})(window, document, window.jQuery);


// Easypie chart Loader
// -----------------------------------

/**
 * Usage
 * <div class="easypie-chart" data-easypiechart data-percent="X" data-optionName="value"></div>
 */
(function(window, document, $, undefined) {

    $(function() {

        if (!$.fn.easyPieChart) return;

        $('[data-easypiechart]').each(function() {
            var $elem = $(this);
            var options = $elem.data();
            $elem.easyPieChart(options || {});
        });
    });

})(window, document, window.jQuery);

// MARKDOWN DOCS
// ----------------------------------- 


(function(window, document, $, undefined){

  $(function(){

    $('.flatdoc').each(function(){

      Flatdoc.run({
        
        fetcher: Flatdoc.file('documentation/readme.md'),

        // Setup custom element selectors (markup validates)
        root:    '.flatdoc',
        menu:    '.flatdoc-menu',
        title:   '.flatdoc-title',
        content: '.flatdoc-content'

      });

    });


  });

})(window, document, window.jQuery);

// FULLSCREEN
// ----------------------------------- 

(function(window, document, $, undefined){

  if ( typeof screenfull === 'undefined' ) return;

  $(function(){

    var $doc = $(document);
    var $fsToggler = $('[data-toggle-fullscreen]');

    // Not supported under IE
    var ua = window.navigator.userAgent;
    if( ua.indexOf("MSIE ") > 0 || !!ua.match(/Trident.*rv\:11\./) ) {
      $fsToggler.addClass('hide');
    }

    if ( ! $fsToggler.is(':visible') ) // hidden on mobiles or IE
      return;

    $fsToggler.on('click', function (e) {
        e.preventDefault();

        if (screenfull.enabled) {
          
          screenfull.toggle();
          
          // Switch icon indicator
          toggleFSIcon( $fsToggler );

        } else {
          console.log('Fullscreen not enabled');
        }
    });

    if ( screenfull.raw && screenfull.raw.fullscreenchange)
      $doc.on(screenfull.raw.fullscreenchange, function () {
          toggleFSIcon($fsToggler);
      });

    function toggleFSIcon($element) {
      if(screenfull.isFullscreen)
        $element.children('em').removeClass('fa-expand').addClass('fa-compress');
      else
        $element.children('em').removeClass('fa-compress').addClass('fa-expand');
    }

  });

})(window, document, window.jQuery);

/**=========================================================
 * Module: gmap.js
 * Init Google Map plugin
 =========================================================*/

(function($, window, document){
  'use strict';

  // -------------------------
  // Map Style definition
  // -------------------------

  // Custom core styles
  // Get more styles from http://snazzymaps.com/style/29/light-monochrome
  // - Just replace and assign to 'MapStyles' the new style array
  var MapStyles = [{featureType:'water',stylers:[{visibility:'on'},{color:'#bdd1f9'}]},{featureType:'all',elementType:'labels.text.fill',stylers:[{color:'#334165'}]},{featureType:'landscape',stylers:[{color:'#e9ebf1'}]},{featureType:'road.highway',elementType:'geometry',stylers:[{color:'#c5c6c6'}]},{featureType:'road.arterial',elementType:'geometry',stylers:[{color:'#fff'}]},{featureType:'road.local',elementType:'geometry',stylers:[{color:'#fff'}]},{featureType:'transit',elementType:'geometry',stylers:[{color:'#d8dbe0'}]},{featureType:'poi',elementType:'geometry',stylers:[{color:'#cfd5e0'}]},{featureType:'administrative',stylers:[{visibility:'on'},{lightness:33}]},{featureType:'poi.park',elementType:'labels',stylers:[{visibility:'on'},{lightness:20}]},{featureType:'road',stylers:[{color:'#d8dbe0',lightness:20}]}];


  // -------------------------
  // Custom Script
  // -------------------------

  var mapSelector = '[data-gmap]';

  if($.fn.gMap) {
      var gMapRefs = [];
      
      $(mapSelector).each(function(){
          
          var $this   = $(this),
              addresses = $this.data('address') && $this.data('address').split(';'),
              titles    = $this.data('title') && $this.data('title').split(';'),
              zoom      = $this.data('zoom') || 14,
              maptype   = $this.data('maptype') || 'ROADMAP', // or 'TERRAIN'
              markers   = [];

          if(addresses) {
            for(var a in addresses)  {
                if(typeof addresses[a] == 'string') {
                    markers.push({
                        address:  addresses[a],
                        html:     (titles && titles[a]) || '',
                        popup:    true   /* Always popup */
                      });
                }
            }

            var options = {
                controls: {
                       panControl:         true,
                       zoomControl:        true,
                       mapTypeControl:     true,
                       scaleControl:       true,
                       streetViewControl:  true,
                       overviewMapControl: true
                   },
                scrollwheel: false,
                maptype: maptype,
                markers: markers,
                zoom: zoom
                // More options https://github.com/marioestrada/jQuery-gMap
            };

            var gMap = $this.gMap(options);

            var ref = gMap.data('gMap.reference');
            // save in the map references list
            gMapRefs.push(ref);

            // set the styles
            if($this.data('styled') !== undefined) {
              
              ref.setOptions({
                styles: MapStyles
              });

            }
          }

      }); //each
  }

}(jQuery, window, document));

/**=========================================================
 * Module: Image Cropper
 =========================================================*/

(function(window, document, $, undefined) {

    $(function() {

        if(! $.fn.cropper ) return;

        var $image = $('.img-container > img'),
            $dataX = $('#dataX'),
            $dataY = $('#dataY'),
            $dataHeight = $('#dataHeight'),
            $dataWidth = $('#dataWidth'),
            $dataRotate = $('#dataRotate'),
            options = {
                // data: {
                //   x: 420,
                //   y: 60,
                //   width: 640,
                //   height: 360
                // },
                // strict: false,
                // responsive: false,
                // checkImageOrigin: false

                // modal: false,
                // guides: false,
                // highlight: false,
                // background: false,

                // autoCrop: false,
                // autoCropArea: 0.5,
                // dragCrop: false,
                // movable: false,
                // rotatable: false,
                // zoomable: false,
                // touchDragZoom: false,
                // mouseWheelZoom: false,
                // cropBoxMovable: false,
                // cropBoxResizable: false,
                // doubleClickToggle: false,

                // minCanvasWidth: 320,
                // minCanvasHeight: 180,
                // minCropBoxWidth: 160,
                // minCropBoxHeight: 90,
                // minContainerWidth: 320,
                // minContainerHeight: 180,

                // build: null,
                // built: null,
                // dragstart: null,
                // dragmove: null,
                // dragend: null,
                // zoomin: null,
                // zoomout: null,

                aspectRatio: 16 / 9,
                preview: '.img-preview',
                crop: function(data) {
                    $dataX.val(Math.round(data.x));
                    $dataY.val(Math.round(data.y));
                    $dataHeight.val(Math.round(data.height));
                    $dataWidth.val(Math.round(data.width));
                    $dataRotate.val(Math.round(data.rotate));
                }
            };

        $image.on({
            'build.cropper': function(e) {
                console.log(e.type);
            },
            'built.cropper': function(e) {
                console.log(e.type);
            },
            'dragstart.cropper': function(e) {
                console.log(e.type, e.dragType);
            },
            'dragmove.cropper': function(e) {
                console.log(e.type, e.dragType);
            },
            'dragend.cropper': function(e) {
                console.log(e.type, e.dragType);
            },
            'zoomin.cropper': function(e) {
                console.log(e.type);
            },
            'zoomout.cropper': function(e) {
                console.log(e.type);
            },
            'change.cropper': function(e) {
                console.log(e.type);
            }
        }).cropper(options);


        // Methods
        $(document.body).on('click', '[data-method]', function() {
            var data = $(this).data(),
                $target,
                result;

            if (!$image.data('cropper')) {
                return;
            }

            if (data.method) {
                data = $.extend({}, data); // Clone a new one

                if (typeof data.target !== 'undefined') {
                    $target = $(data.target);

                    if (typeof data.option === 'undefined') {
                        try {
                            data.option = JSON.parse($target.val());
                        } catch (e) {
                            console.log(e.message);
                        }
                    }
                }

                result = $image.cropper(data.method, data.option);

                if (data.method === 'getCroppedCanvas') {
                    $('#getCroppedCanvasModal').modal().find('.modal-body').html(result);
                }

                if ($.isPlainObject(result) && $target) {
                    try {
                        $target.val(JSON.stringify(result));
                    } catch (e) {
                        console.log(e.message);
                    }
                }

            }
        }).on('keydown', function(e) {

            if (!$image.data('cropper')) {
                return;
            }

            switch (e.which) {
                case 37:
                    e.preventDefault();
                    $image.cropper('move', -1, 0);
                    break;

                case 38:
                    e.preventDefault();
                    $image.cropper('move', 0, -1);
                    break;

                case 39:
                    e.preventDefault();
                    $image.cropper('move', 1, 0);
                    break;

                case 40:
                    e.preventDefault();
                    $image.cropper('move', 0, 1);
                    break;
            }

        });


        // Import image
        var $inputImage = $('#inputImage'),
            URL = window.URL || window.webkitURL,
            blobURL;

        if (URL) {
            $inputImage.change(function() {
                var files = this.files,
                    file;

                if (!$image.data('cropper')) {
                    return;
                }

                if (files && files.length) {
                    file = files[0];

                    if (/^image\/\w+$/.test(file.type)) {
                        blobURL = URL.createObjectURL(file);
                        $image.one('built.cropper', function() {
                            URL.revokeObjectURL(blobURL); // Revoke when load complete
                        }).cropper('reset').cropper('replace', blobURL);
                        $inputImage.val('');
                    } else {
                        alert('Please choose an image file.');
                    }
                }
            });
        } else {
            $inputImage.parent().remove();
        }


        // Options
        $('.docs-options :checkbox').on('change', function() {
            var $this = $(this);

            if (!$image.data('cropper')) {
                return;
            }

            options[$this.val()] = $this.prop('checked');
            $image.cropper('destroy').cropper(options);
        });


        // Tooltips
        $('[data-toggle="tooltip"]').tooltip();

    });

})(window, document, window.jQuery);
// LOAD CUSTOM CSS
// ----------------------------------- 

(function(window, document, $, undefined){

  $(function(){

    $('[data-load-css]').on('click', function (e) {
        
      var element = $(this);

      if(element.is('a'))
        e.preventDefault();
      
      var uri = element.data('loadCss'),
          link;

      if(uri) {
        link = createLink(uri);
        if ( !link ) {
          $.error('Error creating stylesheet link element.');
        }
      }
      else {
        $.error('No stylesheet location defined.');
      }

    });
  });

  function createLink(uri) {
    var linkId = 'autoloaded-stylesheet',
        oldLink = $('#'+linkId).attr('id', linkId + '-old');

    $('head').append($('<link/>').attr({
      'id':   linkId,
      'rel':  'stylesheet',
      'href': uri
    }));

    if( oldLink.length ) {
      oldLink.remove();
    }

    return $('#'+linkId);
  }


})(window, document, window.jQuery);

// TRANSLATION
// ----------------------------------- 

(function(window, document, $, undefined){

  var preferredLang = 'en';
  var pathPrefix    = 'i18n'; // folder of json files
  var packName      = 'site';
  var storageKey    = 'jq-appLang';

  $(function(){

    if ( ! $.fn.localize ) return;

    // detect saved language or use default
    var currLang = $.localStorage.get(storageKey) || preferredLang;
    // set initial options
    var opts = {
        language: currLang,
        pathPrefix: pathPrefix,
        callback: function(data, defaultCallback){
          $.localStorage.set(storageKey, currLang); // save the language
          defaultCallback(data);
        }
      };

    // Set initial language
    setLanguage(opts);

    // Listen for changes
    $('[data-set-lang]').on('click', function(){

      currLang = $(this).data('setLang');

      if ( currLang ) {
        
        opts.language = currLang;

        setLanguage(opts);

        activateDropdown($(this));
      }

    });
    

    function setLanguage(options) {
      $("[data-localize]").localize(packName, options);
    }

    // Set the current clicked text as the active dropdown text
    function activateDropdown(elem) {
      var menu = elem.parents('.dropdown-menu');
      if ( menu.length ) {
        var toggle = menu.prev('button, a');
        toggle.text ( elem.text() );
      }
    }

  });

})(window, document, window.jQuery);

// JVECTOR MAP 
// ----------------------------------- 

(function(window, document, $, undefined){

  window.defaultColors = {
      markerColor:  '#23b7e5',      // the marker points
      bgColor:      'transparent',      // the background
      scaleColors:  ['#878c9a'],    // the color of the region in the serie
      regionFill:   '#bbbec6'       // the base region color
  };

  window.VectorMap = function(element, seriesData, markersData) {
    
    if ( ! element || !element.length) return;

    var attrs       = element.data(),
        mapHeight   = attrs.height || '300',
        options     = {
          markerColor:  attrs.markerColor  || defaultColors.markerColor,
          bgColor:      attrs.bgColor      || defaultColors.bgColor,
          scale:        attrs.scale        || 1,
          scaleColors:  attrs.scaleColors  || defaultColors.scaleColors,
          regionFill:   attrs.regionFill   || defaultColors.regionFill,
          mapName:      attrs.mapName      || 'world_mill_en'
        };
    
    element.css('height', mapHeight);
    
    init( element , options, seriesData, markersData);
    
    function init($element, opts, series, markers) {
        
        $element.vectorMap({
          map:             opts.mapName,
          backgroundColor: opts.bgColor,
          zoomMin:         1,
          zoomMax:         8,
          zoomOnScroll:    false,
          regionStyle: {
            initial: {
              'fill':           opts.regionFill,
              'fill-opacity':   1,
              'stroke':         'none',
              'stroke-width':   1.5,
              'stroke-opacity': 1
            },
            hover: {
              'fill-opacity': 0.8
            },
            selected: {
              fill: 'blue'
            },
            selectedHover: {
            }
          },
          focusOn:{ x:0.4, y:0.6, scale: opts.scale},
          markerStyle: {
            initial: {
              fill: opts.markerColor,
              stroke: opts.markerColor
            }
          },
          onRegionLabelShow: function(e, el, code) {
            if ( series && series[code] )
              el.html(el.html() + ': ' + series[code] + ' visitors');
          },
          markers: markers,
          series: {
              regions: [{
                  values: series,
                  scale: opts.scaleColors,
                  normalizeFunction: 'polynomial'
              }]
          },
        });

      }// end init
  };

})(window, document, window.jQuery);

// Morris
// ----------------------------------- 

(function(window, document, $, undefined){

  $(function(){

    if ( typeof Morris === 'undefined' ) return;

    var chartdata = [
        { y: "2006", a: 100, b: 90 },
        { y: "2007", a: 75,  b: 65 },
        { y: "2008", a: 50,  b: 40 },
        { y: "2009", a: 75,  b: 65 },
        { y: "2010", a: 50,  b: 40 },
        { y: "2011", a: 75,  b: 65 },
        { y: "2012", a: 100, b: 90 }
    ];

    var donutdata = [
      {label: "Download Sales", value: 12},
      {label: "In-Store Sales",value: 30},
      {label: "Mail-Order Sales", value: 20}
    ];

    // Line Chart
    // ----------------------------------- 

    new Morris.Line({
      element: 'morris-line',
      data: chartdata,
      xkey: 'y',
      ykeys: ["a", "b"],
      labels: ["Serie A", "Serie B"],
      lineColors: ["#31C0BE", "#7a92a3"],
      resize: true
    });

    // Donut Chart
    // ----------------------------------- 
    new Morris.Donut({
      element: 'morris-donut',
      data: donutdata,
      colors: [ '#f05050', '#fad732', '#ff902b' ],
      resize: true
    });

    // Bar Chart
    // ----------------------------------- 
    new Morris.Bar({
      element: 'morris-bar',
      data: chartdata,
      xkey: 'y',
      ykeys: ["a", "b"],
      labels: ["Series A", "Series B"],
      xLabelMargin: 2,
      barColors: [ '#23b7e5', '#f05050' ],
      resize: true
    });

    // Area Chart
    // ----------------------------------- 
    new Morris.Area({
      element: 'morris-area',
      data: chartdata,
      xkey: 'y',
      ykeys: ["a", "b"],
      labels: ["Serie A", "Serie B"],
      lineColors: [ '#7266ba', '#23b7e5' ],
      resize: true
    });

  });

})(window, document, window.jQuery);

// NAVBAR SEARCH
// -----------------------------------


(function(window, document, $, undefined){

  $(function(){

    var navSearch = new navbarSearchInput();

    // Open search input
    var $searchOpen = $('[data-search-open]');

    $searchOpen
      .on('click', function (e) { e.stopPropagation(); })
      .on('click', navSearch.toggle);

    // Close search input
    var $searchDismiss = $('[data-search-dismiss]');
    var inputSelector = '.navbar-form input[type="text"]';

    $(inputSelector)
      .on('click', function (e) { e.stopPropagation(); })
      .on('keyup', function(e) {
        if (e.keyCode == 27) // ESC
          navSearch.dismiss();
      });

    // click anywhere closes the search
    $(document).on('click', navSearch.dismiss);
    // dismissable options
    $searchDismiss
      .on('click', function (e) { e.stopPropagation(); })
      .on('click', navSearch.dismiss);

  });

  var navbarSearchInput = function() {
    var navbarFormSelector = 'form.navbar-form';
    return {
      toggle: function() {

        var navbarForm = $(navbarFormSelector);

        navbarForm.toggleClass('open');

        var isOpen = navbarForm.hasClass('open');

        navbarForm.find('input')[isOpen ? 'focus' : 'blur']();

      },

      dismiss: function() {
        $(navbarFormSelector)
          .removeClass('open')      // Close control
          .find('input[type="text"]').blur() // remove focus
          // .val('')                    // Empty input
          ;
      }
    };

  }

})(window, document, window.jQuery);
/**=========================================================
 * Module: notify.js
 * Create toggleable notifications that fade out automatically.
 * Based on Notify addon from UIKit (http://getuikit.com/docs/addons_notify.html)
 * [data-toggle="notify"]
 * [data-options="options in json format" ]
 =========================================================*/

(function($, window, document){
  'use strict';

  var Selector = '[data-notify]',
      autoloadSelector = '[data-onload]',
      doc = $(document);


  $(function() {

    $(Selector).each(function(){

      var $this  = $(this),
          onload = $this.data('onload');

      if(onload !== undefined) {
        setTimeout(function(){
          notifyNow($this);
        }, 800);
      }

      $this.on('click', function (e) {
        e.preventDefault();
        notifyNow($this);
      });

    });

  });

  function notifyNow($element) {
      var message = $element.data('message'),
          options = $element.data('options');

      if(!message)
        $.error('Notify: No message specified');
     
      $.notify(message, options || {});
  }


}(jQuery, window, document));


/**
 * Notify Addon definition as jQuery plugin
 * Adapted version to work with Bootstrap classes
 * More information http://getuikit.com/docs/addons_notify.html
 */

(function($, window, document){

    var containers = {},
        messages   = {},

        notify     =  function(options){

            if ($.type(options) == 'string') {
                options = { message: options };
            }

            if (arguments[1]) {
                options = $.extend(options, $.type(arguments[1]) == 'string' ? {status:arguments[1]} : arguments[1]);
            }

            return (new Message(options)).show();
        },
        closeAll  = function(group, instantly){
            if(group) {
                for(var id in messages) { if(group===messages[id].group) messages[id].close(instantly); }
            } else {
                for(var id in messages) { messages[id].close(instantly); }
            }
        };

    var Message = function(options){

        var $this = this;

        this.options = $.extend({}, Message.defaults, options);

        this.uuid    = "ID"+(new Date().getTime())+"RAND"+(Math.ceil(Math.random() * 100000));
        this.element = $([
            // alert-dismissable enables bs close icon
            '<div class="uk-notify-message alert-dismissable">',
                '<a class="close">&times;</a>',
                '<div>'+this.options.message+'</div>',
            '</div>'

        ].join('')).data("notifyMessage", this);

        // status
        if (this.options.status) {
            this.element.addClass('alert alert-'+this.options.status);
            this.currentstatus = this.options.status;
        }

        this.group = this.options.group;

        messages[this.uuid] = this;

        if(!containers[this.options.pos]) {
            containers[this.options.pos] = $('<div class="uk-notify uk-notify-'+this.options.pos+'"></div>').appendTo('body').on("click", ".uk-notify-message", function(){
                $(this).data("notifyMessage").close();
            });
        }
    };


    $.extend(Message.prototype, {

        uuid: false,
        element: false,
        timout: false,
        currentstatus: "",
        group: false,

        show: function() {

            if (this.element.is(":visible")) return;

            var $this = this;

            containers[this.options.pos].show().prepend(this.element);

            var marginbottom = parseInt(this.element.css("margin-bottom"), 10);

            this.element.css({"opacity":0, "margin-top": -1*this.element.outerHeight(), "margin-bottom":0}).animate({"opacity":1, "margin-top": 0, "margin-bottom":marginbottom}, function(){

                if ($this.options.timeout) {

                    var closefn = function(){ $this.close(); };

                    $this.timeout = setTimeout(closefn, $this.options.timeout);

                    $this.element.hover(
                        function() { clearTimeout($this.timeout); },
                        function() { $this.timeout = setTimeout(closefn, $this.options.timeout);  }
                    );
                }

            });

            return this;
        },

        close: function(instantly) {

            var $this    = this,
                finalize = function(){
                    $this.element.remove();

                    if(!containers[$this.options.pos].children().length) {
                        containers[$this.options.pos].hide();
                    }

                    delete messages[$this.uuid];
                };

            if(this.timeout) clearTimeout(this.timeout);

            if(instantly) {
                finalize();
            } else {
                this.element.animate({"opacity":0, "margin-top": -1* this.element.outerHeight(), "margin-bottom":0}, function(){
                    finalize();
                });
            }
        },

        content: function(html){

            var container = this.element.find(">div");

            if(!html) {
                return container.html();
            }

            container.html(html);

            return this;
        },

        status: function(status) {

            if(!status) {
                return this.currentstatus;
            }

            this.element.removeClass('alert alert-'+this.currentstatus).addClass('alert alert-'+status);

            this.currentstatus = status;

            return this;
        }
    });

    Message.defaults = {
        message: "",
        status: "normal",
        timeout: 5000,
        group: null,
        pos: 'top-center'
    };


    $["notify"]          = notify;
    $["notify"].message  = Message;
    $["notify"].closeAll = closeAll;

    return notify;

}(jQuery, window, document));

// NOW TIMER
// ----------------------------------- 

(function(window, document, $, undefined){

  $(function(){

    $('[data-now]').each(function(){
      var element = $(this),
          format = element.data('format');

      function updateTime() {
        var dt = moment( new Date() ).format(format);
        element.text(dt);
      }

      updateTime();
      setInterval(updateTime, 1000);
    
    });
  });

})(window, document, window.jQuery);

/**=========================================================
 * Module: panel-tools.js
 * Dismiss panels
 * [data-tool="panel-dismiss"]
 *
 * Requires animo.js
 =========================================================*/
(function($, window, document){
  'use strict';
  
  var panelSelector = '[data-tool="panel-dismiss"]',
      removeEvent   = 'panel.remove',
      removedEvent  = 'panel.removed';

  $(document).on('click', panelSelector, function () {
    
    // find the first parent panel
    var parent = $(this).closest('.panel');
    var deferred = new $.Deferred();

    // Trigger the event and finally remove the element
    parent.trigger(removeEvent, [parent, deferred]);
    // needs resolve() to be called
    deferred.done(removeElement);

    function removeElement() {
      if($.support.animation) {
        parent.animo({animation: 'bounceOut'}, destroyPanel);
      }
      else destroyPanel();
    }

    function destroyPanel() {
      var col = parent.parent();
      
      $.when(parent.trigger(removedEvent, [parent]))
       .done(function(){
          parent.remove();
          // remove the parent if it is a row and is empty and not a sortable (portlet)
          col
            .trigger(removedEvent) // An event to catch when the panel has been removed from DOM
            .filter(function() {
            var el = $(this);
            return (el.is('[class*="col-"]:not(.sortable)') && el.children('*').length === 0);
          }).remove();
       });

      

    }

  });

}(jQuery, window, document));


/**
 * Collapse panels
 * [data-tool="panel-collapse"]
 *
 * Also uses browser storage to keep track
 * of panels collapsed state
 */
(function($, window, document) {
  'use strict';
  var panelSelector = '[data-tool="panel-collapse"]',
      storageKeyName = 'jq-panelState';

  // Prepare the panel to be collapsable and its events
  $(panelSelector).each(function() {
    // find the first parent panel
    var $this        = $(this),
        parent       = $this.closest('.panel'),
        wrapper      = parent.find('.panel-wrapper'),
        collapseOpts = {toggle: false},
        iconElement  = $this.children('em'),
        panelId      = parent.attr('id');
    
    // if wrapper not added, add it
    // we need a wrapper to avoid jumping due to the paddings
    if( ! wrapper.length) {
      wrapper =
        parent.children('.panel-heading').nextAll() //find('.panel-body, .panel-footer')
          .wrapAll('<div/>')
          .parent()
          .addClass('panel-wrapper');
      collapseOpts = {};
    }

    // Init collapse and bind events to switch icons
    wrapper
      .collapse(collapseOpts)
      .on('hide.bs.collapse', function() {
        setIconHide( iconElement );
        savePanelState( panelId, 'hide' );
        wrapper.prev('.panel-heading').addClass('panel-heading-collapsed');
      })
      .on('show.bs.collapse', function() {
        setIconShow( iconElement );
        savePanelState( panelId, 'show' );
        wrapper.prev('.panel-heading').removeClass('panel-heading-collapsed');
      });

    // Load the saved state if exists
    var currentState = loadPanelState( panelId );
    if(currentState) {
      setTimeout(function() { wrapper.collapse( currentState ); }, 50);
      savePanelState( panelId, currentState );
    }

  });

  // finally catch clicks to toggle panel collapse
  $(document).on('click', panelSelector, function () {
    
    var parent = $(this).closest('.panel');
    var wrapper = parent.find('.panel-wrapper');

    wrapper.collapse('toggle');

  });

  /////////////////////////////////////////////
  // Common use functions for panel collapse //
  /////////////////////////////////////////////
  function setIconShow(iconEl) {
    iconEl.removeClass('fa-plus').addClass('fa-minus');
  }

  function setIconHide(iconEl) {
    iconEl.removeClass('fa-minus').addClass('fa-plus');
  }

  function savePanelState(id, state) {
    var data = $.localStorage.get(storageKeyName);
    if(!data) { data = {}; }
    data[id] = state;
    $.localStorage.set(storageKeyName, data);
  }

  function loadPanelState(id) {
    var data = $.localStorage.get(storageKeyName);
    if(data) {
      return data[id] || false;
    }
  }


}(jQuery, window, document));


/**
 * Refresh panels
 * [data-tool="panel-refresh"]
 * [data-spinner="standard"]
 */
(function($, window, document){
  'use strict';
  var panelSelector  = '[data-tool="panel-refresh"]',
      refreshEvent   = 'panel.refresh',
      whirlClass     = 'whirl',
      defaultSpinner = 'standard';

  // method to clear the spinner when done
  function removeSpinner(){
    this.removeClass(whirlClass);
  }

  // catch clicks to toggle panel refresh
  $(document).on('click', panelSelector, function () {
      var $this   = $(this),
          panel   = $this.parents('.panel').eq(0),
          spinner = $this.data('spinner') || defaultSpinner
          ;

      // start showing the spinner
      panel.addClass(whirlClass + ' ' + spinner);

      // attach as public method
      panel.removeSpinner = removeSpinner;

      // Trigger the event and send the panel object
      $this.trigger(refreshEvent, [panel]);

  });


}(jQuery, window, document));

/**=========================================================
 * Module: play-animation.js
 * Provides a simple way to run animation with a trigger
 * Targeted elements must have 
 *   [data-animate"]
 *   [data-target="Target element affected by the animation"] 
 *   [data-play="Animation name (http://daneden.github.io/animate.css/)"]
 *
 * Requires animo.js
 =========================================================*/
 
(function($, window, document){
  'use strict';

  var Selector = '[data-animate]';

  $(function() {
    
    var $scroller = $(window).add('body, .wrapper');

    // Parse animations params and attach trigger to scroll
    $(Selector).each(function() {
      var $this     = $(this),
          offset    = $this.data('offset'),
          delay     = $this.data('delay')     || 100, // milliseconds
          animation = $this.data('play')      || 'bounce';
      
      if(typeof offset !== 'undefined') {
        
        // test if the element starts visible
        testAnimation($this);
        // test on scroll
        $scroller.scroll(function(){
          testAnimation($this);
        });

      }

      // Test an element visibilty and trigger the given animation
      function testAnimation(element) {
          if ( !element.hasClass('anim-running') &&
              $.Utils.isInView(element, {topoffset: offset})) {
          element
            .addClass('anim-running');

          setTimeout(function() {
            element
              .addClass('anim-done')
              .animo( { animation: animation, duration: 0.7} );
          }, delay);

        }
      }

    });

    // Run click triggered animations
    $(document).on('click', Selector, function() {

      var $this     = $(this),
          targetSel = $this.data('target'),
          animation = $this.data('play') || 'bounce',
          target    = $(targetSel);

      if(target && target.length) {
        target.animo( { animation: animation } );
      }
      
    });

  });

}(jQuery, window, document));

/**=========================================================
 * Module: portlet.js
 * Drag and drop any panel to change its position
 * The Selector should could be applied to any object that contains
 * panel, so .col-* element are ideal.
 =========================================================*/

(function($, window, document){
  'use strict';

  // Component is optional
  if(!$.fn.sortable) return;

  var Selector = '[data-toggle="portlet"]',
      storageKeyName = 'jq-portletState';

  $(function(){

    $( Selector ).sortable({
      connectWith:          Selector,
      items:                'div.panel',
      handle:               '.portlet-handler',
      opacity:              0.7,
      placeholder:          'portlet box-placeholder',
      cancel:               '.portlet-cancel',
      forcePlaceholderSize: true,
      iframeFix:            false,
      tolerance:            'pointer',
      helper:               'original',
      revert:               200,
      forceHelperSize:      true,
      update:               savePortletOrder,
      create:               loadPortletOrder
    })
    // optionally disables mouse selection
    //.disableSelection()
    ;

  });

  function savePortletOrder(event, ui) {
    
    var data = $.localStorage.get(storageKeyName);
    
    if(!data) { data = {}; }

    data[this.id] = $(this).sortable('toArray');

    if(data) {
      $.localStorage.set(storageKeyName, data);
    }
    
  }

  function loadPortletOrder() {
    
    var data = $.localStorage.get(storageKeyName);

    if(data) {
      
      var porletId = this.id,
          panels   = data[porletId];

      if(panels) {
        var portlet = $('#'+porletId);
        
        $.each(panels, function(index, value) {
           $('#'+value).appendTo(portlet);
        });
      }

    }

  }

}(jQuery, window, document));


// Rickshaw
// ----------------------------------- 

(function(window, document, $, undefined){

  $(function(){
    
    if ( typeof Rickshaw === 'undefined' ) return;

    var seriesData = [ [], [], [] ];
    var random = new Rickshaw.Fixtures.RandomData(150);

    for (var i = 0; i < 150; i++) {
      random.addData(seriesData);
    }

    var series1 = [
      {
        color: "#c05020",
        data: seriesData[0],
        name: 'New York'
      }, {
        color: "#30c020",
        data: seriesData[1],
        name: 'London'
      }, {
        color: "#6060c0",
        data: seriesData[2],
        name: 'Tokyo'
      }
    ];

    var graph1 = new Rickshaw.Graph( {
        element: document.querySelector("#rickshaw1"), 
        series:series1,
        renderer: 'area'
    });
     
    graph1.render();


    // Graph 2
    // ----------------------------------- 

    var graph2 = new Rickshaw.Graph( {
      element: document.querySelector("#rickshaw2"),
      renderer: 'area',
      stroke: true,
      series: [ {
        data: [ { x: 0, y: 40 }, { x: 1, y: 49 }, { x: 2, y: 38 }, { x: 3, y: 30 }, { x: 4, y: 32 } ],
        color: '#f05050'
      }, {
        data: [ { x: 0, y: 40 }, { x: 1, y: 49 }, { x: 2, y: 38 }, { x: 3, y: 30 }, { x: 4, y: 32 } ],
        color: '#fad732'
      } ]
    } );

    graph2.render();

    // Graph 3
    // ----------------------------------- 


    var graph3 = new Rickshaw.Graph({
      element: document.querySelector("#rickshaw3"),
      renderer: 'line',
      series: [{
        data: [ { x: 0, y: 40 }, { x: 1, y: 49 }, { x: 2, y: 38 }, { x: 3, y: 30 }, { x: 4, y: 32 } ],
        color: '#7266ba'
      }, {
        data: [ { x: 0, y: 20 }, { x: 1, y: 24 }, { x: 2, y: 19 }, { x: 3, y: 15 }, { x: 4, y: 16 } ],
        color: '#23b7e5'
      }]
    });
    graph3.render();


    // Graph 4
    // ----------------------------------- 


    var graph4 = new Rickshaw.Graph( {
      element: document.querySelector("#rickshaw4"),
      renderer: 'bar',
      series: [ 
        {
          data: [ { x: 0, y: 40 }, { x: 1, y: 49 }, { x: 2, y: 38 }, { x: 3, y: 30 }, { x: 4, y: 32 } ],
          color: '#fad732'
        }, {
          data: [ { x: 0, y: 20 }, { x: 1, y: 24 }, { x: 2, y: 19 }, { x: 3, y: 15 }, { x: 4, y: 16 } ],
          color: '#ff902b'

      } ]
    } );
    graph4.render();


  });

})(window, document, window.jQuery);

// Select2
// -----------------------------------

(function(window, document, $, undefined){

  $(function(){

    if ( !$.fn.select2 ) return;

    // Select 2

    $('#select2-1').select2({
        theme: 'bootstrap'
    });
    $('#select2-2').select2({
        theme: 'bootstrap'
    });
    $('#select2-3').select2({
        theme: 'bootstrap'
    });
    $('#select2-4').select2({
        placeholder: 'Select a state',
        allowClear: true,
        theme: 'bootstrap'
    });

  });

})(window, document, window.jQuery);


// SIDEBAR
// -----------------------------------


(function(window, document, $, undefined){

  var $win;
  var $html;
  var $body;
  var $sidebar;
  var mq;

  $(function(){

    $win     = $(window);
    $html    = $('html');
    $body    = $('body');
    $sidebar = $('.sidebar');
    mq       = APP_MEDIAQUERY;

    // AUTOCOLLAPSE ITEMS
    // -----------------------------------

    var sidebarCollapse = $sidebar.find('.collapse');
    sidebarCollapse.on('show.bs.collapse', function(event){

      event.stopPropagation();
      if ( $(this).parents('.collapse').length === 0 )
        sidebarCollapse.filter('.in').collapse('hide');

    });

    // SIDEBAR ACTIVE STATE
    // -----------------------------------

    // Find current active item
    var currentItem = $('.sidebar .active').parents('li');

    // hover mode don't try to expand active collapse
    if ( ! useAsideHover() )
      currentItem
        .addClass('active')     // activate the parent
        .children('.collapse')  // find the collapse
        .collapse('show');      // and show it

    // remove this if you use only collapsible sidebar items
    $sidebar.find('li > a + ul').on('show.bs.collapse', function (e) {
      if( useAsideHover() ) e.preventDefault();
    });

    // SIDEBAR COLLAPSED ITEM HANDLER
    // -----------------------------------


    var eventName = isTouch() ? 'click' : 'mouseenter' ;
    var subNav = $();
    $sidebar.on( eventName, '.nav > li', function() {

      if( isSidebarCollapsed() || useAsideHover() ) {

        subNav.trigger('mouseleave');
        subNav = toggleMenuItem( $(this) );

        // Used to detect click and touch events outside the sidebar
        sidebarAddBackdrop();
      }

    });

    var sidebarAnyclickClose = $sidebar.data('sidebarAnyclickClose');

    // Allows to close
    if ( typeof sidebarAnyclickClose !== 'undefined' ) {

      $('.wrapper').on('click.sidebar', function(e){
        // don't check if sidebar not visible
        if( ! $body.hasClass('aside-toggled')) return;

        var $target = $(e.target);
        if( ! $target.parents('.aside').length && // if not child of sidebar
            ! $target.is('#user-block-toggle') && // user block toggle anchor
            ! $target.parent().is('#user-block-toggle') // user block toggle icon
          ) {
                $body.removeClass('aside-toggled');
        }

      });
    }

  });

  function sidebarAddBackdrop() {
    var $backdrop = $('<div/>', { 'class': 'dropdown-backdrop'} );
    $backdrop.insertAfter('.aside').on("click mouseenter", function () {
      removeFloatingNav();
    });
  }

  // Open the collapse sidebar submenu items when on touch devices
  // - desktop only opens on hover
  function toggleTouchItem($element){
    $element
      .siblings('li')
      .removeClass('open')
      .end()
      .toggleClass('open');
  }

  // Handles hover to open items under collapsed menu
  // -----------------------------------
  function toggleMenuItem($listItem) {

    removeFloatingNav();

    var ul = $listItem.children('ul');

    if( !ul.length ) return $();
    if( $listItem.hasClass('open') ) {
      toggleTouchItem($listItem);
      return $();
    }

    var $aside = $('.aside');
    var $asideInner = $('.aside-inner'); // for top offset calculation
    // float aside uses extra padding on aside
    var mar = parseInt( $asideInner.css('padding-top'), 0) + parseInt( $aside.css('padding-top'), 0);

    var subNav = ul.clone().appendTo( $aside );

    toggleTouchItem($listItem);

    var itemTop = ($listItem.position().top + mar) - $sidebar.scrollTop();
    var vwHeight = $win.height();

    subNav
      .addClass('nav-floating')
      .css({
        position: isFixed() ? 'fixed' : 'absolute',
        top:      itemTop,
        bottom:   (subNav.outerHeight(true) + itemTop > vwHeight) ? 0 : 'auto'
      });

    subNav.on('mouseleave', function() {
      toggleTouchItem($listItem);
      subNav.remove();
    });

    return subNav;
  }

  function removeFloatingNav() {
    $('.sidebar-subnav.nav-floating').remove();
    $('.dropdown-backdrop').remove();
    $('.sidebar li.open').removeClass('open');
  }

  function isTouch() {
    return $html.hasClass('touch');
  }
  function isSidebarCollapsed() {
    return $body.hasClass('aside-collapsed') || $body.hasClass('aside-collapsed-text');
  }
  function isSidebarToggled() {
    return $body.hasClass('aside-toggled');
  }
  function isMobile() {
    return $win.width() < mq.tablet;
  }
  function isFixed(){
    return $body.hasClass('layout-fixed');
  }
  function useAsideHover() {
    return $body.hasClass('aside-hover');
  }

})(window, document, window.jQuery);
// SKYCONS
// ----------------------------------- 

(function(window, document, $, undefined){

  $(function(){

    $('[data-skycon]').each(function(){
      var element = $(this),
          skycons = new Skycons({'color': (element.data('color') || 'white')});
      
      element.html('<canvas width="' + element.data('width') + '" height="' + element.data('height') + '"></canvas>');

      skycons.add(element.children()[0], element.data('skycon'));

      skycons.play();
    });

  });

})(window, document, window.jQuery);
// SLIMSCROLL
// ----------------------------------- 

(function(window, document, $, undefined){

  $(function(){

    $('[data-scrollable]').each(function(){

      var element = $(this),
          defaultHeight = 250;
      
      element.slimScroll({
          height: (element.data('height') || defaultHeight)
      });
      
    });
  });

})(window, document, window.jQuery);

// SPARKLINE
// ----------------------------------- 

(function(window, document, $, undefined){

  $(function(){

    $('[data-sparkline]').each(initSparkLine);

    function initSparkLine() {
      var $element = $(this),
          options = $element.data(),
          values  = options.values && options.values.split(',');

      options.type = options.type || 'bar'; // default chart is bar
      options.disableHiddenCheck = true;

      $element.sparkline(values, options);

      if(options.resize) {
        $(window).resize(function(){
          $element.sparkline(values, options);
        });
      }
    }
  });

})(window, document, window.jQuery);

// Sweet Alert
// ----------------------------------- 

(function(window, document, $, undefined){

  $(function(){

    $('#swal-demo1').on('click', function(e){
      e.preventDefault();
      swal("Here's a message!")
    });


    $('#swal-demo2').on('click', function(e){
      e.preventDefault();
      swal("Here's a message!", "It's pretty, isn't it?")
    });

    $('#swal-demo3').on('click', function(e){
      e.preventDefault();
      swal("Good job!", "You clicked the button!", "success")
    });

    $('#swal-demo4').on('click', function(e){
      e.preventDefault();
      swal({
        title : "Are you sure?",
        text : "You will not be able to recover this imaginary file!",
        type : "warning",
        showCancelButton : true,
        confirmButtonColor : "#DD6B55",
        confirmButtonText : "Yes, delete it!",
        closeOnConfirm : false
      },
        function () {
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
      });

    });

    $('#swal-demo5').on('click', function(e){
      e.preventDefault();
      swal({
        title : "Are you sure?",
        text : "You will not be able to recover this imaginary file!",
        type : "warning",
        showCancelButton : true,
        confirmButtonColor : "#DD6B55",
        confirmButtonText : "Yes, delete it!",
        cancelButtonText : "No, cancel plx!",
        closeOnConfirm : false,
        closeOnCancel : false
      }, function (isConfirm) {
        if (isConfirm) {
          swal("Deleted!", "Your imaginary file has been deleted.", "success");
        } else {
          swal("Cancelled", "Your imaginary file is safe :)", "error");
        }
      });

    });

  });

})(window, document, window.jQuery);

// Custom jQuery
// ----------------------------------- 


(function(window, document, $, undefined){

  $(function(){

    $('[data-check-all]').on('change', function() {
      var $this = $(this),
          index= $this.index() + 1,
          checkbox = $this.find('input[type="checkbox"]'),
          table = $this.parents('table');
      // Make sure to affect only the correct checkbox column
      table.find('tbody > tr > td:nth-child('+index+') input[type="checkbox"]')
        .prop('checked', checkbox[0].checked);

    });

  });

})(window, document, window.jQuery);


// TOGGLE STATE
// -----------------------------------

(function(window, document, $, undefined){

  $(function(){

    var $body = $('body');
        toggle = new StateToggler();

    $('[data-toggle-state]')
      .on('click', function (e) {
        // e.preventDefault();
        e.stopPropagation();
        var element = $(this),
            classname = element.data('toggleState'),
            target = element.data('target'),
            noPersist = (element.attr('data-no-persist') !== undefined);

        // Specify a target selector to toggle classname
        // use body by default
        var $target = target ? $(target) : $body;

        if(classname) {
          if( $target.hasClass(classname) ) {
            $target.removeClass(classname);
            if( ! noPersist)
              toggle.removeState(classname);
          }
          else {
            $target.addClass(classname);
            if( ! noPersist)
              toggle.addState(classname);
          }

        }
        // some elements may need this when toggled class change the content size
        // e.g. sidebar collapsed mode and jqGrid
        $(window).resize();

    });

  });

  // Handle states to/from localstorage
  window.StateToggler = function() {

    var storageKeyName  = 'jq-toggleState';

    // Helper object to check for words in a phrase //
    var WordChecker = {
      hasWord: function (phrase, word) {
        return new RegExp('(^|\\s)' + word + '(\\s|$)').test(phrase);
      },
      addWord: function (phrase, word) {
        if (!this.hasWord(phrase, word)) {
          return (phrase + (phrase ? ' ' : '') + word);
        }
      },
      removeWord: function (phrase, word) {
        if (this.hasWord(phrase, word)) {
          return phrase.replace(new RegExp('(^|\\s)*' + word + '(\\s|$)*', 'g'), '');
        }
      }
    };

    // Return service public methods
    return {
      // Add a state to the browser storage to be restored later
      addState: function(classname){
        var data = $.localStorage.get(storageKeyName);

        if(!data)  {
          data = classname;
        }
        else {
          data = WordChecker.addWord(data, classname);
        }

        $.localStorage.set(storageKeyName, data);
      },

      // Remove a state from the browser storage
      removeState: function(classname){
        var data = $.localStorage.get(storageKeyName);
        // nothing to remove
        if(!data) return;

        data = WordChecker.removeWord(data, classname);

        $.localStorage.set(storageKeyName, data);
      },

      // Load the state string and restore the classlist
      restoreState: function($elem) {
        var data = $.localStorage.get(storageKeyName);

        // nothing to restore
        if(!data) return;
        $elem.addClass(data);
      }

    };
  };

})(window, document, window.jQuery);

// Bootstrap Tour
// ----------------------------------- 

(function(window, document, $, undefined){

  $(function(){

    // Prepare steps
    var tourSteps = [];
    $('.tour-step').each(function(){
      var stepsOptions = $(this).data();
      stepsOptions.element = '#'+this.id;
      tourSteps.push( stepsOptions );
    });

    if ( tourSteps.length ) {
      // Instance the tour
      var tour = new Tour({
          backdrop: true,
          onShown: function(tour) {
            // BootstrapTour is not compatible with z-index based layout
            // so adding position:static for this case makes the browser
            // to ignore the property
            $('.wrapper > section').css({'position': 'static'});
          },
          onHide: function (tour) {
            // finally restore on destroy and reuse the value declared in stylesheet
            $('.wrapper > section').css({'position': ''});
          },
          steps: tourSteps
        });

      // Initialize the tour
      tour.init();

      
      $('#start-tour').on('click', function(){
        // Start the tour
        tour.restart();
      });
    }

  });

})(window, document, window.jQuery);

/**=========================================================
 * Module: trigger-resize.js
 * Triggers a window resize event from any element
 =========================================================*/

(function(window, document, $, undefined) {

    $(function() {
        var element = $('[data-trigger-resize]');
        var value = element.data('triggerResize')
        element.on('click', function() {
            setTimeout(function() {
                // all IE friendly dispatchEvent
                var evt = document.createEvent('UIEvents');
                evt.initUIEvent('resize', true, false, window, 0);
                window.dispatchEvent(evt);
                // modern dispatchEvent way
                // window.dispatchEvent(new Event('resize'));
            }, value || 300);
        });
    });

})(window, document, window.jQuery);
/**=========================================================
 * Module: utils.js
 * jQuery Utility functions library 
 * adapted from the core of UIKit
 =========================================================*/

(function($, window, doc){
    'use strict';
    
    var $html = $("html"), $win = $(window);

    $.support.transition = (function() {

        var transitionEnd = (function() {

            var element = doc.body || doc.documentElement,
                transEndEventNames = {
                    WebkitTransition: 'webkitTransitionEnd',
                    MozTransition: 'transitionend',
                    OTransition: 'oTransitionEnd otransitionend',
                    transition: 'transitionend'
                }, name;

            for (name in transEndEventNames) {
                if (element.style[name] !== undefined) return transEndEventNames[name];
            }
        }());

        return transitionEnd && { end: transitionEnd };
    })();

    $.support.animation = (function() {

        var animationEnd = (function() {

            var element = doc.body || doc.documentElement,
                animEndEventNames = {
                    WebkitAnimation: 'webkitAnimationEnd',
                    MozAnimation: 'animationend',
                    OAnimation: 'oAnimationEnd oanimationend',
                    animation: 'animationend'
                }, name;

            for (name in animEndEventNames) {
                if (element.style[name] !== undefined) return animEndEventNames[name];
            }
        }());

        return animationEnd && { end: animationEnd };
    })();

    $.support.requestAnimationFrame = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.msRequestAnimationFrame || window.oRequestAnimationFrame || function(callback){ window.setTimeout(callback, 1000/60); };
    $.support.touch                 = (
        ('ontouchstart' in window && navigator.userAgent.toLowerCase().match(/mobile|tablet/)) ||
        (window.DocumentTouch && document instanceof window.DocumentTouch)  ||
        (window.navigator['msPointerEnabled'] && window.navigator['msMaxTouchPoints'] > 0) || //IE 10
        (window.navigator['pointerEnabled'] && window.navigator['maxTouchPoints'] > 0) || //IE >=11
        false
    );
    $.support.mutationobserver      = (window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver || null);

    $.Utils = {};

    $.Utils.debounce = function(func, wait, immediate) {
        var timeout;
        return function() {
            var context = this, args = arguments;
            var later = function() {
                timeout = null;
                if (!immediate) func.apply(context, args);
            };
            var callNow = immediate && !timeout;
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
            if (callNow) func.apply(context, args);
        };
    };

    $.Utils.removeCssRules = function(selectorRegEx) {
        var idx, idxs, stylesheet, _i, _j, _k, _len, _len1, _len2, _ref;

        if(!selectorRegEx) return;

        setTimeout(function(){
            try {
              _ref = document.styleSheets;
              for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                stylesheet = _ref[_i];
                idxs = [];
                stylesheet.cssRules = stylesheet.cssRules;
                for (idx = _j = 0, _len1 = stylesheet.cssRules.length; _j < _len1; idx = ++_j) {
                  if (stylesheet.cssRules[idx].type === CSSRule.STYLE_RULE && selectorRegEx.test(stylesheet.cssRules[idx].selectorText)) {
                    idxs.unshift(idx);
                  }
                }
                for (_k = 0, _len2 = idxs.length; _k < _len2; _k++) {
                  stylesheet.deleteRule(idxs[_k]);
                }
              }
            } catch (_error) {}
        }, 0);
    };

    $.Utils.isInView = function(element, options) {

        var $element = $(element);

        if (!$element.is(':visible')) {
            return false;
        }

        var window_left = $win.scrollLeft(),
            window_top  = $win.scrollTop(),
            offset      = $element.offset(),
            left        = offset.left,
            top         = offset.top;

        options = $.extend({topoffset:0, leftoffset:0}, options);

        if (top + $element.height() >= window_top && top - options.topoffset <= window_top + $win.height() &&
            left + $element.width() >= window_left && left - options.leftoffset <= window_left + $win.width()) {
          return true;
        } else {
          return false;
        }
    };

    $.Utils.options = function(string) {

        if ($.isPlainObject(string)) return string;

        var start = (string ? string.indexOf("{") : -1), options = {};

        if (start != -1) {
            try {
                options = (new Function("", "var json = " + string.substr(start) + "; return JSON.parse(JSON.stringify(json));"))();
            } catch (e) {}
        }

        return options;
    };

    $.Utils.events       = {};
    $.Utils.events.click = $.support.touch ? 'tap' : 'click';

    $.langdirection = $html.attr("dir") == "rtl" ? "right" : "left";

    $(function(){

        // Check for dom modifications
        if(!$.support.mutationobserver) return;

        // Install an observer for custom needs of dom changes
        var observer = new $.support.mutationobserver($.Utils.debounce(function(mutations) {
            $(doc).trigger("domready");
        }, 300));

        // pass in the target node, as well as the observer options
        observer.observe(document.body, { childList: true, subtree: true });

    });

    // add touch identifier class
    $html.addClass($.support.touch ? "touch" : "no-touch");

}(jQuery, window, document));
$(function () {

  $('[data-type="btn-rotate-file-entity"]').each(function () {
    var $this = $(this);
    var $id = $this.data('id');
    var $idEntryWork = $this.data('idEntry');

    $this.on('click', function (e) {
      $this.prop('disabled', true);

      e.preventDefault();

      if ($id) {
        $.notify("이미지를 회전중입니다. 약 30초만 기다려주세요.", {status: "success"});

        $.ajax({
          url: "/admin/api/file/" + $id + "/rotate/" + $idEntryWork,
          method: 'POST',
          contentType: "application/json"
        }).done(function (result) {
          console.debug(result);
          window.location.reload();

        }).fail(function (jqXHR, textStatus) {
          var message = "(" + jqXHR.status + ") ";
          if (jqXHR.responseJSON && jqXHR.responseJSON.message) {
            message = message + jqXHR.responseJSON.message;
          }
          $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요.<br>Message: " + message, {status: "danger"});
        });
      }

    });
  });

  $('[data-type="btn-rotate-file-winner"]').each(function () {
    var $this = $(this);
    var $id = $this.data('id');
    var $img = $this.data('img');

    $this.on('click', function (e) {
      $this.prop('disabled', true);

      e.preventDefault();

      if ($id) {
        $.notify("이미지를 회전중입니다. 약 30초만 기다려주세요.", {status: "success"});

        $.ajax({
          url: "/admin/api/file/winner/" + $id + "/rotate/img/" + $img,
          method: 'POST',
          contentType: "application/json"
        }).done(function (result) {
          console.debug(result);
          window.location.reload();

        }).fail(function (jqXHR, textStatus) {
          var message = "(" + jqXHR.status + ") ";
          if (jqXHR.responseJSON && jqXHR.responseJSON.message) {
            message = message + jqXHR.responseJSON.message;
          }
          $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요.<br>Message: " + message, {status: "danger"});
        });
      }

    });
  });
});
// Custom jQuery
// ----------------------------------- 


(function(window, document, $, undefined){

  $(function(){
      //console.debug(localStorage.FEK, 'FEK');

  });

})(window, document, window.jQuery);
$(function () {
  $(document).on("keypress", ":input:not(textarea)", function(event) {
    return event.keyCode !== 13;
  });
});
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('#modify-success').each(function () {
            var $this = $(this);

            setTimeout(function () {
                $this.fadeOut();
            }, 1500);
        });

    });

})(window, document, window.jQuery);
$(function () {

  $('[data-type="ticket-mobile-send"]').each(function () {
    var $this = $(this);
    var $id = $this.data('id');
    var $mobile = $this.find('[name="mobile"]');
    var $button = $this.find('button');


    $button.on('click', function (e) {
      if ($mobile.val() !== undefined && ($mobile.val().length === 11 || $mobile.val().length === 12)) {
        if (confirm("정말로 " + $mobile.val() + " 번호로 티켓번호를 발송하시겠습니까?"))
          $.ajax({
            url: "/admin/api/tickets",
            method: 'POST',
            contentType: "application/json",
            data: JSON.stringify({id: $id, mobile: $mobile.val()})
          }).done(function (result) {
            console.debug(result);
            $.notify("티켓이 등록되었습니다.", {status: "success"});

          }).fail(function (jqXHR, textStatus) {
            if (jqXHR.status.toString().startsWith("4")) {
              console.debug(jqXHR, "jqXHR");
              console.debug(textStatus, "textStatus");
              if (jqXHR.responseText && jqXHR.responseText.message) {
                $.notify(jqXHR.responseText, {status: "danger"});
              } else
                $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
            } else {
              $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
            }
          });
      } else {

        $.notify("휴대폰번호를 정확히 입력해주세요.", {status: "warning"});
      }

    });
  });
});
/**
 * NULL 의 사용.
 *
 * 사용 가능한 경우.
 *  - 나중에 값을 할당할 변수를 초기화 할 때
 *  - 선언한 변수에 값이 할당되었는지 비교할때
 *  - 인자 값으로 객체를 넘기는 함수를 호출할 때
 *  - 함수를 호출한 곳에서 반환값으로 객체를 기대할 때
 *
 * 사용 불가능한 경우.
 *  - 함수의 인자 값을 확인하기 위해 null로 비교해서는 안된다.
 *  - 초기화되지 않은 변수를 null로 비교해서는 안된다.
 * */

/**
 * 데이터 타입 검사
 *
 * 주의 : null 은 변수에 값이 할당되었는지 확인할 때 사용하면 안된다.
 *       null 이 허용되는 경우는 기대하는 값이 정말 null 일 경우에만 사용한다.
 *       반드시 '===' 또는 '!==' 을 사용한다.
 *       ex) if(value !== null){...}
 **/

/**
 * String 검사
 *
 * @param value
 * @return {boolean}
 * */
function isString(value) {
    return typeof value === 'string';
}

/**
 * Number 검사
 *
 * @param value
 * @return {boolean}
 * */
function isNumber(value) {
    return typeof value === "number";
}

/**
 * Boolean 검사
 *
 * @param value
 * @return {boolean}
 * */
function isBoolean(value) {
    return typeof value === "boolean" && value;
}

/**
 * Undefined 검사
 * 주의 : "undefined" 의 경우, 선언한 변수이든 선언하지 않은 변수이든
 *        둘다 "undefined" 로 반환한다.
 *
 * @param value
 * @return {boolean}
 * */
function isUndefined(value) {
    return typeof value === "undefined"
}

/**
 * 객체 참조타입 검사(Object, Array, Date, Error)
 *
 * 주의 : 1. 모든 객체는 Object 를 기본적으로 상속받으므로 어떤 객체든지
 *        instanceof Object 를 사용하면 true 를 반환한다.
 *        때문에 instanceof Object 를 사용하면 무슨 타입인지 알수 없다.
 *
 *        2. 사용자 정의 타입을 구분할 때도 "instanceof" 사용.
 *        단, 같은 프레임 내에서만 사용한다. 다른 프레임간에는 instanceof 를 사용하지 않는다.
 **/

/**
 * Date 객체인지 확인
 *
 * @param value
 * @return {boolean}
 **/
function isDate(value) {
    return value instanceof Date;
}

/**
 * RegExp 객체인지 확인
 *
 * @param value
 * @return {boolean}
 **/
function isRegExp(value) {
    return value instanceof RegExp;
}

/**
 * Error 객체인지 확인
 *
 * @param value
 * @return {boolean}
 **/
function isError(value) {
    return value instanceof Error;
}

/**
 * 함수 검사
 *
 * 함수는 참조 타입으로 "Function" 이라는 생성자가 존재,
 * 모든 함수는 "Function" 의 인스턴스이다.
 *
 * 주의 : IE8 이하 버전은 DOM 에 관련된 함수에 "function" 이 아닌 "Object" 로 반환.
 * */
function isFunction(value) {
    return typeof value === "function";
}

// IE8 이하 DOM 관련 함수 검사
function isFunctionForIe(value) {
    return value in document;
}

/**
 * Array 검사
 *
 * @param value
 * @returns {boolean}
 */
function isArray(value) {
    if (typeof Array.isArray === "function") {
        return Array.isArray(value);
    } else {
        return Object.prototype.toString.call(value) === "[object Array]";
    }
}

/**
 * 프로퍼티 검사
 *
 * 인스턴스에 존재한는 프로퍼티인지만 검사한다.
 *
 * 주의 : IE8 이하 버전의 DOM 객체는 Object 를 상속받지 않아 hasOwnProperty() 메서드가 없다.
 *        가급적 in 연산자를 사용하고, 인스턴스의 프로퍼티인지 확인이 필요할때만
 *        hasOwnProperty()를 사용.
 *
 * @param value 프로퍼티값
 * @param object 인스턴스
 * @return {boolean}
 * */
function hasProperty(value, object) {
    return value in object;
}

/**
 *
 * @returns {boolean}
 */
function isEmpty(value) {
    return isUndefined(value) || value === '' || value === null || value !== value;
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
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
$(function () {

  $('[id="update-businessUser"]').each(function () {
  var selectItem = function () {

    console.debug("selectItem");
    $("input[name=selectCheckbox]:checked").each(function () {
      var $this = $(this);

    });
  };

    $('[data-type="checkUserByBU"]').click(function () {
      var chk = $(this).is(":checked");

      if (chk) {
        $("input[name=selectCheckbox]").prop("checked", true);
      } else {
        $("input[name=selectCheckbox]").prop("checked", false);
      }

      selectItem();
    });

  $("input[name=selectCheckbox]").on('change', function () {
    selectItem();
  });
  });
});
$(function () {


  $('.campsite-create-update').each(function () {

    var bindDeleteBtn = function () {
      $('[data-type="btn-delete-camp-type"]').off('click');
      $('[data-type="btn-delete-camp-type"]').on('click', function (e) {
        e.preventDefault();

        if (confirm(($(this).closest('tr').data('index') + 1) + '번째 열을 정말 삭제하시겠습니까?')) {

          var $campTypeId = $(this).closest('tr').data("id");
          var $tr = $(this).closest('tr');
          if ($campTypeId) {
            $.ajax({
              url: "/admin/api/camp-type/" + $campTypeId,
              method: 'DELETE',
              contentType: "application/json"
            }).done(function (result) {
              console.debug(result);
              $tr.remove();
            }).fail(function (jqXHR, textStatus) {
              if (jqXHR.status.toString().startsWith("4")) {
                $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
              } else {
                $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
              }
            });
          } else {
            $tr.remove();
          }
        }
      });
    };

    bindDeleteBtn();

    $('#btn-add-season-type').on('click', function () {

      var $template = $('#template-camp-type').html();
      var $tbody = $('#tbody-camp-type');

      var $lastChildIndex = $tbody.find('[ data-type="tr-camp-type"]:last-child').data('index');
      $lastChildIndex = isEmpty($lastChildIndex) ? 0 : $lastChildIndex + 1;

      var item = {
        index: $lastChildIndex
      };

      if ($lastChildIndex > 10) {
        alert("최대 10개까지 등록가능합니다.");
        return;
      }
      $tbody.append(Mustache.render($template, item));
      bindDeleteBtn();
    });
  });
});
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
$(function () {

  $('[data-type="group-category"]').each(function () {

    var $this = $(this);

    var groups = [];
    var category1 = [];
    var category2 = [];
    var category3 = [];
    var category4 = [];
    var category5 = [];

    var values = $this.data('values');
    console.debug(values, 'values');

    $.ajax({
      url: "/admin/api/category-product/group" + (values ? '?idCategory=' + values : ''),
      method: 'GET',
      contentType: "application/json"
    }).done(function (result) {
      console.debug(result);

      groups = result;

      // CATEGORY 1
      if (groups) {
        groups.forEach(function (item, key, map) {
          category1.push(item);
          var selected = item.checked ? 'selected' : '';
          $selectCategory1.append('<option value="' + item.id + '" ' + selected + '>(' + item.id + ') ' + item.content + '</option>');
        });
        onChangeCategory1();
      }

    }).fail(function (jqXHR, textStatus) {
      if (jqXHR.status.toString().startsWith("4")) {
        $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
      } else {
        $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
      }
    });

    var $selectCategory1 = $this.find('[data-type="select-category-1"]');
    var $selectCategory2 = $this.find('[data-type="select-category-2"]');
    var $selectCategory3 = $this.find('[data-type="select-category-3"]');
    var $selectCategory4 = $this.find('[data-type="select-category-4"]');
    var $selectCategory5 = $this.find('[data-type="select-category-5"]');

    var onChangeCategory1 = function () {

      $selectCategory2.html('<option value="">없음</option>');
      $selectCategory3.html('<option value="">없음</option>');
      $selectCategory4.html('<option value="">없음</option>');
      $selectCategory5.html('<option value="">없음</option>');
      category2 = [];
      category3 = [];
      category4 = [];
      category5 = [];

      var _id = $selectCategory1.find(':selected').val();

      // CATEGORY 2
      groups.forEach(function (item1, key, map) {

        if (item1.id == _id) {
          if (item1.children) {
            $selectCategory2.html('<option value="">카테고리2</option>');
            item1.children.forEach(function (item2, key, map) {
              category2.push(item2);
              var selected = item2.checked ? 'selected' : '';
              $selectCategory2.append('<option value="' + item2.id + '" ' + selected + '>(' + item2.id + ') ' + item2.content + '</option>');
            });
            onChangeCategory2();
          }
        }
      });
    };
    var onChangeCategory2 = function () {

      $selectCategory3.html('<option value="">없음</option>');
      $selectCategory4.html('<option value="">없음</option>');
      $selectCategory5.html('<option value="">없음</option>');
      category3 = [];
      category4 = [];
      category5 = [];

      var _id = $selectCategory2.find(':selected').val();

      // CATEGORY 2
      category2.forEach(function (item2, key, map) {

        if (item2.id == _id) {
          if (item2.children) {
            $selectCategory3.html('<option value="">카테고리3</option>');
            item2.children.forEach(function (item3, key, map) {
              category3.push(item3);
              var selected = item3.checked ? 'selected' : '';
              $selectCategory3.append('<option value="' + item3.id + '" ' + selected + '>(' + item3.id + ') ' + item3.content + '</option>');
            });
            onChangeCategory3();
          }
        }
      });
    };
    var onChangeCategory3 = function () {

      $selectCategory4.html('<option value="">없음</option>');
      $selectCategory5.html('<option value="">없음</option>');
      category4 = [];
      category5 = [];

      var _id = $selectCategory3.find(':selected').val();

      // CATEGORY 3
      category3.forEach(function (item3, key, map) {

        if (item3.id == _id) {
          if (item3.children) {
            $selectCategory4.html('<option value="">카테고리4</option>');
            item3.children.forEach(function (item4, key, map) {
              category4.push(item4);
              var selected = item4.checked ? 'selected' : '';
              $selectCategory4.append('<option value="' + item4.id + '" ' + selected + '>(' + item4.id + ') ' + item4.content + '</option>');
            });
            onChangeCategory4();
          }
        }
      });
    };
    var onChangeCategory4 = function () {

      $selectCategory5.html('<option value="">없음</option>');
      category5 = [];

      var _id = $selectCategory4.find(':selected').val();

      // CATEGORY 3
      category4.forEach(function (item4, key, map) {

        if (item4.id == _id) {
          if (item4.children) {
            $selectCategory5.html('<option value="">카테고리5</option>');
            item4.children.forEach(function (item5, key, map) {
              category5.push(item5);
              var selected = item5.checked ? 'selected' : '';
              $selectCategory5.append('<option value="' + item5.id + '" ' + selected + '>(' + item5.id + ') ' + item5.content + '</option>');
            });
            onChangeCategory5();
          }
        }
      });
    };
    var onChangeCategory5 = function () {

      var _id = $selectCategory5.find(':selected').val();

      console.debug(_id, '_id');
    };

    $selectCategory1.on('change', onChangeCategory1);
    $selectCategory2.on('change', onChangeCategory2);
    $selectCategory3.on('change', onChangeCategory3);
    $selectCategory4.on('change', onChangeCategory4);
    $selectCategory5.on('change', onChangeCategory5);
  });
});
(function (window, document, $, undefined) {

  $(function () {

    $('.coupon-create-update').each(function () {

      // variables
      var $radioMode = $('[name="discountMethod"]');
      var $divRate = $('[data-role="coupon-discount-rate"]');
      var $divPrice = $('[data-role="coupon-discount-price"]');
      var $divFixed = $('[data-role="coupon-discount-fixed"]');
      var $divPoint = $('[data-role="coupon-discount-point"]');

      var collapse = function(val) {
        if (val === 'PRICE') {
          $divRate.closest('div').hide();
          $divFixed.closest('div').hide();
          $divPoint.closest('div').hide();
          $divPrice.closest('div').show();
        } else if (val === 'FIXED_AMOUNT') {
          $divRate.closest('div').hide();
          $divPrice.closest('div').hide();
          $divPoint.closest('div').hide();
          $divFixed.closest('div').show();
        } else if (val === 'POINT') {
          $divRate.closest('div').hide();
          $divPrice.closest('div').hide();
          $divFixed.closest('div').hide();
          $divPoint.closest('div').show();
        } else {
          $divPrice.closest('div').hide();
          $divFixed.closest('div').hide();
          $divPoint.closest('div').hide();
          $divRate.closest('div').show();
        }
      };

      $radioMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="discountMethod"]:checked').val());

    });

  });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

  $(function () {

    $('.coupon-create-update').each(function () {

      // variables
      var $radioMode = $('[name="target"]');
      var $divProduct = $('[data-role="coupon-target-product"]');
      var $divCategory = $('[data-role="coupon-target-category"]');
      var $divBrand = $('[data-role="coupon-target-brand"]');
      var $divPlan = $('[data-role="coupon-target-plan"]');

      var collapse = function(val) {
        if (val === 'CATEGORY') {
          $divProduct.hide();
          $divBrand.hide();
          $divPlan.hide();
          $divCategory.show();
        } else if (val === 'BRAND') {
          $divProduct.hide();
          $divCategory.hide();
          $divPlan.hide();
          $divBrand.show();
        } else if (val === 'PLAN') {
          $divProduct.hide();
          $divCategory.hide();
          $divBrand.hide();
          $divPlan.show();
        } else {
          $divCategory.hide();
          $divBrand.hide();
          $divPlan.hide();
          $divProduct.show();
        }
      };

      $radioMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="target"]:checked').val());

    });

  });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

  $(function () {

    $('.coupon-create-update').each(function () {

      // variables
      var $radioMode = $('[name="type"]');
      var $divLimit = $('[data-role="coupon-type-limit"]');
      var $divPeriod = $('[data-role="coupon-type-period"]');
      var $divCycle = $('[data-role="coupon-type-cycle"]');
      var $divDays = $('[data-role="coupon-type-days"]');

      var collapse = function(val) {
        if (val === 'DOWNLOAD') {
          $divCycle.hide();
          $divDays.hide();
          $divPeriod.show();
          $divLimit.show();
        } else if (val === 'AUTOMATIC') {
          $divPeriod.hide();
          $divLimit.hide();
          $divCycle.show();
          $divDays.show();
        } else if (val === 'BIRTHDAY') {
          $divPeriod.hide();
          $divLimit.hide();
          $divCycle.hide();
          $divDays.show();
        } else {
          $divLimit.hide();
          $divCycle.hide();
          $divDays.hide();
          $divPeriod.show();
        }
      };

      $radioMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="type"]:checked').val());

    });

  });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

  $(function () {

    $('.coupon-create-update').each(function () {

      // variables
      var $radioUserMode = $('[name="userMode"]');
      var $selectBuyerLevels = $('[name="buyerLevels"]');

      var collapse = function(val) {
        if (val === 'LEVEL') {
          $selectBuyerLevels.closest('div').show();
        } else {
          $selectBuyerLevels.closest('div').hide();
        }
      };

      $radioUserMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="userMode"]:checked').val());

    });

  });

})(window, document, window.jQuery);
$(function () {
  $('[data-type="select-currency"]').each(function () {
    $(this).on('change', function (e) {
        var $val = $(this).find(':selected').data('currencyText');
        $('[data-type="currency-text"]').val($val);
    });
  });
});
(function (window, document, $, undefined) {

  $(function () {

    $('[data-type="btn-email-popup-document"]').each(function () {

      $(this).on('click', function (e) {
        e.preventDefault();

        var $this = $(this);

        window.smsPopup = window.open("/admin/document/popup", "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=50,left=50,width=1000,height=800");
      });

    });
  });

})(window, document, window.jQuery);
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
$(function () {
  
  $('.winner-list').each(function () {

    // POD
    $('[name="allCheckedCategories"]').on('change', function () {
      var $this = $(this);
      var list = $('[name="checkedCategories"]');
      list.prop('checked', $this.is(":checked"));
    });

    $('[name="checkedCategories"]').each(function () {
      $(this).on('change', function () {
        if ($(this).is(":checked") === false) {
          $('[name="allCheckedCategories"]').prop('checked', false);
        }
      })
    });
  });
});
$(function () {
  $('[data-type="btn-winner-reg"]').each(function () {
    var $btn = $(this);

    var $id = $btn.data('id');
    var $event = $btn.data('event');

    var $eventValue = null;

    if ($event === 'FIRST') {
      $eventValue = "1차 우수작"
    } else if ($event === 'SECOND') {
      $eventValue = "2차 우수작"
    } else if ($event === 'THIRD') {
      $eventValue = "3차 우수작"
    }

    $btn.on('click', function () {

      if ($event === 'FIRST') {

        if (confirm($eventValue + "에 선정하시겠습니까?")) {
          $.ajax({
            url: "/admin/api/winner",
            method: 'POST',
            contentType: "application/json",
            data: JSON.stringify({
              id: $id,
              event: $event
            })
          }).done(function (result) {
            console.debug(result);
            $.notify("1차 우수작에 등록되었습니다.", {status: "success"});

          }).fail(function (jqXHR, textStatus) {
            var message = "(" + jqXHR.status + ") ";
            if (jqXHR.responseJSON && jqXHR.responseJSON.message) {
              message = message + jqXHR.responseJSON.message;
            }
            $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요.<br>Message: " + message, {status: "danger"});
          });
        }

      } else {
        alert('아직 준비중입니다.');
      }
    })
  })
});
$(function () {


  $('#selectC1s').each(function () {
    var $select = $(this);
    var $selectSub = $('#selectC1S1s');

    $select.on('change', function () {
      var subCategories = $('#selectC1s option:selected').data('ic1s1');

      $selectSub.html('');
      if (subCategories) {
        subCategories.forEach(function (value, index) {
          $selectSub.append('<option value="' + value.ky + '">' + value.name + '</option>');
        });
      }
    });
  })
});
(function(window, document, $, undefined){

    $(function(){

        $('[download]').each(function () {

            $(this).on('click',function (e) {
                e.preventDefault();
                download($(this).attr('href'));
            })
        })

    });

})(window, document, window.jQuery);
// Upload Demo
// -----------------------------------

(function (window, document, $, undefined) {

    $(function () {
        'use strict';

        var deleteFileupload = function ($warp) {

            $warp.find('[data-type="delete-file-upload"]').on('click', function (e) {

                e.preventDefault();

                var $uploadedFile = $warp.find('[data-type="uploaded-file"]');
                var url = $(this).data('url');

                $.ajax({
                    url: "/admin/api/upload/file",
                    method: 'DELETE',
                    contentType: "application/json",
                    data: JSON.stringify({url: url})
                }).done(function (result) {
                    $.notify("파일은 삭제되었습니다. 수정버튼을 눌려주세요.", {status: "success"});
                    $uploadedFile.html('');

                }).fail(function (jqXHR, textStatus) {
                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
                    }
                });

            });
        };

        $('[data-type="upload-file"]').each(function () {

            var $fileupload = $(this);
            var $template = $('#template-upload-file').html();
            var $warp = $fileupload.closest('[data-type="wrap-file-upload"]');
            var $btnFileUpload = $warp.find('[data-type="btn-upload-files"]');
            var $uploadedFile = $warp.find('[data-type="uploaded-file"]');
            var $inputName = $fileupload.data('name');
            var $progress = $($btnFileUpload.data('progress'));

            deleteFileupload($warp);

            $fileupload.fileupload({
                dataType: 'json',
                sequentialUploads: true,
                add: function (e, data) {
                    $progress.show();

                    //console.debug("add");
                    //console.debug(data, 'data');

                    if (data.files.error) {
                        alert(data.files[0].error);
                    }

                    var uploadErrors = [];

                    if (uploadErrors.length > 0) {
                        alert(uploadErrors.join("\n"));
                    } else {
                        data.submit();
                    }
                },
                done: function (e, data) {
                    // console.debug("done");
                    // console.debug(data, 'data');
                    var item = data.result;
                    item['inputName'] = $inputName;
                    item['index'] = 0;

                    $uploadedFile.html(Mustache.render($template, item));
                    $progress.hide();
                    deleteFileupload($warp);
                },

                progressall: function (e, data) {
                    //console.debug("progressall");
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    $progress.html('Uploading data progress (' + progress + '%)');
                    console.debug(progress, 'progress');
                },

                fail: function (e, data) {
                    // console.debug(e);
                    // console.debug(data);
                    $progress.html('');

                    if(data.jqXHR.responseJSON) {
                        // alert("서버와 통신 중 문제가 발생했습니다");
                        $.notify(data.jqXHR.responseJSON.message, {status: "danger"});
                    }
                }
            });
        });
    });

})(window, document, window.jQuery);
// Upload Demo
// -----------------------------------

(function (window, document, $, undefined) {

    $(function () {
        'use strict';

        var deleteFileupload = function ($warp) {

            console.debug($warp.find('[data-type="delete-file-upload"]').length,'$warp');

            $warp.find('[data-type="delete-file-upload"]').off();
            $warp.find('[data-type="delete-file-upload"]').on('click', function (e) {

                e.preventDefault();

                var $item = $(this).closest('[data-type="item-upload-file"]');
                var url = $(this).data('url');

                $.ajax({
                    url: "/admin/api/upload/file",
                    method: 'DELETE',
                    contentType: "application/json",
                    data: JSON.stringify({url: url})
                }).done(function (result) {
                    $.notify("파일이 삭제 되었습니다.", {status: "success"});
                    $item.remove();
                }).fail(function (jqXHR, textStatus) {
                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
                    }
                });

            });
        };

        $('[data-type="upload-files"]').each(function () {

            var $fileupload = $(this);
            var $template = $('#template-upload-files').html();
            var $warp = $fileupload.closest('[data-type="wrap-file-upload"]');
            var $btnFileUpload = $warp.find('[data-type="btn-upload-files"]');
            var $uploadedFile = $warp.find('[data-type="uploaded-file"]');
            var $inputName = $fileupload.data('name');
            var $progress = $($btnFileUpload.data('progress'));

            deleteFileupload($warp);

            $fileupload.fileupload({
                dataType: 'json',
                sequentialUploads: true,
                add: function (e, data) {
                    $progress.show();

                    //console.debug("add");
                    //console.debug(data, 'data');

                    if (data.files.error) {
                        alert(data.files[0].error);
                    }

                    var uploadErrors = [];

                    if (uploadErrors.length > 0) {
                        alert(uploadErrors.join("\n"));
                    } else {
                        data.submit();
                    }
                },
                done: function (e, data) {
                    // console.debug("done");
                    // console.debug(data, 'data');
                    var item = data.result;
                    var $lastChildIndex = $warp.find('[data-type="item-upload-file"]:last-child').data('index');
                    $lastChildIndex = isEmpty($lastChildIndex) ? 0 : $lastChildIndex + 1;

                    item['inputName'] = $inputName;
                    item['index'] = $lastChildIndex;

                    $uploadedFile.append(Mustache.render($template, item));
                    $progress.hide();
                    deleteFileupload($warp);
                },

                progressall: function (e, data) {
                    //console.debug("progressall");
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    $progress.html('Uploading data progress (' + progress + '%)');
                    console.debug(progress, 'progress');
                },

                fail: function (e, data) {
                    // console.debug(e);
                    // console.debug(data);
                    $progress.html('');

                    if(data.jqXHR.responseJSON) {
                        // alert("서버와 통신 중 문제가 발생했습니다");
                        $.notify(data.jqXHR.responseJSON.message, {status: "danger"});
                    }
                }
            });
        });
    });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

    $(function () {

        $('[data-type="upload-image"]').each(function () {

            var $this = $(this);
            var template = $('#template-item-image').html();
            var updatePath = $this.data('value'); // 업데이할 이미지 data-value
            var inputName = $this.data('name');
            var validatorTarget = $this.data('validatorTarget');

            var loadImage = function (url) {
                if (!isEmpty(url)) {
                    var listImage = $this.find('.list-image-uploader');
                    listImage.append(Mustache.render(template, {
                        image: url,
                        inputName: inputName
                    }));
                }
            };

            var refreshPlusBtn = function () {
                if ($this.find('.item-box-image').length > 0) {
                    $this.find('.wrapper-image-upload').hide();
                } else {
                    $this.find('.wrapper-image-upload').show();
                }
            };

            var deleteImageBtn = function () {

                var btnDelete = $this.find('.btn-image-uploader-delete');
                btnDelete.off();
                btnDelete.on('click', function () {
                    var $btnDelete = $(this);
                    $btnDelete.off();
                    $btnDelete.closest('.box-image-uploader').remove();
                    refreshPlusBtn();
                });
            };

            loadImage(updatePath);
            refreshPlusBtn();
            deleteImageBtn();

            $this.find('.wrapper-image-upload').each(function () {

                var $wrapper = $(this);
                var inputFile = $wrapper.find('.input-image-upload');
                var imageDropZone = $wrapper;
                var limit = 3000;

                inputFile.fileupload({
                    dataType: 'json',
                    sequentialUploads: true,
                    singleFileUploads: false,
                    add: function (e, data) {
                        //console.debug("add");
                        //console.debug(data, 'data');

                        var uploadErrors = [];
                        var acceptFileTypes = /^image\/(jpe?g|png|gif|svg|blob)$/i;

                        var oriFile = data.originalFiles[0];

                        if (oriFile['type'] != null && !acceptFileTypes.test(oriFile['type'])) {
                            uploadErrors.push('"gif", "jpeg", "jpg", "png", "svg", "blob" 이미지만 등록하세요.');
                        }

                        if (oriFile['size'] != null && oriFile['size'] > (1048576 * limit)) {
                            uploadErrors.push('파일이 너무 큽니다. 3GB이하로 등록하세요.');
                        }

                        if (uploadErrors.length > 0) {
                            alert(uploadErrors.join("\n"));
                        } else {
                            $(validatorTarget).find('.parsley-errors-list').removeClass("filled");
                            data.submit();
                        }
                    },
                    done: function (e, data) {
                        // console.debug("done");
                        console.debug(data, 'data');

                        var item = data.result;
                        console.debug(item, 'result');

                        loadImage(item.url);

                        refreshPlusBtn();
                        deleteImageBtn();
                    },

                    progressall: function (e, data) {
                        //console.debug("progressall");
                        var progress = parseInt(data.loaded / data.total * 100, 10);
                    },

                    fail: function (e, data) {
                        //console.debug(e);
                        //console.debug(data);
                        if(data.jqXHR.responseJSON) {
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
$(function () {

  $('[data-type="calculate-formula"]').each(function () {

    var $this = $(this);
    var $formula = $this.data('formula');
    var $id = $this.data('id');

    $this.on('click', function (e) {

      e.preventDefault();

      var $price = $this.closest('.input-group').find('[data-type="calculate-formula-price"]').val();

      if($price == 0 || !$price) {
        $.notify('가격을 정확히 입력하세요.', {status: "warning"});
        return;
      }

      var data = {
        price: $price
      };

      $.ajax({
        url: '/admin/api/formula/calculate/' + $id,
        method: 'POST',
        contentType: "application/json",
        data: JSON.stringify(data)
      }).done(function (data) {
        $.notify(data + '원', {status: "success"});
      });
    });
  });
});
$(function () {

  $('[data-type="formula-expression"]').each(function () {

    $(this).on('change', function () {
      var $this = $(this);
      $('[data-type="input-formula-expression"]').val($this.val());
    });
  });

  $('[data-type="formula-discount-expression"]').each(function () {

    $(this).on('change', function () {
      var $this = $(this);
      $('[data-type="input-formula-discount-expression"]').val($this.val());
    });
  });

  $('[data-type="formula-saving-expression"]').each(function () {

    $(this).on('change', function () {
      var $this = $(this);
      $('[data-type="input-formula-saving-expression"]').val($this.val());
    });
  });
});
// Custom jQuery
// -----------------------------------

(function (window, document, $, undefined) {

    $(function () {

        /**
         * SEARCH ADDRESS & GET LOCATION
         */
        $('[data-type="google-location-nearby"]').each(function () {

            var _this = $(this);
            var inputSearchAddress = _this.find('[data-type="input-google-location"]');
            var btnSearchAddress = _this.find('[data-type="btn-google-location"]');

            var latitude = $('#latitude');
            var longitude = $('#longitude');

            var marker = null;

            window.initMap = function () {

                var myLatlng = new google.maps.LatLng(22.3230454, 114.2076429);
                var mapOptions = {
                    zoom: 16,
                    center: myLatlng
                };

                map = new google.maps.Map(document.getElementById("map"), mapOptions);

                if (!isEmpty(latitude.val()) && !isEmpty(longitude.val())) {

                    marker = new google.maps.Marker({
                        position: {lat: Number(latitude.val()), lng: Number(longitude.val())},
                        map: map
                    });

                    map.setCenter(marker.getPosition());
                }
            };

            var getPlaceSearch = function (query) {

                if (marker != null) {
                    marker.setMap(null);
                }

                $.ajax({
                    url: '/api/v1/google/placesearch?query=' + query + '&language=en',
                    method: 'GET',
                    contentType: "application/json"
                }).done(function (data) {

                    if (data && data.status) {

                        if (data.status === 'OVER_QUERY_LIMIT') {
                            $.notify("Google API free quota exceeded.", {status: "warning"});
                        }
                    }

                    // console.debug(data, 'data');
                    if (data && data.results && data.results.length > 0) {

                        var result = data.results[0];
                        // console.debug(result, 'result');

                        if (result && result.place_id) {
                            // console.debug(result.place_id,' result.place_id');
                            placedetail(result.place_id);
                        }
                    }
                }).fail(function (jqXHR, textStatus) {

                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(jqXHR.responseJSON.message, {status: "danger"});
                    }
                });
            };

            var placedetail = function (placeId) {

                $.ajax({
                    url: '/api/v1/google/placedetail?placeId=' + placeId + '&language=en',
                    method: 'GET',
                    contentType: "application/json"
                }).done(function (data) {

                    if (data && data.result) {
                        // console.debug(data, 'data placedetail');

                        var result = data.result;

                        if (result && result.geometry && result.geometry.location) {

                            var latitude = result.geometry.location.lat;
                            var longitude = result.geometry.location.lng;

                            $('#latitude').val(latitude);
                            $('#longitude').val(longitude);

                            var myLatlng = {lat: Number(latitude), lng: Number(longitude)};
                            marker = new google.maps.Marker({
                                position: myLatlng,
                                map: map,
                                title: 'Click to zoom'
                            });

                            map.setCenter(marker.getPosition());
                        }

                        if (result && result.formatted_address) {
                            $('[name="addressEn"]').val(result.formatted_address);
                        }

                        if (result && result.postalCode) {
                            $('[name="postalCode"]').val(result.postalCode);
                        }

                        if (result && result.formatted_phone_number) {
                            $('[name="telephone"]').val(result.formatted_phone_number);
                        }

                        if (result && result.website) {
                            $('[name="website"]').val(result.website);
                        }

                    }

                }).fail(function (jqXHR, textStatus) {

                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(jqXHR.responseJSON.message, {status: "danger"});
                    }
                });

                $.ajax({
                    url: '/api/v1/google/placedetail?placeId=' + placeId + '&language=zh',
                    method: 'GET',
                    contentType: "application/json"
                }).done(function (data) {

                    if (data && data.result) {
                        // console.debug(data, 'data placedetail');

                        var result = data.result;

                        if (result && result.formatted_address) {
                            // console.debug(result.formatted_address,' result.formatted_address');
                            $('[name="addressCn"]').val(result.formatted_address);
                        }
                    }

                }).fail(function (jqXHR, textStatus) {

                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(jqXHR.responseJSON.message, {status: "danger"});
                    }
                });
            };


            var options = {
                serviceUrl: "/api/v1/google/autocomplete",
                paramName: "query",
                dataType: "json",
                onSearchStart: function (params) {
                    // console.debug(params, 'onSearchStart:::params');
                },
                onHint: function (container) {
                    // console.debug(container, 'onHint:::container');
                },
                onSearchComplete: function (query, suggestions) {
                    // console.debug(query, 'onSearchComplete:::query');
                    // console.debug(suggestions, 'onSearchComplete:::suggestions');
                },
                transformResult: function (response, originalQuery) {
                    // console.debug(response, 'transformResult:::response');
                    // console.debug(originalQuery, 'transformResult:::originalQuery');
                    var suggestions = [];

                    if (response && response.predictions) {
                        suggestions = response.predictions.map(function (item) {
                            return {value: item.description, data: item.place_id};
                        });
                    }
                    // console.debug(suggestions, 'suggestions');
                    return {suggestions: suggestions};
                },
                onSelect: function (suggestion) {
                    // console.debug(suggestion, 'onSelect:::suggestion');
                    var place_id = suggestion.data;
                    placedetail(place_id);
                },
                onSearchError: function (query, jqXHR, textStatus, errorThrown) {
                    // console.debug(query, 'onSearchError:::query');
                    // console.debug(jqXHR, 'onSearchError:::jqXHR');
                    // console.debug(textStatus, 'onSearchError:::textStatus');
                    // console.debug(errorThrown, 'onSearchError:::errorThrown');
                },
                onHide: function (container) {
                    // console.debug(container, 'onHide:::container');
                }
            };

            var searchAddress = function () {
                var $query = inputSearchAddress.val();
                if ($query) {
                    getPlaceSearch($query);
                }
            }

            inputSearchAddress.on('keydown', function (e) {

                if (e.keyCode === 13) {
                    e.preventDefault();
                    searchAddress();
                }
            });

            inputSearchAddress.autocomplete(options);

            btnSearchAddress.on('click', function (e) {

                e.preventDefault();
                searchAddress();
            })
        });

    });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------

(function (window, document, $, undefined) {

    $(function () {

        /**
         * SEARCH ADDRESS & GET LOCATION
         */
        $('[data-type="google-location"]').each(function () {

            var _this = $(this);
            var inputSearchAddress = _this.find('[data-type="input-google-location"]');
            var btnSearchAddress = _this.find('[data-type="btn-google-location"]');

            var latitude = $('[data-type="latitude"]');
            var longitude = $('[data-type="longitude"]');

            var marker = null;

            window.initMap = function () {

                var myLatlng = new google.maps.LatLng(22.3230454, 114.2076429);
                var mapOptions = {
                    zoom: 16,
                    center: myLatlng
                };

                map = new google.maps.Map(document.getElementById("map"), mapOptions);

                if (!isEmpty(latitude.val()) && !isEmpty(longitude.val())) {

                    marker = new google.maps.Marker({
                        position: {lat: Number(latitude.val()), lng: Number(longitude.val())},
                        map: map
                    });

                    map.setCenter(marker.getPosition());
                }
            };

            var getPlaceSearch = function (query) {

                if (marker != null) {
                    marker.setMap(null);
                }

                $.ajax({
                    url: '/api/v1/google/placesearch?query=' + query + '&language=en',
                    method: 'GET',
                    contentType: "application/json"
                }).done(function (data) {

                    if (data && data.status) {

                        if (data.status === 'OVER_QUERY_LIMIT') {
                            $.notify("Google API free quota exceeded.", {status: "warning"});
                        }
                    }

                    // console.debug(data, 'data');
                    if (data && data.results && data.results.length > 0) {

                        var result = data.results[0];
                        // console.debug(result, 'result');

                        if (result && result.place_id) {
                            // console.debug(result.place_id,' result.place_id');
                            placedetail(result.place_id);
                        }
                    }
                }).fail(function (jqXHR, textStatus) {

                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(jqXHR.responseJSON.message, {status: "danger"});
                    }
                });
            };

            var placedetail = function (placeId) {

                $.ajax({
                    url: '/api/v1/google/placedetail?placeId=' + placeId + '&language=en',
                    method: 'GET',
                    contentType: "application/json"
                }).done(function (data) {

                    if (data && data.result) {
                        // console.debug(data, 'data placedetail');

                        var result = data.result;

                        if (result && result.geometry && result.geometry.location) {

                            var latitude = result.geometry.location.lat;
                            var longitude = result.geometry.location.lng;

                            $('[data-type="latitude"]').val(latitude);
                            $('[data-type="longitude"]').val(longitude);

                            var myLatlng = {lat: Number(latitude), lng: Number(longitude)};
                            marker = new google.maps.Marker({
                                position: myLatlng,
                                map: map,
                                title: 'Click to zoom'
                            });

                            map.setCenter(marker.getPosition());
                        }

                        if (result && result.formatted_address) {
                            $('[data-type="address"]').val(result.formatted_address);
                        }

                        if (result && result.postalCode) {
                            $('[data-type="postalCode"]').val(result.postalCode);
                        }

                        if (result && result.formatted_phone_number) {
                            $('[data-type="phone"]').val(result.formatted_phone_number);
                        }

                        if (result && result.website) {
                            $('[data-type="website"]').val(result.website);
                        }

                    }

                }).fail(function (jqXHR, textStatus) {

                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(jqXHR.responseJSON.message, {status: "danger"});
                    }
                });

                $.ajax({
                    url: '/api/v1/google/placedetail?placeId=' + placeId + '&language=zh',
                    method: 'GET',
                    contentType: "application/json"
                }).done(function (data) {

                    if (data && data.result) {
                        // console.debug(data, 'data placedetail');

                        var result = data.result;

                        if (result && result.formatted_address) {
                            // console.debug(result.formatted_address,' result.formatted_address');
                            $('[name="addressCn"]').val(result.formatted_address);
                        }
                    }

                }).fail(function (jqXHR, textStatus) {

                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(jqXHR.responseJSON.message, {status: "danger"});
                    }
                });
            };


            var options = {
                serviceUrl: "/api/v1/google/autocomplete",
                paramName: "query",
                dataType: "json",
                onSearchStart: function (params) {
                    // console.debug(params, 'onSearchStart:::params');
                },
                onHint: function (container) {
                    // console.debug(container, 'onHint:::container');
                },
                onSearchComplete: function (query, suggestions) {
                    // console.debug(query, 'onSearchComplete:::query');
                    // console.debug(suggestions, 'onSearchComplete:::suggestions');
                },
                transformResult: function (response, originalQuery) {
                    // console.debug(response, 'transformResult:::response');
                    // console.debug(originalQuery, 'transformResult:::originalQuery');
                    var suggestions = [];

                    if (response && response.predictions) {
                        suggestions = response.predictions.map(function (item) {
                            return {value: item.description, data: item.place_id};
                        });
                    }
                    // console.debug(suggestions, 'suggestions');
                    return {suggestions: suggestions};
                },
                onSelect: function (suggestion) {
                    // console.debug(suggestion, 'onSelect:::suggestion');
                    var place_id = suggestion.data;
                    placedetail(place_id);
                },
                onSearchError: function (query, jqXHR, textStatus, errorThrown) {
                    // console.debug(query, 'onSearchError:::query');
                    // console.debug(jqXHR, 'onSearchError:::jqXHR');
                    // console.debug(textStatus, 'onSearchError:::textStatus');
                    // console.debug(errorThrown, 'onSearchError:::errorThrown');
                },
                onHide: function (container) {
                    // console.debug(container, 'onHide:::container');
                }
            };

            var searchAddress = function () {
                var $query = inputSearchAddress.val();
                if ($query) {
                    getPlaceSearch($query);
                }
            }

            inputSearchAddress.on('keydown', function (e) {

                if (e.keyCode === 13) {
                    e.preventDefault();
                    searchAddress();
                }
            });

            inputSearchAddress.autocomplete(options);

            btnSearchAddress.on('click', function (e) {

                e.preventDefault();
                searchAddress();
            })
        });

    });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

  $(function () {

    $('.grouping-create-update').each(function () {

      // variables
      var $radioTargetMode = $('[name="target"]');
      var $divProduct = $('[data-role="grouping-product"]');
      var $divCategory = $('[data-role="grouping-category"]');
      var $divBrand = $('[data-role="grouping-brand"]');

      var collapse = function(val) {
        if (val === 'CATEGORY') {
          $divProduct.hide();
          $divBrand.hide();
          $divCategory.show();
        } else if (val === 'BRAND') {
          $divProduct.hide();
          $divCategory.hide();
          $divBrand.show();
        } else {
          $divCategory.hide();
          $divBrand.hide();
          $divProduct.show();
        }
        console.debug(val, 'value');
      };

      $radioTargetMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="target"]:checked').val());

    });

  });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

  $(function () {

    $('.grouping-create-update').each(function () {

      // variables
      var $radioUserMode = $('[name="userMode"]');
      var $selectBuyerLevels = $('[name="buyerLevels"]');

      var collapse = function(val) {
        if (val === 'ALL') {
          $selectBuyerLevels.closest('div').hide();
        } else {
          $selectBuyerLevels.closest('div').show();
        }
      };

      $radioUserMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="userMode"]:checked').val());

    });

  });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {
        $('[data-parsley-international="true"]').each(function () {

            var $this = $(this);

            $this.parsley().on('field:error', function () {
                console.debug('ERROR');
                console.debug($(this.$element));
                var $field = $(this.$element);
                if ($field.data('parsleyLanguage') === true) {
                    var $tabpanel = $field.closest('[role="tabpanel"]');
                    $('[href="#' + $tabpanel.attr('id') + '"]').closest('[role="presentation"]').addClass('parsley-error');
                }
            });

            $this.parsley().on('field:success', function () {

                var $field = $(this.$element);
                if ($field.data('parsleyLanguage') === true) {
                    var $tabpanel = $field.closest('[role="tabpanel"]');
                    $('[href="#' + $tabpanel.attr('id') + '"]').closest('[role="presentation"]').removeClass('parsley-error');
                }
            });

            $this.parsley().on('form:submit', function (formInstance) {

                var $hasRequired = false;
                var $valueCount = 0;

                $('[data-type="active-language"]').find('[type="radio"]:checked').each(function () {
                    $hasRequired = true;
                    if ($(this).val() == 'true') {
                        $valueCount = $valueCount + 1;
                    }
                });

                if ($hasRequired && $valueCount === 0) {
                    $.notify("적어도 한가지 언어는 활성되어야 합니다.", {status: "warning"});
                    return false;
                }

                return true;
            });
        });

    });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('[data-type="active-language"]').each(function () {
            var $radio = $(this).find('[type="radio"]');

            var setRequired = function (is, $field) {
                var $tabpanel = $field.closest('[role="tabpanel"]');
                var $input = $tabpanel.find('[data-parsley-required]');
                $input.attr('data-parsley-required', is);
                console.debug(is, 'is');
                // console.debug($input, '$input');
                console.debug($input.length, '$input');

                // console.debug($input.attr('data-type'));
                if (is == 'true') {
                    $input.removeAttr('disabled');
                    $('[href="#' + $tabpanel.attr('id') + '"]').closest('[role="presentation"]').removeClass('inactive');
                    $input.each(function () {
                        if ($(this).attr('data-type') === 'froala-content') {
                            var $editor = $(this);
                            $editor.froalaEditor("edit.on");
                        }
                    });
                } else {
                    $input.attr('disabled', 'disabled');
                    $('[href="#' + $tabpanel.attr('id') + '"]').closest('[role="presentation"]').addClass('inactive');
                    $input.each(function () {
                        if ($(this).attr('data-type') === 'froala-content') {
                            var $editor = $(this);
                            setTimeout(function () {
                                $editor.froalaEditor("edit.off");
                            }, 500);
                        }
                    });
                }
            };

            var changeRadio = function () {

                $radio.each(function () {
                    var $this = $(this);
                    if ($this.is(":checked")) {
                        setRequired($this.val(), $this);
                    }
                });
            };

            $radio.on('change', function (e) {
                changeRadio();
            });

            changeRadio();
        });
    });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {
        $('[data-type="tabpanel-language"]').each(function () {
            $(this).find('.nav-tabs a:first').tab('show');
        });

    });

})(window, document, window.jQuery);

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

(function (window, document, $, undefined) {

  $(function () {

    /**
     * SEARCH ADDRESS & GET LOCATION
     */
    $('[data-type="search-address-map"]').each(function () {

      var _this = $(this);
      var currentPage = 0;
      var inputSearchAddress = _this.find('[data-type="input-search-address"]');
      var btnSearchAddress = _this.find('[data-type="btn-search-address"]');
      var btnNext = $('.list-next');
      var listJusos = $('#list-jusos');

      var latitude = $('[data-type="latitude"]');
      var longitude = $('[data-type="longitude"]');

      btnNext.hide();

      var marker = null;

      window.initMap = function () {

        var myLatlng = new google.maps.LatLng(37.496711, 127.023578);
        var mapOptions = {
          zoom: 16,
          center: myLatlng
        };

        map = new google.maps.Map(document.getElementById("map"), mapOptions);

        if (!isEmpty(latitude.val()) && !isEmpty(longitude.val())) {

          marker = new google.maps.Marker({
            position: {lat: Number(latitude.val()), lng: Number(longitude.val())},
            map: map
          });

          map.setCenter(marker.getPosition());
        }
      };

      var getLocation = function (address) {
        if (marker != null) {
          marker.setMap(null);
        }

        $.ajax({
          url: '/api/v1/location',
          method: 'GET',
          contentType: "application/json",
          data: {address: address}
        }).done(function (result) {

          btnSearchAddress.html("검색");
          if (result) {

            var latitude = result.latitude;
            var longitude = result.longitude;

            $('[data-type="latitude"]').val(latitude);
            $('[data-type="longitude"]').val(longitude);


            var myLatlng = {lat: Number(latitude), lng: Number(longitude)};
            marker = new google.maps.Marker({
              position: myLatlng,
              map: map,
              title: 'Click to zoom'
            });

            map.setCenter(marker.getPosition());
          }

        }).fail(function (jqXHR, textStatus) {
          btnSearchAddress.html("검색");
          if (jqXHR.status.toString().startsWith("4")) {
            $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
          } else {
            $.notify(jqXHR.responseJSON.message, {status: "danger"});
          }
        });
      };

      var search = function () {

        var keyword = inputSearchAddress.val();
        if (keyword === undefined || keyword.length == 0) {
          $.notify("검색어를 입력하세요.");
          return;
        }

        btnSearchAddress.html("<i class='fa fa-spinner fa-spin'></i>");

        $.ajax({
          url: '/api/v1/juso',
          method: 'GET',
          contentType: "application/json",
          data: {keyword: keyword, page: currentPage}
        }).done(function (result) {
          btnSearchAddress.html("검색");
          if (result && result.common.errorCode == '0') {
            currentPage = result.common.currentPage;

            var totalCount = result.common.totalCount;
            if (totalCount === 0) {
              $.notify("검색된 주소가 없습니다.<br/> 검색어를 확인해보세요.", {status: "warning"});
              $('.list-jusos').fadeOut();
              btnNext.hide();
              return;
            } else if (currentPage === 1) {
              $.notify("총 " + totalCount + "개의 주소가 검색되었습니다.", {status: "success"});
            }

            var countPerPage = result.common.countPerPage;
            var jusos = result.jusos;
            var isNext = false;

            if (countPerPage * currentPage < totalCount) {
              isNext = true;
            }

            if (jusos && jusos.length > 0) {
              var listJusos = $('#list-jusos');
              $('.list-jusos').fadeIn();
              jusos.forEach(function (item) {

                var juso = {
                  postalCode: item.zipNo,
                  roadAddr: (item.roadAddrPart1 + item.roadAddrPart2),
                  jibunAddr: item.jibunAddr,
                  engAddr: item.engAddr
                };

                var template = $('#template-juso').html();
                Mustache.parse(template);
                listJusos.append(Mustache.render(template, juso));
              });

              if (isNext) {
                btnNext.show();
              } else {
                btnNext.hide();
              }

              $('.item-juso').on('click', function () {

                var item = $(this);
                var postalCode = item.data('postalCode');
                var roadAddr = item.data('roadAddr');
                var engAddr = item.data('engAddr');

                $('[data-type="postalCode"]').val(postalCode);
                $('[data-type="address1"]').val(roadAddr);
                $('[data-type="engAddr"]').val(engAddr);


                getLocation(roadAddr);

                $('.list-jusos').fadeOut('fast');
                inputSearchAddress.val('');
              });

            } else {
              $('.list-jusos').fadeOut();
            }
          }

        }).fail(function (jqXHR, textStatus) {
          btnSearchAddress.html("검색");
          if (jqXHR.status.toString().startsWith("4")) {
            $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
          } else {
            $.notify(jqXHR.responseJSON.message, {status: "danger"});
          }
        });
      };

      btnNext.on('click', function () {
        currentPage++;
        search();
      });

      inputSearchAddress.on('keydown', function (event) {

        if (event.keyCode === 13) {
          listJusos.children().off();
          listJusos.html('');
          currentPage = 0;
          event.preventDefault();
          search();
        }

      });

      btnSearchAddress.on('click', function () {

        listJusos.children().off();
        listJusos.html('');
        currentPage = 0;
        search();
      });

    });

  });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

    $(function () {

        /**
         * SEARCH ADDRESS
         */
        $('[data-type="search-address-old"]').each(function () {

            var _this = $(this);
            var currentPage = 0;
            var inputSearchAddress = _this.find('[data-type="input-search-address"]');
            var btnSearchAddress = _this.find('[data-type="btn-search-address"]');
            var btnNext = $('.list-next');
            var listJusos = $('#list-jusos');

            btnNext.hide();

            var search = function () {

                var keyword = inputSearchAddress.val();
                if (keyword === undefined || keyword.length === 0) {
                    $.notify("검색어를 입력하세요.");
                    return;
                }

                btnSearchAddress.html("<i class='fa fa-spinner fa-spin'></i>");

                $.ajax({
                    url: '/api/v1/juso',
                    method: 'GET',
                    contentType: "application/json",
                    data: {keyword: keyword, page: currentPage}
                }).done(function (result) {
                    btnSearchAddress.html("검색");
                    if (result && result.common.errorCode == '0') {
                        currentPage = result.common.currentPage;

                        var totalCount = result.common.totalCount;
                        if (totalCount == 0) {
                            $.notify("검색된 주소가 없습니다.<br/> 검색어를 확인해보세요.", {status: "warning"});
                            $('.list-jusos').fadeOut();
                            btnNext.hide();
                            return;
                        } else if (currentPage == 1) {
                            $.notify("총 " + totalCount + "개의 주소가 검색되었습니다.", {status: "success"});
                        }

                        var countPerPage = result.common.countPerPage;
                        var jusos = result.jusos;
                        var isNext = false;

                        if (countPerPage * currentPage < totalCount) {
                            isNext = true;
                        }

                        if (jusos && jusos.length > 0) {
                            var listJusos = $('#list-jusos');
                            $('.list-jusos').fadeIn();
                            jusos.forEach(function (item) {

                                var juso = {
                                    postalCode: item.zipNo,
                                    roadAddr: (item.roadAddrPart1 + item.roadAddrPart2),
                                    jibunAddr: item.jibunAddr,
                                    engAddr: item.engAddr
                                };

                                var template = $('#template-juso').html();
                                Mustache.parse(template);
                                listJusos.append(Mustache.render(template, juso));
                            });

                            if (isNext) {
                                btnNext.show();
                            } else {
                                btnNext.hide();
                            }

                            $('.item-juso').on('click', function () {

                                var item = $(this);
                                var postalCode = item.data('postalCode');
                                var roadAddr = item.data('roadAddr');
                                var jibunAddr = item.data('jibunAddr');
                                var engAddr = item.data('engAddr');

                                $('[data-type="postalCode"]').val(postalCode);
                                $('[data-type="address1"]').val(roadAddr);
                                $('[data-type="jibunAddr"]').val(jibunAddr);
                                $('[data-type="engAddr"]').val(engAddr);

                                $('.list-jusos').fadeOut('fast');
                                inputSearchAddress.val('');
                            });

                        } else {
                            $('.list-jusos').fadeOut();
                        }
                    }

                }).fail(function (jqXHR, textStatus) {
                    btnSearchAddress.html("검색");
                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(jqXHR.responseJSON.message, {status: "danger"});
                    }
                });
            };

            btnNext.on('click', function () {
                currentPage++;
                search();
            });

            inputSearchAddress.on('keydown', function (event) {

                if (event.keyCode === 13) {
                    listJusos.children().off();
                    listJusos.html('');
                    currentPage = 0;
                    event.preventDefault();
                    search();
                }

            });

            btnSearchAddress.on('click', function () {

                listJusos.children().off();
                listJusos.html('');
                currentPage = 0;
                search();
            });

        });

    });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

    $(function () {

        /**
         * SEARCH ADDRESS
         */
        $('[data-type="search-address"]').each(function () {

            var _this = $(this);
            var currentPage = 0;
            var inputSearchAddress = _this.find('[data-type="input-search-address"]');
            var btnSearchAddress = _this.find('[data-type="btn-search-address"]');
            var btnNext = $('.list-next');
            var listJusos = $('#list-jusos');

            var $address1 = $('[data-type="address1"]');
            var $address2 = $('[data-type="address2"]');


            var searchAddress = function () {

                btnNext.hide();

                var search = function () {

                    var keyword = inputSearchAddress.val();
                    if (keyword === undefined || keyword.length === 0) {
                        $.notify("검색어를 입력하세요.");
                        return;
                    }

                    btnSearchAddress.html("<i class='fa fa-spinner fa-spin'></i>");

                    $.ajax({
                        url: '/admin/api/juso',
                        method: 'GET',
                        contentType: "application/json",
                        data: {keyword: keyword, page: currentPage}
                    }).done(function (result) {
                        btnSearchAddress.html("검색");
                        if (result && result.common.errorCode == '0') {
                            currentPage = result.common.currentPage;

                            var totalCount = result.common.totalCount;
                            if (totalCount == 0) {
                                $.notify("검색된 주소가 없습니다.<br/> 검색어를 확인해보세요.", {status: "warning"});
                                $('.list-jusos').fadeOut();
                                btnNext.hide();
                                return;
                            } else if (currentPage == 1) {
                                $.notify("총 " + totalCount + "개의 주소가 검색되었습니다.", {status: "success"});
                            }

                            var countPerPage = result.common.countPerPage;
                            var jusos = result.jusos;
                            var isNext = false;

                            if (countPerPage * currentPage < totalCount) {
                                isNext = true;
                            }

                            if (jusos && jusos.length > 0) {
                                var listJusos = $('#list-jusos');
                                $('.list-jusos').fadeIn();
                                // console.debug(jusos);
                                jusos.forEach(function (item) {
                                    // console.debug(item);
                                    var juso = {
                                        postalCode: item.zipNo,
                                        roadAddr: (item.roadAddrPart1 + item.roadAddrPart2),
                                        jibunAddr: item.jibunAddr,
                                        engAddr: item.engAddr,
                                        siNm: item.siNm,
                                        sggNm: item.sggNm,
                                        emdNm: item.emdNm,
                                        liNm: item.liNm
                                    };

                                    var template = $('#template-juso').html();
                                    Mustache.parse(template);
                                    listJusos.append(Mustache.render(template, juso));
                                });

                                if (isNext) {
                                    btnNext.show();
                                } else {
                                    btnNext.hide();
                                }

                                $('.item-juso').on('click', function () {

                                    var item = $(this);
                                    var postalCode = item.data('postalCode');
                                    var roadAddr = item.data('roadAddr');
                                    var jibunAddr = item.data('jibunAddr');
                                    var engAddr = item.data('engAddr');
                                    var siNm = item.data('siNm');
                                    var sggNm = item.data('sggNm');
                                    var emdNm = item.data('emdNm');
                                    var liNm = item.data('liNm');

                                    $('[data-type="postalCode"]').val(postalCode);
                                    $('[data-type="address1"]').val(roadAddr);
                                    $('[data-type="jibunAddr"]').val(jibunAddr);
                                    $('[data-type="engAddr"]').val(engAddr);
                                    $('[data-type="siNm"]').val(siNm);
                                    $('[data-type="sggNm"]').val(sggNm);
                                    $('[data-type="emdNm"]').val(emdNm);
                                    $('[data-type="liNm"]').val(liNm);

                                    searchAddressToCoordinate(roadAddr);

                                    $('.list-jusos').fadeOut('fast');
                                    inputSearchAddress.val('');
                                });

                            } else {
                                $('.list-jusos').fadeOut();
                            }
                        }

                    }).fail(function (jqXHR, textStatus) {
                        btnSearchAddress.html("검색");
                        if (jqXHR.status.toString().startsWith("4")) {
                            $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                        } else {
                            $.notify(jqXHR.responseJSON.message, {status: "danger"});
                        }
                    });
                };

                btnNext.on('click', function () {
                    currentPage++;
                    search();
                });

                inputSearchAddress.on('keydown', function (event) {

                    if (event.keyCode === 13) {
                        listJusos.children().off();
                        listJusos.html('');
                        currentPage = 0;
                        event.preventDefault();
                        search();
                    }

                });

                btnSearchAddress.on('click', function () {

                    listJusos.children().off();
                    listJusos.html('');
                    currentPage = 0;
                    search();
                });


            };

            // Naver Map

            var map = new naver.maps.Map("map", {
                center: new naver.maps.LatLng(37.5486838, 126.9197463),
                zoom: 10,
                mapTypeControl: true
            });

            var infoWindow = new naver.maps.InfoWindow({
                anchorSkew: true
            });

            map.setCursor('pointer');

// result by latlng coordinate
            function searchAddressToCoordinate(address) {

                naver.maps.Service.geocode({
                    address: address
                }, function (status, response) {
                    if (status === naver.maps.Service.Status.ERROR) {
                        return alert('Something Wrong!');
                    }

                    var item = response.result.items[0],
                        addrType = item.isRoadAddress ? '[도로명 주소]' : '[지번 주소]',
                        point = new naver.maps.Point(item.point.x, item.point.y);

                    $('[name="address.gps.latitude"]').val(point.y);
                    $('[name="address.gps.longitude"]').val(point.x);
                    infoWindow.setContent([
                        '<div style="padding:10px;min-width:200px;line-height:150%;">',
                        '<h4 style="margin-top:5px;">검색 주소 : ' + response.result.userquery + '</h4><br />',
                        addrType + ' ' + item.address + '<br />',
                        '위도 : ' + point.y + ', 경도 :' + point.x,
                        '</div>'
                    ].join('\n'));


                    map.setCenter(point);
                    infoWindow.open(map, point);
                });
            }

            naver.maps.onJSContentLoaded = searchAddress;

            if ($address1.val()) {
                searchAddressToCoordinate($address1.val() + " " + $address2.val());
            }

        });
    });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------

(function (window, document, $, undefined) {

    $(function () {

        var isdestroying = false;

        var initDatetimepicker = function () {

            if ($.fn.datetimepicker) {
                var datepickers = $('.list-filter-date');
                var inputStartDate = $("input[name='startDate']").val();
                var inputEndDate = $("input[name='endDate']").val();

                datepickers.each(function () {
                    var $format = $(this).data('format') === undefined ? "YYYY-MM-DD HH:mm:ss" : $(this).data('format');

                    var datepicker = $(this).datetimepicker({
                        icons: {
                            time: 'fa fa-clock-o',
                            date: 'fa fa-calendar',
                            up: 'fa fa-chevron-up',
                            down: 'fa fa-chevron-down',
                            previous: 'fa fa-chevron-left',
                            next: 'fa fa-chevron-right',
                            today: 'fa fa-crosshairs',
                            clear: 'fa fa-trash',
                            close: 'fa fa-close'
                        },
                        locale: 'ko',
                        format: $format,
                        toolbarPlacement: 'default',
                        showTodayButton: true,
                        showClear: true,
                        showClose: true,
                        ignoreReadonly: true,
                        allowInputToggle: true
                    });

                    if (datepicker.data('type') === 'startDate') {

                        if (inputStartDate) {
                            setTimeout(function () {
                                $(".list-filter-date[data-type='endDate']").data("DateTimePicker").minDate(inputStartDate);
                            }, 0);
                        }
                        datepicker.data("DateTimePicker").options({useCurrent: false});
                    } else if (datepicker.data('type') === 'endDate') {

                        if (inputEndDate) {
                            setTimeout(function () {
                                $(".list-filter-date[data-type='startDate']").data("DateTimePicker").maxDate(inputEndDate);
                            }, 0);
                        }
                        datepicker.data("DateTimePicker").options({useCurrent: false});
                    }
                });

                datepickers.each(function () {
                    var datepicker = $(this);

                    datepicker.on('dp.change', function (e) {

                        if (!isdestroying) {

                            var _this = $(this);
                            var type = _this.data('type');

                            if (type === 'startDate') {
                                $(".list-filter-date[data-type='endDate']").data("DateTimePicker").minDate(e.date);
                            } else if (type === 'endDate') {
                                $(".list-filter-date[data-type='startDate']").data("DateTimePicker").maxDate(e.date);
                            }
                        }
                    });
                });
            }
        };
        var clearDatetimepicker = function () {
            isdestroying = true;
            setTimeout(function () {
                var datepickers = $('.list-filter-date');

                datepickers.each(function () {
                    var datepicker = $(this);
                    datepicker.data("DateTimePicker").minDate(false);
                    datepicker.data("DateTimePicker").maxDate(false);
                    datepicker.data("DateTimePicker").clear();
                });

                isdestroying = false;
            }, 0);
        };

        $('#initial-list-filter').on('click', function (e) {
            e.preventDefault();

            $('.panel.panel-list-filter').on('panel.refresh', function (e, panel) {

                setTimeout(function () {

                    panel.removeSpinner();

                    clearDatetimepicker();
                    var form = $('#form-list-filter');
                    console.debug(form);
                    form.find('input').val('');
                    form.find('select').val('');
                    setTimeout(function () {
                        form.submit();
                    }, 5);
                }, 200);

            });
        });

        $('#submit-list-filter').on('click', function (e) {
            e.preventDefault();

            var form = $('#form-list-filter');
            form.submit();
        });

        $('#form-list-filter').find('input[name="query"]').on('keyup' , function(e){
            if (e.keyCode == 13) {
                var form = $('#form-list-filter');
                form.submit();
            }
        });

        /* 엑셀 다운로드 */
        var isClickExcel = false;
        $('#excel-list-filter').on('click', function (e) {
            e.preventDefault();

            if (isClickExcel) {
                return;
            }
            $.notify("다운로드 중입니다...", {});
            isClickExcel = true;

            var objects = $('#form-list-filter').serializeObject();
            var token = $("meta[name='_csrf']").attr("content");
            var param = $("meta[name='_csrf_param']").attr("content");

            var form = document.createElement("form");
            form.setAttribute("method", "post");
            form.setAttribute("action", $(this).data('action'));
            document.body.appendChild(form);

            var inputs = [];
            inputs.push({name: param, value: token});

            for (var key in objects) {
                inputs.push({name: key, value: objects[key]});
            }

            inputs.forEach(function (item) {
                var input = document.createElement("input");
                input.setAttribute("type", "hidden");
                input.setAttribute("name", item.name);
                input.setAttribute("value", item.value);
                form.appendChild(input);
            });

            form.submit();

            setTimeout(function () {
                isClickExcel = false;
                form.remove();
            }, 5000);
        });

        initDatetimepicker();
    });

})(window, document, window.jQuery);

(function (window, document, $, undefined) {

  $(function () {

    $('[data-type="update-order"]').each(function () {

      var $updateOrder = $(this);

      $updateOrder.find('[data-type="btn-order"]').on('click', function () {

        var $this = $(this);
        var wrapper = $this.closest('.wrapper-order');

        var actionUrl = wrapper.data('action');
        var id = wrapper.data('id');
        var mode = $this.data('mode');

        var csrfParamName = wrapper.data('csrfParamName');
        var csrfValue = wrapper.data('csrfValue');

        // Static 처리시 form 생성
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", actionUrl);
        document.body.appendChild(form);

        var inputIdCategory = document.createElement("input");
        inputIdCategory.setAttribute("type", "hidden");
        inputIdCategory.setAttribute("name", "id");
        inputIdCategory.setAttribute("value", id);
        form.appendChild(inputIdCategory);

        var inputMode = document.createElement("input");
        inputMode.setAttribute("type", "hidden");
        inputMode.setAttribute("name", "mode");
        inputMode.setAttribute("value", mode);
        form.appendChild(inputMode);

        var inputCsrf = document.createElement("input");
        inputCsrf.setAttribute("type", "hidden");
        inputCsrf.setAttribute("name", csrfParamName);
        inputCsrf.setAttribute("value", csrfValue);
        form.appendChild(inputCsrf);

        form.submit();
      });
    });

  });

})(window, document, window.jQuery);

// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('#view-order').each(function () {

            $('[name="shippingNumber"]').each(function () {
                var $this = $(this);
                var idOrder = $this.data('id');

                $this.focusout(function (e) {
                    var value = $(this).val();

                    $.ajax({
                        url: '/admin/api/order/shipping-number/' + idOrder + '/' + value,
                        method: 'PATCH',
                        contentType: "application/json"
                    }).done(function (data) {
                        if (data.result === 'success') {
                            $.notify("데이터가 수정되었습니다.", {status: "success"});
                        }
                    }).fail(function (jqXHR, textStatus) {

                        if (jqXHR.status.toString().startsWith("4")) {
                            $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                        } else {
                            $.notify(jqXHR.responseJSON.message, {status: "danger"});
                        }
                    });
                });
            });
        });
    });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('#view-order').each(function () {

            $('select[name="orderProductStatus"]').each(function () {
                var $this = $(this);
                var idOrderProduct = $this.data('id');

                $this.on('change', function (e) {
                    var status = $(this).val();

                    $.ajax({
                        url: '/admin/api/order/order-product-status/' + idOrderProduct + '/' + status,
                        method: 'PATCH',
                        contentType: "application/json"
                    }).done(function (data) {
                        if (data.result === 'success') {
                            $.notify("데이터가 수정되었습니다.", {status: "success"});
                        }
                    }).fail(function (jqXHR, textStatus) {

                        if (jqXHR.status.toString().startsWith("4")) {
                            $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                        } else {
                            $.notify(jqXHR.responseJSON.message, {status: "danger"});
                        }
                    });
                });
            });
        });
    });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

  $(function () {
    $('#view-order').each(function () {

      $('select[name="orderStatus"]').each(function () {
        var $this = $(this);
        var idOrder = $this.data('id');

        $this.on('change', function (e) {
          var status = $(this).val();

          $.ajax({
            url: '/admin/api/order/order-status/' + idOrder + '/' + status,
            method: 'PATCH',
            contentType: "application/json"
          }).done(function (data) {
            if (data.result === 'success') {
              $.notify("데이터가 수정되었습니다.", {status: "success"});
              setTimeout(function () {
                window.location.reload();
              }, 1000);
            }
          }).fail(function (jqXHR, textStatus) {

            if (jqXHR.status.toString().startsWith("4")) {
              $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
            } else {
              $.notify(jqXHR.responseJSON.message, {status: "danger"});
            }
          });
        });
      });
    });
  });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('#view-order').each(function () {

            $('select[name="paymentStatus"]').each(function () {
                var $this = $(this);
                var idOrder = $this.data('id');

                $this.on('change', function (e) {
                    var status = $(this).val();

                    $.ajax({
                        url: '/admin/api/order/payment-status/' + idOrder + '/' + status,
                        method: 'PATCH',
                        contentType: "application/json"
                    }).done(function (data) {
                        if (data.result === 'success') {
                            $.notify("데이터가 수정되었습니다.", {status: "success"});
                        }
                    }).fail(function (jqXHR, textStatus) {

                        if (jqXHR.status.toString().startsWith("4")) {
                            $.notify("페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                        } else {
                            $.notify(jqXHR.responseJSON.message, {status: "danger"});
                        }
                    });
                });
            });
        });
    });

})(window, document, window.jQuery);
$(function () {

  $('#form-create-payment-test').each(function () {

    var $inputAvailablePoint = $('#available-point');
    var $inputMinPoint = $('#min-point');
    var $inputUnitPoint = $('#unit-point');
    var $inputAmount = $('[name="amount"]');
    var $inputDiscountCoupon = $('#discount-coupon');

    var amountCalc = function () {
      var amount = $inputAmount.val();
      $inputAmount.val(amount);
    };

    var selectItem = function () {

      var subtotal = 0;

      $('#product-tbody').html('');
      $('#products-input').html('');
      $('#id-carts-input').html('');

      $("input[name=selectCheckbox]:checked").each(function () {
        var $this = $(this);

        // === 제품
        var $pId = $this.data('productId');
        var $pName = $this.data('productName');
        var $pPrice = $this.data('productPrice');
        var $pQty = $this.data('productQty');
        var $pDc = $this.data('productDc');
        var $pSubTotal = $this.data('productSubTotal');

        console.debug("id ::: " + $pId);
        console.debug("name ::: " + $pName);
        console.debug("price ::: " + $pPrice);
        console.debug("qty ::: " + $pQty);
        console.debug("dc ::: " + $pDc);
        console.debug("subtotal ::: " + $pSubTotal);

        $('#product-tbody').append('<tr>' +
          '<td class="text-center">' + $pId + '</td>' +
          '<td class="text-center">' + $pName + '</td>' +
          '<td class="text-center">' + numberWithCommas($pPrice - $pDc) + '원</td>' +
          '<td class="text-center">' + numberWithCommas($pQty) + '개</td>' +
          '<td class="text-center">' + numberWithCommas($pSubTotal) + '원</td>' +
          '</tr>');

        var productJson =
          '{' +
          '\"id\":' + $pId + ',' +
          '\"price\":' + $pPrice + ',' + // 원가
          '\"qty\":' + $pQty + ',' + // 수량
          '\"dc\":' + $pDc + ',' + // 개당 할인
          '\"amount\":' + $pSubTotal // 결제금액(소계)
          + '}';

        $('#products-input').append("<input type='hidden' name='products' value='" + productJson + "'/>");
        $('#id-carts-input').append('<input type="hidden" name="idCarts" value="' + $this.val() + '"/>');

        // === 계산식
        subtotal += Number($pSubTotal);
        console.debug("+ " + $pSubTotal);
      });


      console.debug("--------------------");
      console.debug("= " + subtotal);
      $inputAmount.val(subtotal);
      //

      amountCalc();
    };

    $('[data-type="checkPaymentTest"]').each(function () {

      $(this).click(function () {
        var chk = $(this).is(":checked");

        if (chk) {
          $("input[name=selectCheckbox]").prop("checked", true);
        } else {
          $("input[name=selectCheckbox]").prop("checked", false);
        }

        selectItem();
      })
    });

    $("input[name=selectCheckbox]").on('change', function () {
      selectItem();
    });

    $('#form-create-payment-test').parsley().on('form:submit', function () {

      var form = $('#form-create-payment-test');
      var data = form.serializeObject();

      console.debug(data, "data");

      var orderBody = {
        buyer: {
          fullname: data['buyer.fullname'],
          phone: data['buyer.phone'],
          email: data['buyer.email']
        },
        payMethod: data.payMethod,
        point: data.point ? Number(data.point) : 0,
        idCoupons: data.idCoupons ? data.idCoupons.split(',').map(function (idCoupon) {
          return Number(idCoupon);
        }) : [],
        subtotal: data.subtotal ? Number(data.subtotal) : 0,
        amount: data.amount ? Number(data.amount) : 0,
        idCarts: data.idCarts ? (isArray(data.idCarts) ? data.idCarts.map(function (idCart) {
          return Number(idCart);
        }) : [Number(data.idCarts)]) : [],
        products: data.products ? (isArray(data.products) ? data.products.map(function (product) {
          return product ? JSON.parse(product) : {};
        }) : [JSON.parse(data.products)]) : []
      };

      console.debug(orderBody, "orderBody");

      $.ajax({
          url: form.attr('action'),
          method: 'POST',
          contentType: "application/json",
          data: JSON.stringify(orderBody)
      }).done(function (result) {
          console.debug(result);
          $.notify("주문을 성공하였습니다.", {status:"success"});

      }).fail(function (jqXHR, textStatus) {
        var message = "(" + jqXHR.status + ") ";
        if (jqXHR.responseJSON && jqXHR.responseJSON.message) {
          message = message + jqXHR.responseJSON.message;
        }
        $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요.<br>Message: " + message, {status: "danger"});
      });

      return false;
    });

  });
});
(function (window, document, $, undefined) {

  $(function () {

    $('.es-product-create-update').each(function () {

      // variables
      var $selectCategory1 = $('[data-type="select-category-1"]');
      var $selectCategory2 = $('[data-type="select-category-2"]');
      var $selectCategory3 = $('[data-type="select-category-3"]');
      var $selectCategory4 = $('[data-type="select-category-4"]');
      var $selectCategory5 = $('[data-type="select-category-5"]');
      var $inputKeywordKoKr = $('input[name="keyword.textKoKr"]');
      var $inputKeywordEnUs = $('input[name="keyword.textEnUs"]');
      var $inputKeywordJaJp = $('input[name="keyword.textJaJp"]');
      var $inputKeywordZhCn = $('input[name="keyword.textZhCn"]');
      var $inputKeywordZhTw = $('input[name="keyword.textZhTw"]');

      var handleOnInitTagsInput = function () {
        $('[data-role="tagsinput"]').tagsinput('destroy');
        setTimeout(function () {
          $('[data-role="tagsinput"]').tagsinput();
        });
      };

      var setTags = function (idCategory) {
        $.ajax({
          url: '/admin/api/category-product/' + idCategory,
          method: 'GET',
          contentType: "application/json"
        }).done(function (result) {
          console.debug(result, 'result');
          if (result.tags) {
            console.debug($inputKeywordKoKr.val(), '$inputKeywordKoKr.val()');
            if (result.tags.textKoKr) {
              $inputKeywordKoKr.val(isEmpty($inputKeywordKoKr.val()) ? result.tags.textKoKr : $inputKeywordKoKr.val() + "," + result.tags.textKoKr);
            }
            if (result.tags.textEnUs) {
              $inputKeywordEnUs.val(isEmpty($inputKeywordEnUs.val()) ? result.tags.textEnUs : $inputKeywordEnUs.val() + "," + result.tags.textEnUs);
            }
            if (result.tags.textJaJp) {
              $inputKeywordJaJp.val(isEmpty($inputKeywordJaJp.val()) ? result.tags.textJaJp : $inputKeywordJaJp.val() + "," + result.tags.textJaJp);
            }
            if (result.tags.textZhCn) {
              $inputKeywordZhCn.val(isEmpty($inputKeywordZhCn.val()) ? result.tags.textZhCn : $inputKeywordZhCn.val() + "," + result.tags.textZhCn);
            }
            if (result.tags.textZhTw) {
              $inputKeywordZhTw.val(isEmpty($inputKeywordZhTw.val()) ? result.tags.textZhTw : $inputKeywordZhTw.val() + "," + result.tags.textZhTw);
            }

            handleOnInitTagsInput();
          }
        }).fail(function (jqXHR, textStatus) {
          if (jqXHR.status.toString().startsWith("4")) {
            $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
          } else {
            $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
          }
        });
      };

      $selectCategory1.on('change', function () {
        setTags($(this).val());
      });
      $selectCategory2.on('change', function () {
        setTags($(this).val());
      });
      $selectCategory3.on('change', function () {
        setTags($(this).val());
      });
      $selectCategory4.on('change', function () {
        setTags($(this).val());
      });
      $selectCategory5.on('change', function () {
        setTags($(this).val());
      });

    });

  });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

  $(function () {

    $('.es-product-create-update').each(function () {

      // variables
      var $selectLimitQtyMode = $('[name="limitQtyMode"]');
      var $inputLimitQty = $("input[name=limitQty]");

      var collapse = function(val) {
        if (val === 'NOT') {
          $inputLimitQty.closest('div').hide();
        } else {
          $inputLimitQty.closest('div').show();
        }
      };

      $selectLimitQtyMode.on('change', function () {
        collapse($(this).val());
      });

      collapse($('[name="limitQtyMode"]:checked').val());

    });

  });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

  $(function () {

    $('#list-product').each(function () {

      // variables
      var $checkAll = $("input[name=checkAll]");
      var $checkProduct = $("input[name=checkProduct]");
      var $updateForm = $('#form-list-update');
      var $removeBadgeForm = $('#form-list-remove-badge');
      var $filterForm = $('#form-list-filter');

      // check all
      $checkAll.on('click', function () {
        var chk = $(this).is(":checked");

        if (chk) {
          $checkProduct.prop("checked", true);
        } else {
          $checkProduct.prop("checked", false);
        }
      });

      // remove badge submit
      var $isClickRemoveBadge = false;
      $removeBadgeForm.parsley().on('form:submit', function () {

        if ($("input[name=checkProduct]:checked").length === 0) {
          alert('변경할 상품을 선택하세요.');
          return false;
        }

        if (isEmpty($('[name="idBadgeByRemove"]').val())) {
          alert('제거할 벳지를 선택하세요.');
          return false;
        }

        if ($isClickRemoveBadge) {
          return false;
        }
        $.notify("수정 중입니다...", {timeout: 30000});
        $isClickRemoveBadge = true;

        var $removeBadgeFormParams = $removeBadgeForm.serializeObject();
        var $filterFormParams = $filterForm.serializeObject();
        var $token = $("meta[name='_csrf']").attr("content");
        var $tokenParam = $("meta[name='_csrf_param']").attr("content");

        var $form = document.createElement("form");
        $form.setAttribute("method", "post");
        $form.setAttribute("action", $removeBadgeForm.attr('action'));
        document.body.appendChild($form);

        var $inputs = [];
        $inputs.push({name: $tokenParam, value: $token});

        $("input[name=checkProduct]:checked").each(function () {
          var $thisValue = $(this).val();
          $inputs.push({name: 'idProducts', value: $thisValue});
          console.log($thisValue);
        });

        for (var key in $removeBadgeFormParams) {
          $inputs.push({name: key, value: $removeBadgeFormParams[key]});
        }

        for (var key in $filterFormParams) {
          $inputs.push({name: key, value: $filterFormParams[key]});
        }

        $inputs.forEach(function (item) {
          var $input = document.createElement("input");
          $input.setAttribute("type", "hidden");
          $input.setAttribute("name", item.name);
          $input.setAttribute("value", item.value);
          $form.appendChild($input);
          console.debug(item.name + " ::: " + item.value);
        });

        $form.submit();

        return false;
      });

      // update submit
      var $isClickUpdate = false;
      $updateForm.parsley().on('form:submit', function () {

        if ($("input[name=checkProduct]:checked").length === 0) {
          alert('변경할 상품을 선택하세요.');
          return false;
        }

        if (isEmpty($('[name="updateSaleStatus"]').val())
          && isEmpty($('[name="updateDisplayStatus"]').val())) {
          alert('상태값을 선택하세요.');
          return false;
        }

        if ($isClickUpdate) {
          return false;
        }
        $.notify("수정 중입니다...", {timeout: 30000});
        $isClickUpdate = true;

        var $updateFormParams = $updateForm.serializeObject();
        var $filterFormParams = $filterForm.serializeObject();
        var $token = $("meta[name='_csrf']").attr("content");
        var $tokenParam = $("meta[name='_csrf_param']").attr("content");

        var $form = document.createElement("form");
        $form.setAttribute("method", "post");
        $form.setAttribute("action", $updateForm.attr('action'));
        document.body.appendChild($form);

        var $inputs = [];
        $inputs.push({name: $tokenParam, value: $token});

        $("input[name=checkProduct]:checked").each(function () {
          var $thisValue = $(this).val();
          $inputs.push({name: 'idProducts', value: $thisValue});
          console.log($thisValue);
        });

        for (var key in $updateFormParams) {
          $inputs.push({name: key, value: $updateFormParams[key]});
        }

        for (var key in $filterFormParams) {
          $inputs.push({name: key, value: $filterFormParams[key]});
        }

        $inputs.forEach(function (item) {
          var $input = document.createElement("input");
          $input.setAttribute("type", "hidden");
          $input.setAttribute("name", item.name);
          $input.setAttribute("value", item.value);
          $form.appendChild($input);
          console.debug(item.name + " ::: " + item.value);
        });

        $form.submit();

        return false;
      });

      // select delete
      var $isClickDelete = false;
      $('#btn-select-delete').on('click', function (e) {
        e.preventDefault();
        var $this = $(this);

        if ($("input[name=checkProduct]:checked").length === 0) {
          alert('삭제할 상품을 선택하세요.');
          return false;
        }

        if ($isClickUpdate) {
          return false;
        }
        $isClickUpdate = true;

        swal({
          title: "정말 데이터를 삭제하시겠습니까?",
          text: "삭제 후, 다시 복구할 수 없습니다.",
          type: "warning",
          showCancelButton: true,
          confirmButtonColor: "#DD6B55",
          confirmButtonText: "네, 삭제합니다.",
          cancelButtonText: "아니오.",
          closeOnConfirm: false,
          closeOnCancel: false
        }, function (isConfirm) {

          if (isConfirm) {

            var form = document.createElement("form");
            form.setAttribute("method", "post");
            form.setAttribute("action", $this.data('action'));
            document.body.appendChild(form);

            var token = $("meta[name='_csrf']").attr("content");
            var parameterName = $("meta[name='_csrf_param']").attr("content");

            var inputCsrf = document.createElement("input");
            inputCsrf.setAttribute("type", "hidden");
            inputCsrf.setAttribute("name", parameterName);
            inputCsrf.setAttribute("value", token);
            form.appendChild(inputCsrf);

            var $inputs = [];
            $("input[name=checkProduct]:checked").each(function () {
              var $thisValue = $(this).val();
              $inputs.push({name: 'idProducts', value: $thisValue});
            });

            $inputs.forEach(function (item) {
              var $input = document.createElement("input");
              $input.setAttribute("type", "hidden");
              $input.setAttribute("name", item.name);
              $input.setAttribute("value", item.value);
              form.appendChild($input);
              // console.debug(item.name + " ::: " + item.value);
            });

            form.submit();

          } else {
            swal("취소되었습니다.", "이 데이터는 그대로 유지됩니다.", "error");
          }
        });

      });

    });

  });

})(window, document, window.jQuery);
$(function () {
  $('[data-type="product-option"]').each(function () {
    $('[data-role="tagsinput"]').tagsinput({confirmKeys: [13, 9]});

    var $listOption = $('[data-type="list-option"]');
    var $listOptionValue = $('[data-type="list-option-value"]');
    var $template = $('#template-option-list').html();
    var $template2 = $('#template-option-value-list').html();


    $listOption.each(function () {
      var _list = $(this);
      var lang = _list.data('lang');

      if (lang === 'koKr') {

      } else if(lang === 'enUs') {
      } else if(lang === 'zhCn') {
      } else if(lang === 'zhTw') {
      } else if(lang === 'jaJp') {

      }
    });

    $('#btn-add-option').on('click', function (e) {
      e.preventDefault();


      $listOption.each(function () {
        var _list = $(this);
        var lang = _list.data('lang');

        _list.append(Mustache.render($template, {name:'option-title-' + lang}));
        // if (lang === 'ko-kr') {
        //
        // } else if(lang === 'en-us') {
        //   _list.append(Mustache.render($template, {name:''}));
        // } else if(lang === 'zh-cn') {
        //   _list.append(Mustache.render($template, {name:''}));
        // } else if(lang === 'zh-tw') {
        //   _list.append(Mustache.render($template, {name:''}));
        // } else if(lang === 'ja-jp') {
        //   _list.append(Mustache.render($template, {name:''}));
        // }
      });

      $('[data-role="tagsinput"]').tagsinput({confirmKeys: [13, 9]});

    });

    $('#submit-list-option').on('click', function (e) {
      e.preventDefault();

      $listOptionValue.each(function () {
        var _list = $(this);
        var lang = _list.data('lang');
        _list.append(Mustache.render($template2, {name:'option-value-title-' + lang}));
      });

    });
  });
});

/*
options.push({
  title: {
    internationalMode: {
      koKr: optionTitleKoKr,
      enUs: optionTitleEnUs,
      zhCn: optionTitleZhCn,
      zhTw: optionTitleZhTw,
      jaJp: optionTitleJaJp
    }
  }, // 옵션 제목
  code: null, // 상품 코드
  addPrice: 0, // 추가 가격
  price: 0, // 가격
  qty: 0, // 수량
  limit: 0 // 제한수량
});
*/
$(function () {
  $('[data-type="product-option-old"]').each(function () {

    var optionValue = {
      title: {
        internationalMode: {
          koKr: null,
          enUs: null,
          zhCn: null,
          zhTw: null,
          jaJp: null
        }
      },
      items: []
    };

    var option = {
      title: {
        internationalMode: {
          koKr: null,
          enUs: null,
          zhCn: null,
          zhTw: null,
          jaJp: null
        }
      }, // 옵션 제목
      code: null, // 상품 코드
      addPrice: 0, // 추가 가격
      price: 0, // 가격
      qty: 0, // 수량
      limit: 0 // 제한수량
    };

    var options = [];
    var optionValues = [];

    var $container = $(this);

    var koKr = $container.data('koKr');
    var enUs = $container.data('enUs');
    var zhCn = $container.data('zhCn');
    var zhTw = $container.data('zhTw');
    var jaJp = $container.data('jaJp');

    var $wrapOptionValueList = $('[data-type="wrap-option-value-list"]');
    var $wrapOptionList = $('[data-type="wrap-option-list"]');
    var $btnAdd = $container.find('#btn-add-option-ss');

    var $inputOptionTitleKoKr = $container.find('[name="option-title-kokr"]');
    var $inputOptionTitleEnUs = $container.find('[name="option-title-enus"]');
    var $inputOptionTitleZhCn = $container.find('[name="option-title-zhcn"]');
    var $inputOptionTitleZhTw = $container.find('[name="option-title-zhtw"]');
    var $inputOptionTitleJaJp = $container.find('[name="option-title-jajp"]');

    window.deleteOption = function (e, index) {
      e.preventDefault();
      alert(index);
      options.splice(index, 1);
      $(e.target).closest('.row').remove();
    };

    var onChangeOptionValue = function () {

      $wrapOptionValueList.html('');

      optionValues.forEach(function (optionValue, index) {

        $wrapOptionValueList.append('<div class="row" data-index="' + index + '">' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">옵션 제목</label>' +
            '   <input type="text" class="form-control" value="' + (koKr ? optionValue.title.internationalMode.koKr : optionValue.title.internationalMode.zhCn) + '"/>' +
            '   <input name="optionTitle" type="hidden" value="' + JSON.stringify(optionValue.title) + '"/>' +
            ' </div>' +
            '</div>' +

            '<div class="col-sm-12">' +

            '<div class="row">' +

            '          <div class="col-sm-2">' +
            '            <div class="form-group">' +
            '              <label class="control-label">옵션 값 (국문)</label>' +
            '              <input name="option-value-kokr" type="text" class="form-control" ' + (koKr ? '' : 'disabled') + '/>' +
            '            </div>' +
            '          </div>' +

            '          <div class="col-sm-2">' +
            '            <div class="form-group">' +
            '              <label class="control-label">옵션 값 (영문)</label>' +
            '              <input name="option-value-enus" type="text" class="form-control" ' + (enUs ? '' : 'disabled') + '/>' +
            '            </div>' +
            '          </div>' +

            '          <div class="col-sm-2">' +
            '            <div class="form-group">' +
            '              <label class="control-label">옵션 값 (중문(간체))</label>' +
            '              <input name="option-value-zhcn" type="text" class="form-control" ' + (zhCn ? '' : 'disabled') + '/>' +
            '            </div>' +
            '          </div>' +

            '          <div class="col-sm-2">' +
            '            <div class="form-group">' +
            '              <label class="control-label">옵션 값 (중문(번체)</label>' +
            '              <input name="option-value-zhtw" type="text" class="form-control" ' + (zhTw ? '' : 'disabled') + '/>' +
            '            </div>' +
            '          </div>' +

            '          <div class="col-sm-2">' +
            '            <div class="form-group">' +
            '              <label class="control-label">옵션 값 (일문)</label>' +
            '              <input name="option-value-jajp" type="text" class="form-control" ' + (jaJp ? '' : 'disabled') + '/>' +
            '            </div>' +
            '          </div>' +

            '        <div class="col-sm-2">' +
            '          <div class="form-group">' +
            '            <label class="control-label text-center"></label>' +
            '            <div class="clearfix">' +
            '              <button id="btn-remove-option-value" class="btn btn-default pull-left" >삭제</button>' +
            '            </div>' +
            '          </div>' +
            '        </div>' +
            '      </div>' +


            '</div>' +
            '</div>'
        );
        console.debug(option);

        $('[name="optionCode"]').tagsinput({confirmKeys: [13, 9]});
      })
    };
    var onChangeOption = function () {

      $wrapOptionList.html('');

      options.forEach(function (option, index) {

        $wrapOptionList.append('<div class="row" data-index="' + index + '">' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">옵션 제목</label>' +
            '   <input name="optionTitle" type="text" class="form-control" value="' + (koKr ? option.title.internationalMode.koKr : option.title.internationalMode.zhCn) + '"/>' +
            ' </div>' +
            '</div>' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">상품코드</label>' +
            '   <input name="optionCode" type="text" class="form-control"/>' +
            ' </div>' +
            '</div>' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">추가금액</label>' +
            '   <input name="optionAddPrice" type="number" class="form-control"/>' +
            ' </div>' +
            '</div>' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">수량</label>' +
            '   <input name="optionQty" type="number" class="form-control"/>' +
            ' </div>' +
            '</div>' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">제한수량</label>' +
            '   <input name="optionLimit" type="number" class="form-control"/>' +
            ' </div>' +
            '</div>' +

            '<div class="col-sm-2">' +
            ' <div class="form-group">' +
            '   <label class="control-label">삭제</label><br/>' +
            '   <button class="btn btn-default" onclick="deleteOption(event , ' + index + ')">삭제</button>' +
            ' </div>' +
            '</div>' +

            '</div>');
        console.debug(option);
      })
    };


    var showOptionField = function () {
      var isSkuField = $container.find('[name="skuField"]:checked').val() == 'true';

      if (isSkuField) {
        $('[data-type="option-field-body"]').show();

      } else {
        $('[data-type="option-field-body"]').hide();
      }
    };

    $container.find('[name="skuField"]').on('change', function () {

      showOptionField();

    });


    $btnAdd.on('click', function (e) {
      e.preventDefault();

      var optionTitleKoKr = $inputOptionTitleKoKr.val() ? $inputOptionTitleKoKr.val() : null;
      var optionTitleEnUs = $inputOptionTitleEnUs.val() ? $inputOptionTitleEnUs.val() : null;
      var optionTitleZhCn = $inputOptionTitleZhCn.val() ? $inputOptionTitleZhCn.val() : null;
      var optionTitleZhTw = $inputOptionTitleZhTw.val() ? $inputOptionTitleZhTw.val() : null;
      var optionTitleJaJp = $inputOptionTitleJaJp.val() ? $inputOptionTitleJaJp.val() : null;

      if (koKr && (optionTitleKoKr == null || optionTitleKoKr.length === 0)) {
        $.notify("옵션제목(국문)을 입력하세요.", {status: "warning"});
        return;
      }
      if (enUs && (optionTitleEnUs == null || optionTitleEnUs.length === 0)) {
        $.notify("옵션제목(영문)을 입력하세요.", {status: "warning"});
        return;
      }
      if (zhCn && (optionTitleZhCn == null || optionTitleZhCn.length === 0)) {
        $.notify("옵션제목(중문(간체))을 입력하세요.", {status: "warning"});
        return;
      }
      if (zhTw && (optionTitleZhTw == null || optionTitleZhTw.length === 0)) {
        $.notify("옵션제목(중문(번체))을 입력하세요.", {status: "warning"});
        return;
      }
      if (jaJp && (optionTitleJaJp == null || optionTitleJaJp.length === 0)) {
        $.notify("옵션제목(일문)을 입력하세요.", {status: "warning"});
        return;
      }

      optionValues.push({
        title: {
          internationalMode: {
            koKr: optionTitleKoKr,
            enUs: optionTitleEnUs,
            zhCn: optionTitleZhCn,
            zhTw: optionTitleZhTw,
            jaJp: optionTitleJaJp
          }
        },
        items: []
      });

      onChangeOptionValue();
    });

    // init
    showOptionField();
  });
});

/*
options.push({
  title: {
    internationalMode: {
      koKr: optionTitleKoKr,
      enUs: optionTitleEnUs,
      zhCn: optionTitleZhCn,
      zhTw: optionTitleZhTw,
      jaJp: optionTitleJaJp
    }
  }, // 옵션 제목
  code: null, // 상품 코드
  addPrice: 0, // 추가 가격
  price: 0, // 가격
  qty: 0, // 수량
  limit: 0 // 제한수량
});
*/
/**
 * 상품 가격 관리
 * 2019.04.04 deokin
 * */
$(function () {
  $('[data-type="product-price"]').each(function () {
    var $this = $(this);
    var $radioMembershipPrice = $this.find('[name="membershipPrice"]'); // 회원가 적용 라디오
    var $membershipPriceActive = $('[data-membership-price-active]'); // 마진 등급 테이블

    var $inputOriginPrice = $this.find('#originPrice'); // 제품 원가 인풋(디폴트)
    var $inputProductionPrice = $this.find('[name="productionCost"]'); // 판매 소비자가 인풋(디폴트)
    var $inputMarginProductionPrice = $this.find('#margin-production-cost'); // 마진 인풋(디폴트)

    var $inputMembershipPrices = $this.find('[data-origin-price]'); // 등급별 판매 소비자가 인풋들
    var $inputDiscountRates = $this.find('[data-discount-rate]'); // 등급별 할인율 인풋들

    // 회원가 활성 상태 변경 시, 마진 테이블 숨김처리.
    var collapse = function () {
      if ($radioMembershipPrice.filter(':checked').val() === 'true') {
        $membershipPriceActive.show()
      } else {
        $membershipPriceActive.hide()
      }
    };
    $radioMembershipPrice.on('change', function () {
      collapse();
    });
    collapse();

    // 할인율 반영된 금액 계산 함수.
    var handleCalculatePrice = function (originPrice, discountRate) {
      var result = 0;
      if (!isEmpty(originPrice) && !isEmpty(discountRate)) {
        result = parseFloat(originPrice) * ((100 - parseFloat(discountRate)) / 100);
      }
      return result.toFixed(2)
    };

    // 할인된 금액의 할인율 계산 함수.
    var handleCalculateRate = function (originPrice, price) {
      var result = 0;
      if (!isEmpty(originPrice) && !isEmpty(price)) {
        var rate = 1 - (parseFloat(price) / parseFloat(originPrice))
        result = rate * 100;
      }
      return result.toFixed(1)
    };

    // 마진 계산 함수.
    var handleCalculateMargin = function (originPrice, price) {
      var result = 0;
      if (!isEmpty(originPrice) && !isEmpty(price)) {
        result = parseFloat(price) - parseFloat(originPrice);
      }
      return result.toFixed(2)
    };

    /*
    * 인풋 값 세팅 함수.
    * */
    // 등급별 판매가 계산/적용.(기본 판매 소비자가 기준으로 계산됨.)
    var handleSetMembershipPrice = function (defaultPrice) {
      $inputMembershipPrices.each(function (index) {
        var $this_price = $(this);
        var calcPrice = handleCalculatePrice(defaultPrice, $('#discountRate-' + index).val());
        $this_price.val(calcPrice).change();
      })
    };

    // 제품 원가 변경 시.
    $inputOriginPrice.on('change', function () {
      var defaultPrice = $inputOriginPrice.val() * 2; // 제품원가의 2배 기본 설정.

      // 판매소비자가(디폴트)
      $inputProductionPrice.val(defaultPrice);

      // 마진(디폴트) 계산
      $inputMarginProductionPrice.val(handleCalculateMargin($inputOriginPrice.val(), defaultPrice));

      // 등급별 판매가 계산/적용.(기본 판매 소비자가 기준으로 계산됨.)
      handleSetMembershipPrice(defaultPrice);
    });

    // 판매소비자(디폴트)가 변경 시.
    $inputProductionPrice.on('change', function () {
      var defaultPrice = $inputProductionPrice.val();

      // 마진(디폴트) 계산
      $inputMarginProductionPrice.val(handleCalculateMargin($inputOriginPrice.val(), defaultPrice)); // 마진(디폴트) 마진

      // 등급별 판매가 계산/적용.(기본 판매 소비자가 기준으로 계산됨.)
      handleSetMembershipPrice(defaultPrice);
    });

    // 등급 별 판매 소비자가 변경 시.
    $inputMembershipPrices.each(function (index) {
      var $this_price = $(this);
      $this_price.on('change', function () {

        var calcRate = handleCalculateRate($inputProductionPrice.val(), $this_price.val());
        var calcPrice = handleCalculateMargin($inputOriginPrice.val(), $this_price.val());

        // 각 판매 소비자가의 마진 계산/적용(할인율 적용).
        $('#margin-' + index).val(calcPrice);
        $('#discountRate-' + index).val(calcRate);
      });
    });

    // 등급 별 할인율 변경 시.
    $inputDiscountRates.each(function (index) {
      var $this_rate = $(this);
      $this_rate.on('change', function () {

        var calcPrice = parseFloat($inputProductionPrice.val()) * ((100 - parseFloat($this_rate.val())) / 100);

        // 각 판매 소비자가의 마진 계산/적용(할인율 적용).
        $('#price-' + index).val(calcPrice.toFixed(2)).change();
      });
    });

    // 초기화
    if(!isEmpty($inputProductionPrice.val())){
      $inputProductionPrice.change()
    }

  });
});
$(function () {

  $('.es-product-create-update').each(function () {


    /* TODO (Start)Deprecated : 마진계산 코드 products/price.js 로 대체. 테스트 후 삭제.*/
    // var $container = $(this);
    // var $form = $container.find('form');
    // var $originPrice = $container.find('[name="originPrice"]');
    // var $productionCost = $container.find('[name="productionCost"]');
    // var $margin = $container.find('[name="margin"]');
    //
    // $form.keypress(function (e) {
    //   if (e.which === 13 || event.keyCode === 13 || e.which === 9 || event.keyCode === 9) {
    //     return false;
    //   }
    // });
    //
    // var calculate = function () {
    //   if ($productionCost.val() && $originPrice.val()) {
    //     console.debug($originPrice.val(), '1');
    //     console.debug($productionCost.val(), '2');
    //
    //     var margin = parseFloat($originPrice.val()) - parseFloat($productionCost.val());
    //     $margin.val(margin);
    //   }
    // };
    //
    // $originPrice.on('change', function () {
    //   calculate();
    // });
    //
    // $productionCost.on('change', function () {
    //   calculate();
    // });
    /* TODO (End)Deprecated*/

  });
});
(function (window, document, $, undefined) {

  $(function () {

    $('[data-type="update-order-sortable-product"]').each(function () {

      var $updateOrder = $(this);

      $updateOrder.find('[data-type="btn-order"]').on('click', function () {

        var $this = $(this);
        var wrapper = $this.closest('.wrapper-order');

        var actionUrl = wrapper.data('action');
        var idProduct = wrapper.data('idProduct');
        var idBrand = wrapper.data('idBrand');
        var mode = $this.data('mode');

        var csrfParamName = wrapper.data('csrfParamName');
        var csrfValue = wrapper.data('csrfValue');

        // Static 처리시 form 생성
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", actionUrl);
        document.body.appendChild(form);

        var inputIdProduct = document.createElement("input");
        inputIdProduct.setAttribute("type", "hidden");
        inputIdProduct.setAttribute("name", "idProduct");
        inputIdProduct.setAttribute("value", idProduct);
        form.appendChild(inputIdProduct);

        var inputIdBrand = document.createElement("input");
        inputIdBrand.setAttribute("type", "hidden");
        inputIdBrand.setAttribute("name", "idBrand");
        inputIdBrand.setAttribute("value", idBrand);
        form.appendChild(inputIdBrand);

        var inputMode = document.createElement("input");
        inputMode.setAttribute("type", "hidden");
        inputMode.setAttribute("name", "mode");
        inputMode.setAttribute("value", mode);
        form.appendChild(inputMode);

        var inputCsrf = document.createElement("input");
        inputCsrf.setAttribute("type", "hidden");
        inputCsrf.setAttribute("name", csrfParamName);
        inputCsrf.setAttribute("value", csrfValue);
        form.appendChild(inputCsrf);

        form.submit();
      });
    });

  });

})(window, document, window.jQuery);

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
/**
 * 적립금 지급 with VueJS
 * 2019.04.04 deokin
 * */
$(function () {
  $('[data-type="product-point"]').each(function () {
    var $this = $(this);
    var $initValue = $this.data('value');

    var vm_point = new Vue({
      el: $this[0],
      data: {
        membershipPoint: Boolean($initValue)
      }
    })
  })
});
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
// Custom jQuery
// -----------------------------------


(function(window, document, $, undefined){

    $(function(){

        $('#create-sample').each(function () {

            var BASIC_INFO_SIZE = 15;
            var index = 0;
            var list = $('#list-basic-info');
            var template = $('#template-item-basic-info').html();
            var plusBtn = $('#btn-plus-basic-info');

            // function
            var appendItem = function () {

                var itemId = '#item-basic-info-' + index;
                list.append(Mustache.render(template, {index: index}));

                $(itemId).find('.btn-delete').on('click', function (e) {
                    $(this).off('click');
                    $(itemId).remove();

                    if ($('.item-basic-info').length < BASIC_INFO_SIZE) {
                        plusBtn.show();
                    }
                });

                index++;

                if ($('.item-basic-info').length >= BASIC_INFO_SIZE) {
                    plusBtn.hide();
                }
            };

            // bind
            plusBtn.on('click', function () {

                if ($('.item-basic-info').length < BASIC_INFO_SIZE) {
                    appendItem();
                }
            });

            // init append
            appendItem();
        });
    });

})(window, document, window.jQuery);
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
// Custom jQuery
// ----------------------------------- 

(function (window, document, $, undefined) {

    $(function () {

        $('#create-sample').each(function () {

            var template = $('#template-item-image').html();

            var bindingSortable = function () {

                var btnDelete = $('.btn-image-uploader-delete');
                btnDelete.off();
                btnDelete.on('click', function () {
                    var _this = $(this);
                    _this.off();
                    _this.closest('.box-image-uploader').remove();
                });

                var _sortable = sortable('.sortable', {
                    forcePlaceholderSize: true,
                    placeholder: '<div class="box-image-uploader empty-image-uploader"></div>'
                });

                _sortable[0].addEventListener('sortstart', function (e) {
                    //console.debug('---- sortstart ----');
                    //console.debug(e.detail);
                    /*

                     This event is triggered when the user starts sorting and the DOM position has not yet changed.

                     e.detail.item contains the current dragged element
                     e.detail.placeholder contains the placeholder element
                     e.detail.startparent contains the element that the dragged item comes from

                     */
                });

                _sortable[0].addEventListener('sortstop', function (e) {
                    //console.debug('---- sortstop ----');
                    //console.debug(e.detail);
                    /*

                     This event is triggered when the user stops sorting. The DOM position may have changed.

                     e.detail.item contains the element that was dragged.
                     e.detail.startparent contains the element that the dragged item came from.

                     */
                });

                _sortable[0].addEventListener('sortupdate', function (e) {
                    //console.debug('---- sortupdate ----');
                    //console.debug(e.detail);
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
            };

            bindingSortable();

            $('#product-image-uploader').each(function () {

                var listThumb = $('#list-image-uploader');
                var _this = $(this);
                var inputFile = _this.find('#input-image-upload');
                var imageDropZone = _this;
                var limit = 2;

                inputFile.fileupload({
                    dataType: 'json',
                    sequentialUploads: true,
                    add: function (e, data) {

                        //console.debug("add");
                        //console.debug(data, 'data');

                        if (data.files.error) {
                            alert(data.files[0].error);
                        }

                        var uploadErrors = [];
                        var acceptFileTypes = /^image\/(jpe?g|png|gif|svg|blob)$/i;

                        if (data.originalFiles[0]['type'] != null && !acceptFileTypes.test(data.originalFiles[0]['type'])) {
                            uploadErrors.push('"gif", "jpeg", "jpg", "png", "svg", "blob" 이미지만 등록하세요.');
                        }

                        if (data.originalFiles[0]['size'] != null && data.originalFiles[0]['size'] > (1048576 * limit)) {
                            uploadErrors.push('파일이 너무 큽니다. 2MB이하로 등록하세요.');
                        }

                        if (uploadErrors.length > 0) {
                            alert(uploadErrors.join("\n"));
                        } else {
                            data.submit();
                        }
                    },
                    done: function (e, data) {
                        //console.debug("done");
                        //console.debug(data, 'data');
                        var list = data.result;

                        list.forEach(function (item) {
                            listThumb.append(Mustache.render(template, {image: item.url, index: $('.item-box-image').length}));
                        });

                        bindingSortable();
                    },

                    progressall: function (e, data) {
                        //console.debug("progressall");
                        var progress = parseInt(data.loaded / data.total * 100, 10);
                    },

                    fail: function (e, data) {
                        //console.debug(e);
                        //console.debug(data);
                        alert("서버와 통신 중 문제가 발생했습니다");
                    },
                    dropZone: imageDropZone
                });
            });
        });

    });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

    $(function () {

        $('#list-sample').each(function () {

            var OPTION_SIZE = 3;
            var index = 0;
            var list = $(this);
            var template = $('#template-item-product-option').html();
            var plusBtn = $('#anchor-add-option-item');

            // function
            var changeArticle = function () {
                var inputs = $('.input-product-option-value');
                if(inputs && inputs.length) {
                    inputs.each(function(){
                       var input = $(this);
                        console.log(input.val());
                    });
                }
            };

            var tagsInputListener = function (itemId) {
                var input = $(itemId).find('.input-product-option-value');
                input.on('itemAddedOnInit', function (event) {
                    // event.item: contains the item
                    console.log('itemAddedOnInit');
                });

                input.on('beforeItemAdd', function (event) {
                    // event.item: contains the item
                    // event.cancel: set to true to prevent the item getting added
                    console.log('beforeItemAdd');

                });

                input.on('itemAdded', function (event) {
                    // event.item: contains the item
                    console.log('itemAdded');
                    changeArticle();
                });

                input.on('beforeItemRemove', function (event) {
                    // event.item: contains the item
                    // event.cancel: set to true to prevent the item getting removed
                    console.log('beforeItemRemove');

                });

                input.on('itemRemoved', function (event) {
                    // event.item: contains the item
                    console.log('itemRemoved');
                    changeArticle();
                });
            };

            var appendItem = function () {

                var itemId = '#item-product-option-' + index;

                list.append(Mustache.render(template, {index: index}));

                $(itemId).find('.btn-delete').on('click', function (e) {
                    $(this).off('click');
                    $(itemId).find('.input-product-option-value').tagsinput('destroy');
                    $(itemId).remove();

                    if ($('.item-product-option').length < OPTION_SIZE) {
                        plusBtn.show();
                    }
                });

                $(itemId).find('.input-product-option-value').tagsinput();
                tagsInputListener(itemId);
                index++;

                if ($('.item-product-option').length >= OPTION_SIZE) {
                    plusBtn.hide();
                }
            };

            plusBtn.on('click', function () {

                if ($('.item-product-option').length < OPTION_SIZE) {
                    appendItem();
                }
            });

            // init append
            appendItem();

        });
    });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

    $(function () {
        window.checkUnload = false;

        //$('input, textarea').on('keydown', function () {
        //    window.checkUnload = true;
        //});
        //
        //window.onbeforeunload = function () {
        //    if (window.checkUnload) {
        //        return "이 페이지를 벗어나면 작성된 내용은 저장되지 않습니다.";
        //    }
        //};

        //$(window).on("beforeunload", function () {
        //    alert(window.checkUnload);
        //    if (window.checkUnload) return "이 페이지를 벗어나면 작성된 내용은 저장되지 않습니다.";
        //});

        $('#create-sample').each(function () {
            $('.chosen-select').chosen();
        });

        $('#form-create-sample').each(function () {
            $(this).on('submit', function () {
                var form = $(this);

                var data = form.serializeObject();
                console.debug(data, 'data');

                if (!data['images[0].url']) {
                    $('#product-image-validator').addClass("filled");
                } else {
                    $('#product-image-validator').removeClass("filled");
                }

                window.checkUnload = false;
                return true;
                //return false;
            })
        });
    });

})(window, document, window.jQuery);
// Custom jQuery
// ----------------------------------- 


(function (window, document, $, undefined) {

  $(function () {

    $('#form-update-buyer-level-setting').each(function () {
      var $form = $(this);

      var changeSelect = function () {
        var $selectEnabled = $form.find('[name="enabled"]:checked');
        var $buyerLevelSettingEnabled = $('[data-type="buyerLevelSettingEnabled"]');
        if ($selectEnabled.val() === 'false') {
          $buyerLevelSettingEnabled.attr("disabled", true);
          $buyerLevelSettingEnabled.attr("readonly", true);
        } else {
          $buyerLevelSettingEnabled.attr("disabled", false);
          $buyerLevelSettingEnabled.attr("readonly", false);
        }
      };

      $form.find('[name="enabled"]').on('change', function () {
        changeSelect();
      });

      changeSelect();
    })

  });

})(window, document, window.jQuery);
// Custom jQuery
// ----------------------------------- 


(function (window, document, $, undefined) {

  $(function () {

    $('#form-update-point-setting').each(function () {
      var $form = $(this);

      var changeSelect = function () {
        var $selectEnabled = $form.find('[name="enabled"]:checked');
        var $pointSettingEnabled = $('[data-type="pointSettingEnabled"]');
        if ($selectEnabled.val() === 'false') {
          $pointSettingEnabled.attr("disabled", true);
          $pointSettingEnabled.attr("readonly", true);
        } else {
          $pointSettingEnabled.attr("disabled", false);
          $pointSettingEnabled.attr("readonly", false);
        }
      };

      $form.find('[name="enabled"]').on('change', function () {
        changeSelect();
      });

      changeSelect();
    })

  });

})(window, document, window.jQuery);
// Custom jQuery
// ----------------------------------- 


(function (window, document, $, undefined) {

    $(function () {

        $('#form-update-setting').each(function () {
            var $form = $(this);

            var changeSelect = function () {
                var $selectInternational = $form.find('[name="international"]:checked');
                var $internationalMode = $('[data-type="internationalMode"]');
                if ($selectInternational.val() === 'false') {
                    $internationalMode.attr("disabled", true);
                    $internationalMode.attr("readonly", true);
                } else {
                    $internationalMode.attr("disabled", false);
                    $internationalMode.attr("readonly", false);
                }
            };

            $form.find('[name="international"]').on('change', function () {
                changeSelect();
            });

            changeSelect();
        })

    });

})(window, document, window.jQuery);
$(function () {

  $('[data-type="radio-sku"]').each(function () {

    var $this = $(this);

    var changeRadio = function () {
      var value = $('[name="skuField"]:checked').val();

      if (value == 'true') {
        $('[data-type="sku-fields"]').show();
      } else {
        $('[data-type="sku-fields"]').hide();
      }
    };

    $this.find('input').on('change', function () {
      changeRadio();
    })
  });
});
(function (window, document, $, undefined) {

    $(function () {

        $('#form-sms,#form-email').each(function () {

            $('#send-time-datetimepicker').datetimepicker({
                icons: {
                    time: 'fa fa-clock-o',
                    date: 'fa fa-calendar',
                    up: 'fa fa-chevron-up',
                    down: 'fa fa-chevron-down',
                    previous: 'fa fa-chevron-left',
                    next: 'fa fa-chevron-right',
                    today: 'fa fa-crosshairs',
                    clear: 'fa fa-trash',
                    close: 'fa fa-close'
                },
                locale: 'ko',
                format: "YYYYMMDDHHmmss",
                toolbarPlacement: 'default',
                showTodayButton: true,
                showClear: true,
                showClose: true,
                ignoreReadonly: true,
                allowInputToggle: true,
                widgetPositioning: {
                    horizontal: 'left',
                    vertical: 'auto'
                }
            });

            $('#send-time-datetimepicker').on('dp.change', function (e) {

                if (e.date && "format" in e.date)
                    $('#input-send-time').val(e.date.format('YYYYMMDDHHmmss'));
            });

            $('#msgBody').on('keyup', function (e) {

                var str = $(this).val();
                var $msgByteSize = $('#msg-byte-size');
                var $msgSize = $('#msg-size');
                if (!isEmpty(str)) {
                    var bytes = str.replace(/[\0-\x7f]|([0-\u07ff]|(.))/g, "$&$1$2").length;
                    $msgByteSize.html(bytes + " Bytes");
                    if (bytes > 2000) {
                        // $msgSize.html('MMS');
                    } else if (bytes > 90) {
                        // $msgSize.html('LMS');
                    } else {
                        // $msgSize.html('SMS');
                    }
                } else {
                    // $msgSize.html('SMS');
                    $msgByteSize.html('0 Bytes');
                }
            });
        });
    });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

  $(function () {

    $('[data-type="btn-sms"]').each(function () {

      $(this).on('click', function (e) {
        e.preventDefault();

        var $this = $(this);
        var title = $this.data('title') ? $this.data('title') : "";
        var message = $this.data('message') ? $this.data('message') : "";
        var recipients = $this.data('recipients') ? $this.data('recipients') : "";
        var username = $this.data('username') ? $this.data('username') : "";

        window.smsPopup = window.open("/admin/sms/popup?username=" + username + "&title=" + title + "&message=" + message + "&recipients=" + recipients, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=50,left=50,width=1000,height=800");
      });

    });
  });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('[data-type="btn-delete"]').each(function () {
            var $btn = $(this);

            $btn.on('click', function (e) {
                e.preventDefault();
                var _this = $(this);
                swal({
                    title: "정말 데이터를 삭제하시겠습니까?",
                    text: "삭제 후, 다시 복구할 수 없습니다.",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "네, 삭제합니다.",
                    cancelButtonText: "아니오.",
                    closeOnConfirm: false,
                    closeOnCancel: false
                }, function (isConfirm) {

                    if (isConfirm) {

                        var form = document.createElement("form");
                        form.setAttribute("method", "post");
                        form.setAttribute("action", _this.data('action'));
                        document.body.appendChild(form);

                        var token = $("meta[name='_csrf']").attr("content");
                        var parameterName = $("meta[name='_csrf_param']").attr("content");

                        var inputCsrf = document.createElement("input");
                        inputCsrf.setAttribute("type", "hidden");
                        inputCsrf.setAttribute("name", parameterName);
                        inputCsrf.setAttribute("value", token);
                        form.appendChild(inputCsrf);

                        var inputId = document.createElement("input");
                        inputId.setAttribute("type", "hidden");
                        inputId.setAttribute("name", "id");
                        inputId.setAttribute("value", _this.data("id"));
                        form.appendChild(inputId);

                        form.submit();

                    } else {
                        swal("취소되었습니다.", "이 데이터는 그대로 유지됩니다.", "error");
                    }
                });
            });
        });
    });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

  $(function () {

    // CHOSEN INIT
    $('[data-type="chosen-select"]').each(function () {
      var $this = $(this);
      $this.chosen();
    });

  });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        if ($.fn.datetimepicker) {
        var $datepickers = $('[data-type="datetimepicker"]');

            $datepickers.each(function () {

                var $format = $(this).data('format') === undefined ? "YYYY-MM-DD HH:mm:ss" : $(this).data('format');

                $(this).datetimepicker({
                    icons: {
                        time: 'fa fa-clock-o',
                        date: 'fa fa-calendar',
                        up: 'fa fa-chevron-up',
                        down: 'fa fa-chevron-down',
                        previous: 'fa fa-chevron-left',
                        next: 'fa fa-chevron-right',
                        today: 'fa fa-crosshairs',
                        clear: 'fa fa-trash',
                        close: 'fa fa-close'
                    },
                    locale: 'en',
                    format: $format,
                    toolbarPlacement: 'default',
                    showTodayButton: true,
                    showClear: true,
                    showClose: true,
                    ignoreReadonly: true,
                    allowInputToggle: true
                });
            });
        }
    });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('.timepicker-hhmm').each(function () {
            $(this).datetimepicker({
                format: 'HH:mm'
            });
        });

        $('.timepicker-lt').each(function () {
            $(this).datetimepicker({
                format: 'LT'
            });
        });
    });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

    $(function () {

        /**
         * WYSIWYG INIT
         */
        $('[data-type="froala-content"]').each(function () {
            // TODO http://d2.naver.com/helloworld/76650 -- 한글 변경작업
            var $this = $(this);
            var height = $this.data('height');
            var mode = $this.data('mode');

            height = height === undefined ? 600 : height;
            mode = mode === undefined ? 'ALL' : mode;

            var contentOption = {
                language: 'ko',
                zIndex: 2501,
                height: height,
                heightMax: 1200,
                placeholderText: '내용을 입력하세요.',
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
                requestHeaders: {},

                toolbarSticky: false
            };

            if (mode === 'ONLY_TEXT') {
                contentOption["toolbarButtons"]
                    = ['bold', 'italic', 'underline', 'strikeThrough', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'indent', 'outdent', 'html', 'undo', 'redo'];
                contentOption["quickInsertButtons"] = ['ul', 'ol', 'hr'];
            }


            contentOption.requestHeaders[window.csrf.header] = window.csrf.token;

            //https://www.froala.com/wysiwyg-editor/docs/server/java/image-upload
            $(this).on('froalaEditor.contentChanged froalaEditor.initialized', function (e, editor) {
                //console.debug(editor.html.get());
                //window.checkUnload = false;
//            $('pre#eg-previewer').text(editor.codeBeautifier.run(editor.html.get()))
//            $('pre#eg-previewer').removeClass('prettyprinted');
//        prettyPrint()
            }).froalaEditor(contentOption);
        });
    });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('#btn-leave-user').each(function () {
            var $btn = $(this);

            $btn.on('click', function (e) {
                e.preventDefault();
                var _this = $(this);
                swal({
                    title: "정말 회원을 탈퇴하시겠습니까?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "네, 탈퇴합니다.",
                    cancelButtonText: "아니오.",
                    closeOnConfirm: false,
                    closeOnCancel: false
                }, function (isConfirm) {

                    if (isConfirm) {

                        var form = document.createElement("form");
                        form.setAttribute("method", "post");
                        form.setAttribute("action", _this.data('action'));
                        document.body.appendChild(form);

                        var token = $("meta[name='_csrf']").attr("content");
                        var parameterName = $("meta[name='_csrf_param']").attr("content");

                        var inputCsrf = document.createElement("input");
                        inputCsrf.setAttribute("type", "hidden");
                        inputCsrf.setAttribute("name", parameterName);
                        inputCsrf.setAttribute("value", token);
                        form.appendChild(inputCsrf);

                        var inputId = document.createElement("input");
                        inputId.setAttribute("type", "hidden");
                        inputId.setAttribute("name", "id");
                        inputId.setAttribute("value", _this.data("id"));
                        form.appendChild(inputId);

                        form.submit();

                    } else {
                        swal("취소되었습니다.", "", "error");
                    }
                });
            });
        });
    });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('#btn-remove-privacy').each(function () {
            var $btn = $(this);

            $btn.on('click', function (e) {
                e.preventDefault();
                var _this = $(this);
                swal({
                    title: "정말 개인정보를 파기하시겠습니까?",
                    text: "파기 후, 다시 복구할 수 없습니다.",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "네, 파기합니다.",
                    cancelButtonText: "아니오.",
                    closeOnConfirm: false,
                    closeOnCancel: false
                }, function (isConfirm) {

                    if (isConfirm) {

                        var form = document.createElement("form");
                        form.setAttribute("method", "post");
                        form.setAttribute("action", _this.data('action'));
                        document.body.appendChild(form);

                        var token = $("meta[name='_csrf']").attr("content");
                        var parameterName = $("meta[name='_csrf_param']").attr("content");

                        var inputCsrf = document.createElement("input");
                        inputCsrf.setAttribute("type", "hidden");
                        inputCsrf.setAttribute("name", parameterName);
                        inputCsrf.setAttribute("value", token);
                        form.appendChild(inputCsrf);

                        var inputId = document.createElement("input");
                        inputId.setAttribute("type", "hidden");
                        inputId.setAttribute("name", "id");
                        inputId.setAttribute("value", _this.data("id"));
                        form.appendChild(inputId);

                        form.submit();

                    } else {
                        swal("취소되었습니다.", "", "error");
                    }
                });
            });
        });
    });

})(window, document, window.jQuery);
// Custom jQuery
// -----------------------------------


(function (window, document, $, undefined) {

    $(function () {

        $('#btn-restore-leave').each(function () {
            var $btn = $(this);

            $btn.on('click', function (e) {
                e.preventDefault();
                var _this = $(this);
                swal({
                    title: "탈퇴한 회원을 복구하시겠습니까?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "네, 복구합니다.",
                    cancelButtonText: "아니오.",
                    closeOnConfirm: false,
                    closeOnCancel: false
                }, function (isConfirm) {

                    if (isConfirm) {

                        var form = document.createElement("form");
                        form.setAttribute("method", "post");
                        form.setAttribute("action", _this.data('action'));
                        document.body.appendChild(form);

                        var token = $("meta[name='_csrf']").attr("content");
                        var parameterName = $("meta[name='_csrf_param']").attr("content");

                        var inputCsrf = document.createElement("input");
                        inputCsrf.setAttribute("type", "hidden");
                        inputCsrf.setAttribute("name", parameterName);
                        inputCsrf.setAttribute("value", token);
                        form.appendChild(inputCsrf);

                        var inputId = document.createElement("input");
                        inputId.setAttribute("type", "hidden");
                        inputId.setAttribute("name", "id");
                        inputId.setAttribute("value", _this.data("id"));
                        form.appendChild(inputId);

                        form.submit();

                    } else {
                        swal("취소되었습니다.", "", "error");
                    }
                });
            });
        });
    });

})(window, document, window.jQuery);
$(function () {

  $('[id="user-list"]').each(function () {
    var selectItem = function () {

      console.debug("selectItem");
      $("input[name=selectCheckbox]:checked").each(function () {
        var $this = $(this);

      });
    };

    $('[data-type="checkUserByBU"]').click(function () {
      var chk = $(this).is(":checked");

      if (chk) {
        $("input[name=selectCheckbox]").prop("checked", true);
      } else {
        $("input[name=selectCheckbox]").prop("checked", false);
      }

      selectItem();
    });

    $("input[name=selectCheckbox]").on('change', function () {
      selectItem();
    });
  });
});
// Custom jQuery
// -----------------------------------

(function (window, document, $, undefined) {

    $(function () {

        $('#form-update-password').each(function () {

            $(this).parsley().on('form:submit', function () {

                var form = $('#form-update-password');
                var data = form.serializeObject();

                $.ajax({
                    url: form.attr('action'),
                    method: 'POST',
                    contentType: "application/json",
                    data: JSON.stringify(data)
                }).done(function (result) {
                    $.notify(" Data has been modified.", {status: "success"});
                    $('#modal-update-password').modal('hide');
                    $('#form-update-password')[0].reset();

                }).fail(function (jqXHR, textStatus) {
                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status: "danger"});
                    } else {
                        $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status: "danger"});
                    }
                });

                return false;
            });
        });

        $('.user-create-update').each(function () {

            window.Parsley.addAsyncValidator('duplicate',
                function (xhr) {
                    //console.log(this.$element, 'this.$element'); // jQuery Object[ input[name="q"] ]
                    //console.log(xhr.status, 'xhr.status');
                    if (xhr.status == 200) {
                        if (xhr.responseJSON.result == 'duplicate') {
                            return false;
                        }
                        return true;
                    }
                    return false;
                }
            );

        });

    });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

  $(function () {

    $('[data-type="search-user"]').each(function () {

      var _this = $(this);
      var currentPage = 0;
      var inputSearchUser = _this.find('[data-type="input-search-user"]');
      var btnSearchUser = _this.find('[data-type="btn-search-user"]');
      var btnNext = $('.list-next');
      var listUsers = $('#list-jusos');
      var divListUsers = $('.list-users');

      btnNext.hide();

      var search = function () {

        var keyword = inputSearchUser.val();
        if (keyword === undefined || keyword.length === 0) {
          $.notify("검색어를 입력하세요.");
          return;
        }

        btnSearchUser.html("<i class='fa fa-spinner fa-spin'></i>");

        $.ajax({
          url: '/admin/api/user/search',
          method: 'GET',
          contentType: "application/json",
          data: {keyword: keyword, page: currentPage}
        }).done(function (result) {
          console.debug(result, 'result');
          btnSearchUser.html("검색");
          if (result) {
            currentPage = result.page.number;

            var totalCount = result.page.totalElements;
            if (totalCount === 0) {
              $.notify("검색된 회원이 없습니다.<br/> 검색어를 확인해보세요.", {status: "warning"});
              divListUsers.fadeOut();
              btnNext.hide();
              return;
            } else if (currentPage === 0) {
              $.notify("총 " + totalCount + "명의 회원이 검색되었습니다.", {status: "success"});
            }

            var countPerPage = result.page.size;
            var users = result._embedded.searchUserList;
            var isNext = false;

            if (countPerPage * (currentPage + 1) < totalCount) {
              isNext = true;
            }

            if (users && users.length > 0) {
              divListUsers.fadeIn();
              users.forEach(function (item) {

                var user = {
                  id: item.id,
                  email: item.email,
                  fullname: item.fullname
                };

                var template = $('#template-search-user').html();
                Mustache.parse(template);
                listUsers.append(Mustache.render(template, user));
              });

              if (isNext) {
                btnNext.show();
              } else {
                btnNext.hide();
              }

              $('.item-search-user').on('click', function () {

                var item = $(this);
                var id = item.data('id');
                var email = item.data('email');
                var fullname = item.data('fullname');

                $('[data-type="id"]').val(id);
                $('[data-type="userEmail"]').val(email);
                $('[data-type="userFullname"]').val(fullname);

                divListUsers.fadeOut('fast');
                inputSearchUser.val('');
              });

            } else {
              divListUsers.fadeOut();
            }
          }

        }).fail(function (jqXHR, textStatus) {
          btnSearchUser.html("검색");
          var message = "(" + jqXHR.status + ") ";
          if (jqXHR.responseJSON && jqXHR.responseJSON.message) {
            message = message + jqXHR.responseJSON.message;
          }
          $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요.<br>Message: " + message, {status: "danger"});
        });
      };

      btnNext.on('click', function () {
        currentPage++;
        search();
      });

      inputSearchUser.on('keydown', function (event) {
        if (event.keyCode === 13) {
          listUsers.children().off();
          listUsers.html('');
          currentPage = 0;
          event.preventDefault();
          search();
        }
      });

      btnSearchUser.on('click', function () {
        listUsers.children().off();
        listUsers.html('');
        currentPage = 0;
        search();
      });

    });

  });

})(window, document, window.jQuery);
(function (window, document, $, undefined) {

    $(function () {

        $('#form-update-password').each(function(){

            $(this).parsley().on('form:submit', function () {

                var form = $('#form-update-password');
                var data = form.serializeObject();

                $.ajax({
                    url: form.attr('action'),
                    method: 'POST',
                    contentType: "application/json",
                    data: JSON.stringify(data)
                }).done(function (result) {
                    $.notify("업로드 되었습니다.", {status:"success"});
                    $('#modal-update-password').modal('hide');
                    $('#form-update-password')[0].reset();

                }).fail(function (jqXHR, textStatus) {
                    if (jqXHR.status.toString().startsWith("4")) {
                        $.notify("현재페이지에 오류가 있습니다. 페이지를 새로고침(F5)하여 다시 이용해주세요.", {status:"danger"});
                    } else {
                        $.notify(textStatus.toUpperCase() + ": 관리자에게 문의하세요. <br>STATUS CODE: " + jqXHR.status, {status:"danger"});
                    }
                });

                return false;
            });
        });
    });

})(window, document, window.jQuery);
(function(window, document, $, undefined){

    $(function () {

        if ($('[data-parsley-remote-validator="duplicate"]').length > 0) {

            if (window.Parsley) {
                window.Parsley.addAsyncValidator('duplicate',
                    function (xhr) {
                        //console.log(this.$element, 'this.$element'); // jQuery Object[ input[name="q"] ]
                        //console.log(xhr.status, 'xhr.status');
                        if (xhr.status === 200) {
                            if (xhr.responseJSON.result === 'duplicate') {
                                return false;
                            }
                            return true;
                        }
                        return false;
                    }
                );
            }
        }
    });

})(window, document, window.jQuery);
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

(function (window, document, $, undefined) {

  $(function () {

    if ($('[data-parsley-remote-validator="possible"]').length > 0) {

      if (window.Parsley) {
        window.Parsley.addAsyncValidator('possible',
          function (xhr) {
            //console.log(this.$element, 'this.$element'); // jQuery Object[ input[name="q"] ]
            //console.log(xhr.status, 'xhr.status');
            if (xhr.status === 200) {
              return xhr.responseJSON.result === 'success';
            }
            return false;
          }
        );
      }
    }
  });

})(window, document, window.jQuery);