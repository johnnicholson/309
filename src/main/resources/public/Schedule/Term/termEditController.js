app.controller('termEditController', ['$scope', '$state', '$http', '$stateParams', 'uiCalendarConfig', '$compile',
function($scope, $state, $http, $stateParams, config, $compile) {

  // Grabs specific term from the server
  $scope.fetchTerm = function(id) {
    $http({
      method: 'GET',
      url: 'api/term/' + id
    })
    .then(function success(response) {
      $scope.term = response.data;
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not fetch: " + response.status);
    });
  }

  // Fetch term when first loaded up
  $scope.fetchTerm($stateParams.id);

  var date = new Date();
  var d = date.getDate();
  var m = date.getMonth();
  var y = date.getFullYear();


  $scope.events = [
    {title: 'Birthday Party',start: new Date(y, m, d + 1, 19, 0),end: new Date(y, m, d + 1, 22, 30),allDay: false},
    {title: 'Stuff',start: new Date(y, m, d, 6, 0), end: new Date(y, m, d, 9, 0), allDay : false}
  ];


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
  $scope.eventSources = [$scope.events];

}]);
