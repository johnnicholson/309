app.controller('equipmentController', ['$scope', '$state', '$http',
  function ($scope, $state, $http) {

    // Define equipment endpoint interface
    fetchAllEquip = function() {
        $http({
            method: 'GET',
            url: 'api/equip'
        }).then(function success(response) {
            $scope.equipment = response.data;
        });
    };

    // Fetch equip on startup
    fetchAllEquip();
  }]);
