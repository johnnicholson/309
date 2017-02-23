app.controller('addRoomController', ['$scope', '$state', '$http', 'notifyDlg', '$stateParams', function(scope, $state, $http, nDlg, params) {
  if (params.room === null)
    scope.room = {};
  else
    scope.room = params.room;


  scope.toggled = function(open) {
    console.log(open + " clicked");
  }

  $http.get("/api/roomType")
  .then(function(response) {
    scope.roomTypes = response.data;
  })

  scope.setRoomType = function(roomType) {
    console.log(roomType);
    scope.room.roomType = roomType;
  }

  scope.addRoom = function() {
    if (scope.room.id === undefined) {
      $http.post("api/room", scope.room)
      .then(function(response) {
        return nDlg.show(scope, "New Room Added")
      })
      .then(function(response) {
        $state.go('rooms');
      }).catch(function(response) {
        return nDlg.show(scope, "Addition failed.")
      })
    } else {
      $http.put("api/room/" + scope.room.id, scope.room)
      .then(function(response) {
        return nDlg.show(scope, "Room Modified")
      })
      .then(function(response){
        $state.go('rooms');
      }).catch(function(response) {
        return nDlg.show(scope, "Modification failed.")
      })
    }
  };
  scope.quit = function() {
    $state.go('rooms');
  };
}]);
