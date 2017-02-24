
app.controller('termAddController', ['$scope', '$state', '$http', 'notifyDlg', '$stateParams',
function($scope, $state, $http, nDlg, params) {

  $scope.term = {isPublished : 0};

  // Posts new term and returns to term-home screen
  $scope.submit = function() {
    $http.post("api/term", $scope.term)
    .then(function(response){
      return nDlg.show($scope, "New term added")
    })
    .then(function(response){
      $scope.quit();
    }).catch(function(response) {
      return nDlg.show($scope, "Could not create term")
    })
  }

  // Goes back to term home
  $scope.quit = function() {
    $state.go('term-home');
  };
}]);
