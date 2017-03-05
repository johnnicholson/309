app.controller('editSectionController', ['$scope', '$state', '$http', 'notifyDlg', '$stateParams',
function($scope, $state, $http, nDlg, params) {

  // Get reference to section
  $scope.section = params.section;

  $scope.quit = function() {
    $state.go('term-edit', {id : params.termID});
  };
}]);
