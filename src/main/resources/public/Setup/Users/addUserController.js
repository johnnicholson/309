app.controller('addUserController', ['$scope', '$state', '$http', 'notifyDlg', '$q', 'login',
function(scope, $state, $http, nDlg, $q, login) {
  scope.user = {};

  scope.registerUser = function() {
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
    $http.put("api/prss/" + scope.user.id)
    .then(function(response) {
      return nDlg.show(scope, "User Modified")
    })
    .then(function(response) {
      $state.go('users');
    }).catch(function(response) {
      return nDlg.show(scope, "Modification failed.")
    })
  }
  scope.quit = function() {
    $state.go('users');
  };


  // scope.registerUser = function() {
  //   $http.post("/api/prss", scope.user)
  //   .then(function success(response) {
  //     return nDlg.show(scope, "New user registered.",
  //     "Success");
  //   })
  //   .then(function dlgAnswr(response) {
  //     $state.go('users');
  //   })
  //   .catch(function error(response) {
  //     return nDlg.show(scope, "Could not register user.",
  //     "Error");
  //   });
  // };
  //
  // scope.quit = function() {
  //   $state.go('users');
  // };
}]);
