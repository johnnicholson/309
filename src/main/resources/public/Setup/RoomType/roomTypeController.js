app.controller('roomTypeController', ['$scope', '$state', '$http',
  function ($scope, $state, $http) {

    // Define room type endpoint interface
    fetchAllRoomTypes = function() {
        $http({
            method: 'GET',
            url: 'api/roomType'
        }).then(function success(response) {
            $scope.roomTypes = response.data;
        });
    };

    // Fetch equip on startup
    fetchAllRoomTypes();
  }]);
