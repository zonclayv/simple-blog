angular
  .module('app', [
    'ui.router',
    'ngStorage'
  ])
  .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider', function ($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider) {
    $stateProvider
      .state('home', {
        url: '/',
        templateUrl: 'views/home.html'
      })
      .state('logout', {
        url: '/logout',
        controller: 'LogoutCtrl'
      })
      .state('login', {
        url: '/login',
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl'
      })
      .state('register', {
        url: '/register',
        templateUrl: 'views/register.html',
        controller: 'RegisterCtrl'
      })
      .state('404', {
        url: '/404',
        templateUrl: 'views/404.html'
      })
      .state('401', {
        url: '/401',
        templateUrl: 'views/401.html'
      })
      .state('users', {
        url: '/users',
        templateUrl: 'views/users.html',
        controller: 'AllUsersCtrl'
      })
      .state('users-info', {
        url: '/users/:id',
        templateUrl: 'views/user-info.html',
        controller: 'ProfileCtrl'
      })
      .state('me', {
        url: '/me',
        templateUrl: 'views/user-info.html',
        controller: 'MyProfileCtrl'
      });

    $locationProvider.html5Mode({
      enabled: true,
      requireBase: false
    });

    $urlRouterProvider.otherwise('404');
  }])
.run(function ($rootScope, $http, $location, $localStorage, AuthService){

    if ($localStorage.currentUser) {
      AuthService.loggedIn($localStorage.currentUser.username, $localStorage.currentUser.token);
    }

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
      var publicPages = ['/login', '/register', '/home', '/404', '/401', '/'];
      var restrictedPage = publicPages.indexOf($location.path()) === -1;
      if (restrictedPage && !$localStorage.currentUser) {
        $location.path('/401');
      }
    });
});