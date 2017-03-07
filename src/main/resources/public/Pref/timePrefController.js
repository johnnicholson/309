app.controller('timePrefController', ['$scope', '$state', 'login', '$http', 'notifyDlg', '$uibModal',
  function(scope, state, login, $http, nDlg, $uibM) {
    scope.coursePrefs = [];
    scope.levelToEnglish = function(level) {
      if (level == 1) {
        return "I am unable to teach at this time"
      }
      if (level == 2) {
        return "I am able to teach at this time, but would rather not"
      }
      if (level == 3) {
        return "I prefer to teach at this time"
      }
    }
    // Supply days of week data
    scope.daysOfWeek = [
      {sName: "Sun", lName: "SUNDAY"},
      {sName: "Mon", lName: "MONDAY"},
      {sName: "Tue", lName: "TUESDAY"},
      {sName: "Wed", lName: "WEDNESDAY"},
      {sName: "Thu", lName: "THURSDAY"},
      {sName: "Fri", lName: "FRIDAY"},
      {sName: "Sat", lName: "SATURDAY"}
    ];

    // Generate array containing contiguous integers from
    // start to end inclusive
    function genRange(start, end) {
      var range = [];
      while (start <= end) {
        range.push(start);
        start += 1;
      }
      return range;
    }

    // Returns the human readable string for a time
    scope.timeToReadable = function(tVal) {
      var reducedNum = tVal % 12 > 0 ? tVal % 12 : 12;
      return "" + reducedNum + (tVal / 12 < 1 ? " AM" : " PM");
    }

    // Returns db ready string for given time
    scope.timeToDBReady = function(tVal) {
      if (tVal < 10) tVal = "0" + tVal;
      return "" + tVal + ":00";

    }

    // Returns whether start and end time are valid
    // Assumed start and end time exist
    scope.timeValid = function(section) {
      return section.startTime < section.endTime;
    }

    scope.times = genRange(6, 23);


    // Fetches courses
    scope.fetchAllCourses = function() {
      $http({
        method: 'GET',
        url: 'api/course'
      })
      .then(function success(response) {
        scope.courses = response.data;
      })
      .catch(function error(response) {
        return notifyDlg.show(scope, "Could not fetch: " + response.status);
      });
    };


    scope.addTimePref = function() {
      scope.coursePrefs.unshift({id: null, prof: {id: login.getUser().id}, startTime: null, endTime: null, level: 1, edit: true})
    };

    login.getUserPromise()
    .then(function(user) {
      return $http.get("/api/prss/" + login.getUser().id + "/timeprefs")
    })
    .then(function(response) {
      scope.coursePrefs = response.data;
    });

    $http.get("/api/time")
    .then(function(response) {
      scope.courses = response.data;
    });

    scope.postTimePref = function(pref) {
      if (pref.id == null) {
        $http.post("/api/pref/time", pref)
        .then(function (response) {
          pref.id = response.data;
        });
      } else {
        $http.put("/api/pref/time/" + pref.id, pref)
      }
    }

  }]);
