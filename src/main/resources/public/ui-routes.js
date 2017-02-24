
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
      })
      .state('addequipment', {
        parent:'setup',
        url: '/addequipment',
        params: {
         equip: null
       },
        templateUrl: 'Setup/Equipment/addequipment.template.html',
        controller: 'addEquipmentController'
      }).state('course', {
        parent:'setup',
        url: '/course',
        templateUrl: 'Setup/Course/course.template.html',
        controller: 'courseController'
      })
      .state('addcourse', {
        parent:'setup',
        url: '/addcourse',
        params: {
         course: null
       },
        templateUrl: 'Setup/Course/addcourse.template.html',
        controller: 'addCourseController'
      })
      .state('roomtypes', {
        parent: 'setup',
        url: '/roomType',
        templateUrl: 'Setup/RoomType/roomtypes.template.html',
        controller: 'roomTypeController'
      })
      .state('addroomtype', {
        parent: 'setup',
        url: '/addroomtype',
        templateUrl: 'Setup/RoomType/addRoomType.template.html',
        controller: 'addRoomTypeController',
        params: {
          roomtype: null
        }
      })
      .state('users', {
        parent: 'setup',
        url: '/prss',
        templateUrl: 'Setup/Users/users.template.html',
        controller: 'usersController'
      })
      .state('adduser', {
        parent: 'setup',
        url: '/prss/add',
        templateUrl: 'Setup/Users/addUser.template.html',
        controller: 'addUserController',
        params: {
          user: null
        }
      })
      .state('rooms', {
        parent: 'setup',
        url: '/room',
        templateUrl: 'Setup/Rooms/rooms.template.html',
        controller: 'roomsController'
      })
      .state('addroom', {
        parent:'setup',
        url:'/addroom',
        templateUrl: 'Setup/Rooms/addRoom.template.html',
        controller: 'addRoomController',
        params: {
          room: null
        }
      })
      .state('componenttype', {
        parent: 'setup',
        url: '/component/type/',
        templateUrl: 'Setup/ComponentType/componentType.template.html',
        controller: 'componentTypeController'
      })
      .state('addeditcomponenttype', {
        parent: 'setup',
        url: '/component/type/addedit',
        templateUrl: 'Setup/ComponentType/addEditComponentType.template.html',
        controller: 'addEditComponentTypeController',
        params: {
          cType: null
        }
      })
      .state('term-home', {
        parent: 'base',
        url: '/term',
        templateUrl: 'Schedule/Term/term.template.html',
        controller: 'termController'
      })
      .state('term-add', {
        parent: 'base',
        url: '/term/add',
        templateUrl: 'Schedule/Term/termAdd.template.html',
        controller: 'termAddController'
      })
      .state('term-edit', {
        parent: 'base',
        url: '/term/:id/edit',
        templateUrl: 'Schedule/Term/termEdit.template.html',
        controller: 'termEditController'
      })
      .state('section-add', {
        parent: 'base',
        url: '/term/:id/section/add',
        templateUrl: 'Schedule/Term/Section/addSection.template.html',
        controller: 'addSectionController'
      })
      .state('calendar', {
        parent: 'base',
        url: '/calendar',
        templateUrl: 'Schedule/Calendar/calendar.template.html',
        controller: 'calendarController'
      })


   }]);
