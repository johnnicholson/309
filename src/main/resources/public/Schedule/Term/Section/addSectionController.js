app.controller('addSectionController', ['$scope', '$state', '$http', 'notifyDlg', '$q', 'login','$stateParams', 'notifyDlg',
function($scope, $state, $http, nDlg, $q, login, params, notifyDlg) {

  $scope.termID = params.id;

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

  $scope.printSections = function() {
    console.log($scope.sections);
  }

  $scope.quit = function() {
    $state.go("term-edit", {id : $scope.termID});
  }

  $scope.fetchAllCourses()
  $scope.fetchAllProfessors();
}]);
