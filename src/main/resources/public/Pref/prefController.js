app.controller('prefController', ['$scope', '$state', 'login', '$http', 'notifyDlg', '$uibModal', '$stateParams',
  function(scope, state, login, $http, nDlg, $uibM, params) {
    scope.coursePrefs = [];

     scope.levelToEnglish = function(level) {
      if (level == 1) {
        return "I am unable to teach this course"
      }
      if (level == 2) {
        return "I am able to teach this course, but would rather not"
      }
      if (level == 3) {
        return "I prefer to teach this course"
      }
    }
    scope.addCoursePref = function() {
        scope.coursePrefs.unshift({id: null, prof: {id: login.getUser().id}, course: null, level: 1, edit: true})
    }
    login.getUserPromise()
    .then(function(user) {
      if (params.user) {
        scope.curUser = params.user;
        return $http.get("/api/prss/" + params.user.id + "/courseprefs")

      } else {
        return $http.get("/api/prss/" + login.getUser().id + "/courseprefs")
      }
    })
    .then(function(response) {
      scope.coursePrefs = response.data;
    })

    $http.get("/api/course")
    .then(function(response) {
      scope.courses = response.data;
    })

    scope.postCoursePref = function(pref) {
      if (pref.id == null) {
        $http.post("/api/pref/crs", pref)
        .then(function (response) {
          pref.id = response.data;
        });
      } else {
        $http.put("/api/pref/crs/" + pref.id, pref)
      }
    }

    scope.deletePref = function(pref) {
       if (pref.id == null) {
         scope.coursePrefs.splice(scope.coursePrefs.indexOf(pref), 1);
       } else {
         $http.delete("/api/pref/crs/" + pref.id)
         .then(function(response) {
           scope.coursePrefs.splice(scope.coursePrefs.indexOf(pref), 1);
         })
         .catch(function(response) {
            console.log(response.data);
         })
       }
    }

  }]);
