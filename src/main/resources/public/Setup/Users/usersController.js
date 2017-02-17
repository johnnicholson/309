app.controller('usersController', ['$scope', '$state', '$http', 'login', 'notifyDlg',
function ($scope, $state, $http, login, notifyDlg) {

  $scope.showEdit = login.isAdmin();
  // Define users endpoint interface
  fetchAllUsers= function() {
    $http({
      method: 'GET',
      url: 'api/prss'
    }).then(function success(response) {
      $scope.users = response.data;
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not fetch: " + response.status);
    });
  };

  // Consumes an userID to delete and attempts to delete it
  $scope.removeUser = function(userId) {
    $http({
      method: 'DELETE',
      url: 'api/prss/' + userId
    })
    .then(function success(response) {
      // If successful, fetch user list
      $scope.fetchAllUsers();
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not delete user: " + response.status);
    });
  };

  // Fetch users on startup
  fetchAllUsers();
}]);
