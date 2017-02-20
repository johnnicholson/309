app.controller('addEquipmentController', ['$scope', '$state', '$http', 'notifyDlg', '$stateParams', function(scope, $state, $http, nDlg, params) {

  if (params.equip === undefined)
    scope.equipment = {};
  else
    scope.equipment = params.equip;



  scope.addEquipment = function() {
    if (scope.equipment.id === undefined) {
      $http.post("api/equip", scope.equipment)
      .then(function(response) {
        return nDlg.show(scope, "New Equipment Added")
      })
      .then(function(response){
        $state.go('equipment');
      }).catch(function(response) {
        return nDlg.show(scope, "Addition failed.")
      })
    } else {
      $http.put("api/equip/" + scope.equipment.id, scope.equipment)
      .then(function(response) {
        return nDlg.show(scope, "Equipment Modified")
      })
      .then(function(response){
        $state.go('equipment');
      }).catch(function(response) {
        return nDlg.show(scope, "Modification failed.")
      })
    }

  };

  scope.quit = function() {
    $state.go('equipment');
  };
}]);
