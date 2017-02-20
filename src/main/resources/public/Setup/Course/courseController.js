app.controller('courseController', ['$scope', '$state', '$http', 'notifyDlg', 'login', '$uibModal',
function ($scope, $state, $http, notifyDlg, login , $uibM) {

  // Only admin users can edit
  $scope.showEdit = login.isAdmin();

  files = undefined;

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

  $scope.loadFile = function() {
    $uibM.open({
      templateUrl: "Setup/Course/courseImport.template.html",
      scope: $scope
    }).result
    .then(function(result) {
      if (result === "QUIT")
        return $q.reject("QUIT");
      return $http({
        method: "POST",
        url: "/api/course/import",
        headers: {
          'Content-Type': undefined
        },
        data: {
          file: files[0]
        },
        transformRequest: function(data, headersGetter) {
          var formData = new FormData();
          angular.forEach(data, function(value, key) {
            formData.append(key, value);
          });
          var headers = headersGetter();
          return formData;
        }
      });
    })
    .then(function() {
      return notifyDlg.show($scope, "Courses Imported",
      "Success");
    }).then(function() {
      $state.reload();
    }).catch(function(result) {
      console.log(result);
      if (!(result === "QUIT" || result === 'backdrop click'))
        notifyDlg.show($scope, "Failed to Import Courses", "Error");
    });
  };

  $scope.storeFile = function(formFiles) {
    files = formFiles;
  };

  // Fetch equip on startup
  $scope.fetchAllCourses();
}]);
