app.controller('addEditComponentTypeController', ['$scope', '$state', '$http', 'notifyDlg', '$stateParams', function(scope, $state, $http, nDlg, params) {

  // Bind cType to scope if exists
  scope.cType = params.cType === undefined ? {} : params.cType;

  console.log("beginingn: ---")
  console.log("this is the id: " + scope.cType.id);
  console.log("and this is the ctype: " + scope.cType);
  console.log("---");

  scope.submitChange= function() {
    if (scope.cType.id === undefined) {
      $http.post("api/component/type", scope.cType)
      .then(function(response) {
        return nDlg.show(scope, "New Component Type Added")
      })
      .then(function(response){
        scope.quit();
      }).catch(function(response) {
        return nDlg.show(scope, "Addition failed.")
      })
    }
    else {
      $http.put("api/component/type/" + scope.cType.id, scope.cType)
      .then(function(response) {
        return nDlg.show(scope, "Component Type Modified")
      })
      .then(function(response){
        scope.quit();
      }).catch(function(response) {
        return nDlg.show(scope, "Modification failed.")
      })
    }

  };

  scope.quit = function() {
    $state.go('componenttype');
  };
}]);
