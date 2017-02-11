app.controller('addEquipmentController', ['$scope', '$state', '$http', 'notifyDlg', function(scope, $state, $http, nDlg) {
  scope.equipment = {};

  scope.addEquipment = function() {
    $http.post("api/equip", scope.equipment)
    .then(function(response){
      $state.go('equipment');
      //if we have time we can show the dialog box where they click OK afterwards, for now going back to the equipment page is more important!
      // return nDlg.show(scope, "Addition succeeded.", );
    }).catch(function(response) {
      return nDlg.show(scope, "Addition failed.")
    })
  };

  scope.quit = function() {
    $state.go('equipment');
  };
}]);
