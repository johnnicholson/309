
app.controller('addRoomTypeController', ['$scope', '$state', '$http', 'notifyDlg', '$stateParams', function(scope, $state, $http, nDlg, params) {

  if (params.roomtype === undefined) {
    console.log("its undefined")
    scope.roomtype = {};
  }
  else {
    scope.roomtype = params.roomtype
    console.log("beginingn: ---")
    console.log(scope.roomtype);
  }
  scope.addRoomType = function() {
    if (scope.roomtype.id === undefined) {

      $http.post("api/roomType", scope.roomtype)
      .then(function(response){
        return nDlg.show(scope, "New RoomType Added")
      })
      .then(function(response){
        $state.go('roomtypes');
      }).catch(function(response) {
        return nDlg.show(scope, "Addition failed.")
      })
    }
    else {
      $http.put("api/roomType/" + scope.roomtype.id, scope.roomtype)
      .then(function(response) {
        console.log("successful!!!");
        return nDlg.show(scope, "RoomType Modified")
      })
      .then(function(response) {
        scope.quit();
      }).catch(function(response) {
        console.log("failureee");
        return nDlg.show(scope, "Modification Failed.")
      })
    }
  };

  scope.quit = function() {
    $state.go('roomtypes');
  };
}]);
