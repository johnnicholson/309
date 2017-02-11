app.controller('roomsController', ['$scope', '$state', '$http',
  function ($scope, $state, $http) {

    // Define equipment endpoint interface
    fetchAllRooms = function() {
        $http({
            method: 'GET',
            url: 'api/room'
        }).then(function success(response) {
            $scope.rooms = response.data;
        });
    };

    // Fetch equip on startup
    fetchAllRooms();
  }]);
