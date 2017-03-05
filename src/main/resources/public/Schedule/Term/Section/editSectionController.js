app.controller('editSectionController', ['$scope', '$state', '$http', 'notifyDlg', '$stateParams',
function(scope, $state, $http, nDlg, params) {


  scope.quit = function() {
    $state.go('rooms');
  };
}]);
