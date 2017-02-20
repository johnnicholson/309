app.controller('addUserController', ['$scope', '$state', '$http', 'notifyDlg', '$q', 'login','$stateParams',
function(scope, $state, $http, nDlg, $q, login, params) {
  if (params.user === undefined)
    scope.user = {};
  else {
    scope.user = params.user;

  }



  scope.registerUser = function() {
    if (scope.user.id === undefined) {
      $http.post("/api/prss", scope.user)
      .then(function success(response) {
        return nDlg.show(scope, "New user registered.",
        "Success");
      })
      .then(function dlgAnswr(response) {
        $state.go('users');
      }).catch(function (response){
        return nDlg.show(scope, "Addition failed.")
      })
    }
    else {
      $http.put("api/prss/" + scope.user.id, scope.user)
      .then(function(response) {
        return nDlg.show(scope, "User Modified")
      })
      .then(function(response) {
        $state.go('users');
      }).catch(function(response) {
        return nDlg.show(scope, "Modification failed.")
      })
    }
  };
  scope.quit = function() {
    $state.go('users');
  };
}]);
