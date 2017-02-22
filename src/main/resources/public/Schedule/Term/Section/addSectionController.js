app.controller('addSectionController', ['$scope', '$state', '$http', 'notifyDlg', '$q', 'login','$stateParams', 'notifyDlg',
function($scope, $state, $http, nDlg, $q, login, params, notifyDlg) {

  $scope.termID = params.id;

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

  $scope.quit = function() {
    $state.go("term-edit", {id : $scope.termID});
  }

  $scope.fetchAllCourses()
  $scope.fetchAllProfessors();
}]);
