app.controller('componentTypeController', ['$scope', '$state', '$http', 'notifyDlg', 'login',
function ($scope, $state, $http, notifyDlg, login) {

  // Only admin users can edit
  $scope.showEdit = login.isAdmin();

  // Define component type endpoint interface
  $scope.fetchAllComponentTypes = function() {
    $http({
      method: 'GET',
      url: 'api/component/type'
    })
    .then(function success(response) {
      $scope.componentTypes = response.data;
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not fetch: " + response.status);
    });
  };

  // Consumes a componentID to delete and attempts to delete it
  $scope.removeComponent = function(componentID) {
    $http({
      method: 'DELETE',
      url: 'api/component/type/' + componentID
    })
    .then(function success(response) {
      // If successful, refresh list
      $scope.fetchAllComponentTypes();
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not delete componentType: " + response.status);
    });
  };


  // Fetch equip on startup
  $scope.fetchAllComponentTypes();
}]);
