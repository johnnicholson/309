app.controller('homeController', ['$scope', '$state', 'login', '$http', 'notifyDlg', '$uibModal',
 function(scope, state, login, $http, nDlg, $uibM) {
   scope.user={};

  //  scope.goToLogin = function() {
      // state.go('login');
  //  };

   scope.goToRegister = function() {
      state.go('register');
   };

   scope.login = function() {
     console.log("inside login");
     login.login(scope.user)
     .then(function(response) {
       state.go('welcome');
     });
   };

}]);
