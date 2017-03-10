app.controller('addSectionController', ['$scope', '$state', '$http', 'notifyDlg', '$q', 'login','$stateParams', 'notifyDlg',
function($scope, $state, $http, nDlg, $q, login, params, notifyDlg) {

  $scope.termID = params.id;

  // Supply days of week data
  $scope.daysOfWeek = [
    {sName: "Sun", lName: "sunday"},
    {sName: "Mon", lName: "monday"},
    {sName: "Tue", lName: "tuesday"},
    {sName: "Wed", lName: "wednesday"},
    {sName: "Thu", lName: "thursday"},
    {sName: "Fri", lName: "friday"},
    {sName: "Sat", lName: "saturday"}
  ];

  // Consumes a section and returns whether it has been scheduled at least
  // one day of the week
  $scope.scheduledAtLeastOneDay = function(section) {
    return section &&
    (section.monday ||
      section.tuesday ||
      section.wednesday ||
      section.thursday ||
      section.friday ||
      section.saturday ||
      section.sunday);
    }

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
    $scope.timeToReadable = function(tVal) {
      var reducedNum = tVal % 12 > 0 ? tVal % 12 : 12;
      return "" + reducedNum + (tVal / 12 < 1 ? " AM" : " PM");
    }

    // Returns db ready string for given time
    $scope.timeToDBReady = function(tVal) {
      return "" + tVal + ":00";
    }

    // Returns whether start and end time are valid
    // Assumed start and end time exist
    $scope.timeValid = function(section) {
      return section.startTime < section.endTime;
    }

    $scope.times = genRange(6, 23);

    // Fetches courses
    $scope.fetchAllCourses = function() {
      $http({
        method: 'GET',
        url: 'api/course'
      })
      .then(function success(response) {
        $scope.courses = response.data;
      })
      .catch(function error(response) {
        return notifyDlg.show($scope, "Could not fetch: " + response.status);
      });
    };

    // Fetch professors
    $scope.fetchAllProfessors = function() {
      $http({
        method: 'GET',
        url: 'api/prss'
      })
      .then(function success(response) {
        $scope.users = response.data;
        console.log($scope.users);
      })
      .catch(function error(response) {
        return notifyDlg.show($scope, "Could not fetch: " + response.status);
      });
    };

    // Fetch rooms
    $scope.fetchAllRooms = function() {
      $http({
        method: 'GET',
        url: 'api/room'
      })
      .then(function success(response) {
        $scope.rooms = response.data;
      })
      .catch(function error(response) {
        return notifyDlg.show($scope, "Could not fetch: " + response.status);
      });
    }

    // Called when a new course is selected
    // Wipes all previously filled out sections
    $scope.startNewSections = function() {
      var sections = [];

      // Get course, add its components to a new list
      var selectedComponents = $scope.selectedCourse.components;
      for (var cID in selectedComponents) {
        var curComp = selectedComponents[cID];
        var newSection = {};
        newSection.component = curComp;
        newSection.course = $scope.selectedCourse;
        sections.push(newSection);
      }

      // Set new components
      $scope.sections = sections;
    }

    // Replaces any necessary fields in the sections to prepare them for
    // the database.
    // * Changes startTime and endTime to be DB ready
    function createDBReadySections(inSections) {

      // Makes a deep copy of the sections passed in
      var sections = JSON.parse(JSON.stringify(inSections));

      // Iterate through and modify values (like times)
      for (var sKey in sections) {
        var section = sections[sKey];
        if (section.startTime) {
          section.startTime = $scope.timeToDBReady(section.startTime);
        }
        if (section.endTime) {
          section.endTime = $scope.timeToDBReady(section.endTime);
        }
      }
      return sections;
    }

    // Submits new section if they're valid,
    // else notifies the user that they need to fix the fields
    $scope.submitForm = function(isValid) {

      // Manually check if at least 1 day is scheduled
      // and times are valid for each section
      var sectIt = $scope.sections.length;
      var manualCheck = true;
      while (sectIt-- && manualCheck) {
        var section = $scope.sections[sectIt];
        manualCheck = manualCheck && $scope.scheduledAtLeastOneDay(section)
        && $scope.timeValid(section);
      }

      if (isValid && manualCheck) {
        $scope.submitSections();
      }
      else {
        $scope.showErrors = true;
        nDlg.show($scope, "Could not submit. Invalid fields have been marked.", "Invalid Sections");
      }
    }

    // Prepares and submits sections to db
    $scope.submitSections = function() {
      var dbReady = createDBReadySections($scope.sections);
      var nPosts = dbReady.length;
      var sStatement = "";
      var fStatement = "";

      // Shows which sections were successfully or Unsuccessfully posted
      function displayResults() {
        var res = "";
        if (sStatement.length > 0 ) {
          res += "Successfully posted: " + sStatement + "\n";
        }
        if (fStatement.length > 0 ) {
          res += "Unsuccessfully posted: " + fStatement;
        }

        nDlg.show($scope, res)
        .then(function() {
          $state.go("term-edit", {id : $scope.termID});
        });
      }

      // Iterate through and post each section
      // Display results when all posts return
      for (var sKey in dbReady) {
        var section = dbReady[sKey];

        // Wrapping in immediately called function in order to capture
        // value of sectName in scope
        (function () {
          var sectName = section.name;
          $http.post("api/term/" + $scope.termID + "/section", section)
        .then(function(response) {
          sStatement += sectName + " ";
          if (--nPosts == 0) {
            displayResults();
          }
        }).catch(function(response) {
          fStatement += sectName + " ";
          if (--nPosts == 0) {
            displayResults();
          }
        })})();
      }
    }

    // Adds another empty section template
    $scope.addSection = function() {
      var newSection = {};
      newSection.course = $scope.selectedCourse;
      $scope.sections.push(newSection);
    }

    $scope.quit = function() {
      $state.go("term-edit", {id : $scope.termID});
    }

    $scope.fetchAllCourses()
    $scope.fetchAllProfessors();
    $scope.fetchAllRooms();
  }]);
