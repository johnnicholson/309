app.controller('roomTypeController', ['$scope', '$state', '$http', 'login', 'notifyDlg',
  function ($scope, $state, $http, login, notifyDlg) {
    $scope.showEdit = login.isAdmin();

    // Define room type endpoint interface
    $scope.fetchAllRoomTypes = function() {
        $http({
            method: 'GET',
            url: 'api/roomType'
        }).then(function success(response) {
            $scope.roomTypes = response.data;
        });
    };

    $scope.removeRoomType = function(roomTypeID) {
      $http({
        method: 'DELETE',
        url: 'api/roomType/' + roomTypeID
      })
      .then(function success(response) {
        $scope.fetchAllRoomTypes();
      }).catch(function error(response) {
        return notifyDlg.show($scope, "Could not delete Room Type: " + response.status)
      });
    };

    // Fetch equip on startup
    $scope.fetchAllRoomTypes();
  }]);
