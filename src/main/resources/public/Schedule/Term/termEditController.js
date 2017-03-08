app.controller('termEditController', ['$scope', '$state', '$http', '$stateParams', 'uiCalendarConfig', '$compile', 'notifyDlg', '$filter', 'login',
function($scope, $state, $http, $stateParams, config, $compile, notifyDlg, $filter, login) {

  // Get hold on termID for adding sections
  $scope.termID = $stateParams.id;

  // Only admin users can edit
  $scope.showEdit = login.isAdmin();

  //First sunday of 1980
  var date = new Date("January 6, 1980 11:13:00");
  var d = date.getDate();
  var m = date.getMonth();
  var y = date.getFullYear();

  $scope.sections = {
      events : [
        //  {title: 'Birthday Party',start: new Date(y, m, d, 19, 0),end: new Date(y, m, d, 22, 30),allDay: false}
     ]
  };
  $scope.eventSources = [$scope.sections];

  // Grabs all resources from endpoint and sets $scope[resAttr] to the result
  var fetchAllResourcesAtEndpoint = function(endPoint, resAttr) {
    $http({
      method: 'GET',
      url: endPoint
    })
    .then(function success(response) {
      $scope[resAttr] = response.data;
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not fetch filter data for " + resAttr + " : " + response.status);
    });
  }

  $scope.fetchFilterData = function() {
    fetchAllResourcesAtEndpoint('api/prss/', "users");
    fetchAllResourcesAtEndpoint('api/course/', "courses");
    fetchAllResourcesAtEndpoint('api/room/', "rooms");
  }

  // Only admins can filter as of now
  if (login.isAdmin()) {
    $scope.fetchFilterData();
  }

  // Call this function to regenerate the events shown on the
  // calendar
  $scope.updateEvents = function(sections) {

    var daysOfWeek = {
      "sunday" : 0,
      "monday" : 1,
      "tuesday" : 2,
      "wednesday" : 3,
      "thursday" : 4,
      "friday" : 5,
      "saturday" : 6
    }
    // Get rid of old events
    $scope.sections.events.splice(0);

    //Iterate through sections and add them to events
    for (var sId in sections) {
      var section = sections[sId];

      // Get start and end times
      var startTime = parseInt(section.startTime.split(/:/)[0], 10);
      var endTime = parseInt(section.endTime.split(/:/)[0], 10);

      // Add new event for each day of week
      for (var dow in daysOfWeek) {
        if (section[dow] === true) {
          $scope.sections.events.push(
            {
              title: section.name,
              start: new Date(y, m, d + daysOfWeek[dow], startTime, 0),
              end: new Date(y, m, d + daysOfWeek[dow], endTime, 0),
              allDay: false,
              section : section
            }
          );
        }
      }
    }
  }

  /* alert on eventClick */
  $scope.alertOnEventClick = function( sectionEvent, jsEvent, view){
    $scope.selectedSection = sectionEvent.section;
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
        left: '',
        center: '',
        right: ''
      },
      defaultView: 'agendaWeek',
      columnFormat: 'dddd',
      // titleFormat: 'YYYY',
      minTime: '06:00:00',
      defaultDate: date,
      eventClick: $scope.alertOnEventClick,
      eventDrop: $scope.alertOnDrop,
      eventResize: $scope.alertOnResize,
      eventRender: $scope.eventRender
    }
  };

  // Grabs specific term from the server
  $scope.fetchTerm = function(id) {
    $http({
      method: 'GET',
      url: 'api/term/' + id
    })
    .then(function success(response) {
      console.log(response.data);
      $scope.term = response.data;
      $scope.updateEvents($scope.term.sections);
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not fetch: " + response.status);
    });
  }

  // This reloads the calendar to reflect whatever filters are in place
  $scope.reload = function() {
    // Filter through the options
    // This could be optimized, as performance is not great as of now


    var sections = $filter('filter')($scope.term.sections,
      function(value, index, array) {
        var roomMatch = true, professorMatch = true, courseMatch = true;

        // If there is a room filter selected, check whether object is valid
        if ($scope.selectedRooms.length > 0) {
          if (value.room) {
            roomMatch = ($scope.selectedRooms.indexOf(value.room.id) > -1);
          }
          else {
            roomMatch = false;
          }
        }

        // Filter by courses if filter is selected
        if ($scope.selectedCourses.length > 0) {
          if (value.course) {
            courseMatch = ($scope.selectedCourses.indexOf(value.course.id) > -1);
          }
          else {
            courseMatch = false;
          }
        }

        // Filter by professors if filter is selected
        if ($scope.selectedProfessors.length > 0) {
          if (value.professor) {
            professorMatch = ($scope.selectedProfessors.indexOf(value.professor.id) > -1);
          }
          else {
            professorMatch = false;
          }
        }

        return roomMatch && courseMatch && professorMatch;
      });
      $scope.updateEvents(sections);
  }

  // Resets all filters to empty arrays
  var clearFilters = function() {
    $scope.selectedCourses = [];
    $scope.selectedProfessors = [];
    $scope.selectedRooms = [];
  }

  // Clear filters and force calendar to reload
  $scope.resetFilters = function() {
    clearFilters();
    $scope.reload();
  }

  clearFilters();
  // Fetch term when first loaded up
  $scope.fetchTerm($stateParams.id);

}]);
