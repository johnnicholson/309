app.controller('addSectionController', ['$scope', '$state', '$http', 'notifyDlg', '$q', 'login','$stateParams',
function($scope, $state, $http, nDlg, $q, login, params) {

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

  $scope.fetchAllStaff = function() {
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
  }

  $scope.quit = function() {
    $state.go("term-edit", {id : $scope.termID});
  }

  $scope.fetchAllCourses()
  $scope.fetchAllStaff();
}]);
