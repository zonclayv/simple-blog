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
angular
  .module('app')
  .factory('AuthService',
  ['$http', '$rootScope', '$localStorage', function ($http, $rootScope, $localStorage) {

    const AUTH_PREFIX = "auth/";


    function login(credentials) {
      let promise = $http.post(AUTH_PREFIX + 'login', credentials);

      promise.then(function (response) {
          loggedIn(credentials.username, response.data.token);
        }, function () {
          loggedOut();
        });

      return promise;
    }

    function logout() {
      return $http.post(AUTH_PREFIX + 'logout', {}).then(function () {
        loggedOut();
      }, function () {
        loggedOut();
      });
    }

    function loggedIn(username, token) {
      $rootScope.authenticated = !!token;
      $rootScope.currentUser = {};
      $rootScope.currentUser.name = username;
      $rootScope.currentUser.token = token;

      $localStorage.currentUser = { username: username, token: token };

      $http.defaults.headers.common['Authorization'] = token;
    }

    function loggedOut() {
      $rootScope.authenticated = false;
      $rootScope.currentUser = {};

      $http.defaults.headers.common.Authorization = '';
      delete $localStorage.currentUser;
    }

    function register(user) {
      return $http.post(AUTH_PREFIX + 'register', user);
    }

     return {
       login: login,
       logout: logout,
       loggedIn: loggedIn,
       register: register
     };
  }]);

angular
  .module('app')
  .factory('UserService',
  ['$http', function ($http) {

     return {
     };
    }]);

angular
  .module('app')
  .controller('LoginCtrl',
    ['$scope', 'AuthService', '$state', function ($scope, AuthService, $state) {
      $scope.credentials = {};

      $scope.login = function () {
        AuthService
          .login({username: $scope.credentials.userId, password: $scope.credentials.password})
          .then(function () {
            $state.go('home');
          }, function (e) {
            console.log(e);
          });
      };
    }]);

angular
  .module('app')
  .controller('LogoutCtrl',
    ['$scope', 'AuthService', '$state', function ($scope, AuthService, $state) {
      AuthService
        .logout()
        .then(function () {
          $state.go('home');
        });
    }]);

angular
  .module('app')
  .controller('RegisterCtrl',
    ['$scope', 'AuthService', '$state', function ($scope, AuthService, $state) {
      $scope.user = {};

      $scope.register = function () {
        AuthService
          .register($scope.user)
          .then(function () {
            $state.go('home');
          }, function (e) {
            console.log(e);
          });
      };
    }]);
