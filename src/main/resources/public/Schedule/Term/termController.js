app.controller('termController', ['$scope', '$state', '$http',
function(scope, $state, $http) {

  // Grabs all terms from the server
  scope.fetchAllTerms = function() {
    $http({
      method: 'GET',
      url: 'api/term'
    })
    .then(function success(response) {
      scope.terms = response.data;
    })
    .catch(function error(response) {
      return notifyDlg.show($scope, "Could not fetch: " + response.status);
    });
  }

  // Fetch all terms when first instantiated
  scope.fetchAllTerms();
}]);
