app.controller('termEditController', ['$scope', '$state', '$http', '$stateParams', 'uiCalendarConfig', '$compile', 'notifyDlg',
function($scope, $state, $http, $stateParams, config, $compile, notifyDlg) {


  var date = new Date();
  var d = date.getDate();
  var m = date.getMonth();
  var y = date.getFullYear();

  $scope.sections = {
      events : [
        {title: 'Birthday Party',start: new Date(y, m, d + 1, 19, 0),end: new Date(y, m, d + 1, 22, 30),allDay: false}
      ]
  };
  $scope.eventSources = [$scope.sections];

  // Call this function to regenerate the events shown on the
  // calendar
  $scope.updateEvents = function(sections) {
    // Get rid of old events
    $scope.sections.events.splice(0);

    //Iterate through sections and add them to events
    for (var sId in sections) {
      var section = sections[sId];
      $scope.sections.events.push(
        {
          title: section.name,
          start: new Date(y, m, d + 1, 19, 0),
          end: new Date(y, m, d + 1, 22, 0),
          allDay: false
        }
      );
    }
    // console.log(newEvents);
    // console.log($scope.sections)
    // $scope.sections.events = newEvents;
    // console.log($scope.sections);
  }

  /* alert on eventClick */
  $scope.alertOnEventClick = function( date, jsEvent, view){
    console.log(date.title + ' was clicked ');
  };
  /* alert on Drop */
  $scope.alertOnDrop = function(event, delta, revertFunc, jsEvent, ui, view){
    $scope.alertMessage = ('Event Droped to make dayDelta ' + delta);
  };
  /* alert on Resize */
  $scope.alertOnResize = function(event, delta, revertFunc, jsEvent, ui, view ){
    $scope.alertMessage = ('Event Resized to make dayDelta ' + delta);
  };
  /* add and removes an event source of choice */

  /* Change View */
  $scope.changeView = function(view,calendar) {
    uiCalendarConfig.calendars[calendar].fullCalendar('changeView',view);
  };
  /* Change View */
  $scope.renderCalender = function(calendar) {
    if(uiCalendarConfig.calendars[calendar]){
      uiCalendarConfig.calendars[calendar].fullCalendar('render');
    }
  };
  /* Render Tooltip */
  $scope.eventRender = function( event, element, view ) {
    element.attr({'tooltip': event.title,
      'tooltip-append-to-body': true});
    $compile(element)($scope);
  };
  /* config object */
  $scope.uiConfig = {
    calendar:{
      height: 800,
      //This allows drag and drop
      //editable: true,
      header:{
        left: 'title',
        center: '',
        right: ''
      },
      defaultView: 'agendaWeek',
      columnFormat: 'dddd',
      titleFormat: 'YYYY',
      defaultDate: null,
      eventClick: $scope.alertOnEventClick,
      eventDrop: $scope.alertOnDrop,
      eventResize: $scope.alertOnResize,
      eventRender: $scope.eventRender
    }
  };

  /* event sources array*/
  //$scope.eventSources = [$scope.events];

  // Grabs specific term from the server
  $scope.fetchTerm = function(id) {
    $http({
      method: 'GET',
      url: 'api/term/' + id
    })
    .then(function success(response) {
      $scope.term = response.data;
      $scope.updateEvents($scope.term.sections);
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not fetch: " + response.status);
    });
  }

  // Fetch term when first loaded up
  $scope.fetchTerm($stateParams.id);

}]);
