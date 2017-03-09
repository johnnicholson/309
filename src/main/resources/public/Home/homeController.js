app.controller('homeController', ['$scope', '$state', 'login', '$http', 'notifyDlg', '$uibModal',
 function(scope, state, login, $http, nDlg, $uibM) {
   scope.user={};

   scope.goToRegister = function() {
      state.go('register');
   };

   scope.login = function() {
     console.log("inside login");
     login.login(scope.user)
     .then(function(response) {
       state.go('term-home');
     });
   };

   var studentUser = {username : "student@11test.edu", password : "passB"};
   scope.signInAsStudent = function() {
     login.login(studentUser)
     .then(function(response) {
       state.go('term-home');
     });
   }
}]);
