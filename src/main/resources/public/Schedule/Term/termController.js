app.controller('termController', ['$scope', '$state', '$http', 'login',
function(scope, $state, $http, login) {

  // Only admin users can edit
  scope.showEdit = login.isAdmin();

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
