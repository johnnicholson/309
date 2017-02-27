app.controller('addSectionController', ['$scope', '$state', '$http', 'notifyDlg', '$q', 'login','$stateParams', 'notifyDlg',
function($scope, $state, $http, nDlg, $q, login, params, notifyDlg) {

  $scope.termID = params.id;

  // Supply days of week data
  $scope.daysOfWeek = [
    {sName: "Sun", lName: "sunday"},
    {sName: "Mon", lName: "monday"},
    {sName: "Tue", lName: "tuesday"},
    {sName: "Wed", lName: "wednesday"},
    {sName: "Thu", lName: "thursday"},
    {sName: "Fri", lName: "friday"},
    {sName: "Sat", lName: "saturday"}
  ];

  // Generate array containing contiguous integers from
  // start to end inclusive
  function genRange(start, end) {
    var range = [];
    while (start <= end) {
      range.push(start);
      start += 1;
    }
    return range;
  }

  // Returns the human readable string for a time
  $scope.timeToReadable = function(tVal) {
    var reducedNum = tVal % 12 > 0 ? tVal % 12 : 12;
    return "" + reducedNum + (tVal / 12 < 1 ? " AM" : " PM");
  }

  // Returns db ready string for given time
  $scope.timeToDBReady = function(tVal) {
    return "" + tVal + ":00";
  }

  // Returns whether start and end time are valid
  // Assumed start and end time exist
  $scope.timeValid = function(section) {
    return section.startTime < section.endTime;
  }

  $scope.times = genRange(6, 23);


  // Gets

  // Fetches courses
  $scope.fetchAllCourses = function() {
    $http({
      method: 'GET',
      url: 'api/course'
    })
    .then(function success(response) {
      $scope.courses = response.data;
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not fetch: " + response.status);
    });
  };

  // Fetch professors
  $scope.fetchAllProfessors = function() {
    $http({
      method: 'GET',
      url: 'api/prss'
    })
    .then(function success(response) {
      $scope.users = response.data;
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not fetch: " + response.status);
    });
  };

  // Fetch rooms
  $scope.fetchAllRooms = function() {
    $http({
      method: 'GET',
      url: 'api/room'
    })
    .then(function success(response) {
      $scope.rooms = response.data;
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not fetch: " + response.status);
    });
  }

  // Called when a new course is selected
  // Wipes all previously filled out sections
  $scope.startNewSections = function() {
    var sections = [];

    // Get course, add its components to a new list
    var selectedComponents = $scope.selectedCourse.components;
    for (var cID in selectedComponents) {
      var curComp = selectedComponents[cID];
      var newSection = {};
      newSection.component = curComp;
      newSection.course = $scope.selectedCourse;
      sections.push(newSection);
    }

    // Set new components
    $scope.sections = sections;
  }

  // Replaces any necessary fields in the sections to prepare them for
  // the database.
  // * Changes startTime and endTime to be DB ready
  function createDBReadySections(inSections) {

    // Makes a deep copy of the sections passed in
    var sections = JSON.parse(JSON.stringify(inSections));

    // Iterate through and modify values
    for (var sKey in sections) {
      var section = sections[sKey];
      if (section.startTime) {
        section.startTime = $scope.timeToDBReady(section.startTime);
      }
      if (section.endTime) {
        section.endTime = $scope.timeToDBReady(section.endTime);
      }
    }
    return sections;
  }

  // Prepares and submits sections to db
  $scope.submitSections = function() {
    var dbReady = createDBReadySections($scope.sections);
    for (var sKey in dbReady) {
      var section = dbReady[sKey];

      $http.post("api/term/" + $scope.termID + "/section", section)
      .then(function(response) {
        return nDlg.show($scope, "New Section Added: " + section.name);
      }).catch(function(response) {
        return nDlg.show($scope, "Addition failed: " + section.name);
      })
    }
  }

  // Adds another empty section template
  $scope.addSection = function() {
    var newSection = {};
    newSection.course = $scope.selectedCourse;
    $scope.sections.push(newSection);
  }

  $scope.quit = function() {
    $state.go("term-edit", {id : $scope.termID});
  }

  $scope.fetchAllCourses()
  $scope.fetchAllProfessors();
  $scope.fetchAllRooms();
}]);
