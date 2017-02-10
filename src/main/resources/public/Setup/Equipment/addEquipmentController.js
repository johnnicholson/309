app.controller('addEquipmentController', ['$scope', '$state', '$http', 'notifyDlg', function(scope, $state, $http, nDlg) {
    scope.equipment = {};

    scope.addEquipment = function() {
      console.log("add equipment")
      $http.post("api/equip", scope.equipment)
      .then(function(response){
        return nDlg.show(scope, "Equipment addition succeeded.");
      })
    };

    scope.quit = function() {
      $state.go('home');
    };
}]);
