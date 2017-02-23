app.controller('roomsController', ['$scope', '$state', '$http', 'notifyDlg', 'login',
  function ($scope, $state, $http, notifyDlg, login) {

  $scope.showEdit = login.isAdmin();
    // Define equipment endpoint interface
    fetchAllRooms = function() {
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
    };

    $scope.removeRoom = function(roomID) {
      $http({
        method: 'DELETE',
        url: 'api/room/' + roomID
      })
      .then(function success(response) {
        fetchAllRooms();
      })
      .catch(function error(response) {
        return notifyDlg.show($scope, "Could not delete room: " + response.status);
      });
    };

    // Fetch equip on startup
    fetchAllRooms();
  }]);
