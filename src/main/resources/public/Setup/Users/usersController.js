app.controller('usersController', ['$scope', '$state', '$http', 'login',
  function ($scope, $state, $http, login) {

    $scope.showEdit = login.isAdmin();
    // Define users endpoint interface
    fetchAllUsers= function() {
        $http({
            method: 'GET',
            url: 'api/prss'
        }).then(function success(response) {
            $scope.users = response.data;
        });
    };

    // Fetch users on startup
    fetchAllUsers();
  }]);
