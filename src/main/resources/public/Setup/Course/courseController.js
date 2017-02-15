app.controller('courseController', ['$scope', '$state', '$http', 'notifyDlg', 'login',
function ($scope, $state, $http, notifyDlg, login) {

  // Only admin users can edit
  $scope.showEdit = login.isAdmin();

  // Define equipment endpoint interface
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

  // Consumes an equipID to delete and attempts to delete it
  $scope.removeCourse = function(courseID) {
    $http({
      method: 'DELETE',
      url: 'api/course/' + courseID
    })
    .then(function success(response) {
      // If successful, fetch equipment list
      $scope.fetchAllCourses();
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not delete course: " + response.status);
    });
  };


  // Fetch equip on startup
  $scope.fetchAllCourses();
}]);
