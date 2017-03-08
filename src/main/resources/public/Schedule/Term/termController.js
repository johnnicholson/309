app.controller('termController', ['$scope', '$state', '$http', 'login', 'notifyDlg',
function(scope, $state, $http, login, notifyDlg) {

  // Only admin users can edit
  scope.showEdit = login.isAdmin();

  scope.publishedOptions = [{name : "NO", val: 0}, {name : "YES", val: 1}];

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
      return notifyDlg.show(scope, "Could not fetch: " + response.status);
    });
  }

  scope.updateTerm = function(term) {
    $http.put('api/term/' + term.id, term)
    .then(function success(response) {
      return notifyDlg.show(scope, "Successfully updated published status.");
    })
    .catch(function error(response) {
      return notifyDlg.show(scope, "Could not update published status: " + response.status);
    });
  }

  // Fetch all terms when first instantiated
  scope.fetchAllTerms();
}]);
