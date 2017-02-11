app.controller('equipmentController', ['$scope', '$state', '$http', 'notifyDlg',
function ($scope, $state, $http, notifyDlg) {

  // Define equipment endpoint interface
  $scope.fetchAllEquip = function() {
    $http({
      method: 'GET',
      url: 'api/equip'
    })
    .then(function success(response) {
      $scope.equipment = response.data;
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not fetch: " + response.status);
    });
  };

  // Consumes an equipID to delete and attempts to delete it
  $scope.removeEquip = function(equipID) {
    $http({
      method: 'DELETE',
      url: 'api/equip/' + equipID
    })
    .then(function success(response) {
      // If successful, fetch equipment list
      $scope.fetchAllEquip();
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not delete equipment: " + response.status);
    });
  };


  // Fetch equip on startup
  $scope.fetchAllEquip();
}]);
