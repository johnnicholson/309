app.controller('homeController', ['$scope', '$state', 'login', '$http', 'notifyDlg', '$uibModal',
 function(scope, state, login, $http, nDlg, $uibM) {
   scope.goToLogin = function() {
      state.go('login');
   };

   scope.goToRegister = function() {
      state.go('register');
   };

}]);
