app.controller('prefController', ['$scope', '$state', 'login', '$http', 'notifyDlg', '$uibModal',
  function(scope, state, login, $http, nDlg, $uibM) {
    scope.coursePrefs = [];
    scope.addCoursePref = function() {
        scope.coursePrefs.push({id: null, prof: {id: login.getUser().id}, course: null, level: 1})
    }
    login.getUserPromise()
    .then(function(user) {
      return $http.get("/api/prss/" + login.getUser().id + "/courseprefs")
    })
    .then(function(response) {
      scope.coursePrefs = response.data;
    })

    $http.get("/api/course")
    .then(function(response) {
      scope.courses = response.data;
    })

    scope.postCoursePref = function(pref) {
      $http.post("/api/pref/crs", pref)
      .then(function(response) {
        pref.id = response.data;
      });
    }

  }]);
