
app.config(['$stateProvider', '$urlRouterProvider',
   function($stateProvider, $router) {

      //redirect to home if path is not matched
      $router.otherwise("/");

      $stateProvider
      .state('base', {
         abstract: true,
         template: '<ui-view/>',
         resolve : {
            user: function(login) {
               login.relogin();
            }
         }
      })
      .state('home',  {
         parent: 'base',
         url: '/',
         templateUrl: 'Home/home.template.html',
         controller: 'homeController'

      })
      .state('register', {
         parent: 'base',
         url: '/register',
         templateUrl: 'Register/register.template.html',
         controller: 'registerController',
      })
      .state('login', {
         parent: 'base',
         url: '/login',
         templateUrl: 'Login/login.template.html',
         controller: 'loginController',
      })
      .state('setup', {
         parent: 'base',
         url: '/setup',
         templateUrl: 'Setup/setup.template.html',
         controller: 'setupController',
      })
      .state('equipment', {
        parent:'setup',
        url: '/equipment',
        templateUrl: 'Setup/Equipment/equipment.template.html',
        controller: 'equipmentController'
      });
   }]);
