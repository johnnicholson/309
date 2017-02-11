
app.controller('addRoomTypeController', ['$scope', '$state', '$http', 'notifyDlg', function(scope, $state, $http, nDlg) {
  scope.roomtype = {};

  scope.addRoomType = function() {
    $http.post("api/roomType", scope.roomtype)
    .then(function(response){
      $state.go('roomtypes');
      //if we have time we can show the dialog box where they click OK afterwards, for now going back to the equipment page is more important!
      // return nDlg.show(scope, "Addition succeeded.", );
    }).catch(function(response) {
      return nDlg.show(scope, "Addition failed.")
    })
  };

  scope.quit = function() {
    $state.go('roomtypes');
  };
}]);
