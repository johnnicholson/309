app.controller('addCourseController', ['$scope', '$state', '$http', 'notifyDlg', '$stateParams', function(scope, $state, $http, nDlg, params) {

  if (params.course === null)
    scope.course = {units:4};
  else
    scope.course = params.course;
  scope.toggled = function(open) {
    console.log(open + " clicked");
  }
  $http.get("/api/component/type")
  .then(function(response) {
    scope.componentTypes = response.data;
  })

  scope.addCourse = function() {
    if (scope.course.id === undefined) {
      $http.post("api/course", scope.course)
      .then(function(response) {
        return nDlg.show(scope, "New Course Added")
      })
      .then(function(response){
        $state.go('course');
      }).catch(function(response) {
        return nDlg.show(scope, "Addition failed.")
      })
    } else {
      $http.put("api/equip/" + scope.course.id, scope.course)
      .then(function(response) {
        return nDlg.show(scope, "Course Modified")
      })
      .then(function(response){
        $state.go('course');
      }).catch(function(response) {
        return nDlg.show(scope, "Modification failed.")
      })
    }

  };

  scope.quit = function() {
    $state.go('course');
  };
}]);
