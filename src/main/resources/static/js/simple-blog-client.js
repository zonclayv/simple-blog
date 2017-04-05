angular
  .module('app', [
    'ui.router',
    'ngStorage'
  ])
  .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', function ($stateProvider, $urlRouterProvider, $locationProvider) {
    $stateProvider
      .state('home', {
        url: '/',
        templateUrl: 'views/home.html'
      })
      .state('forbidden', {
        url: '/forbidden',
        templateUrl: 'views/forbidden.html',
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
        controller: 'RegisterCtrl',
      });

    $locationProvider.html5Mode({
      enabled: true,
      requireBase: false
    });

    $urlRouterProvider.otherwise('home');
  }])
  .run(function ($rootScope, $http, $location, $localStorage, AuthService){
    // keep user logged in after page refresh
    if ($localStorage.currentUser) {
      AuthService.loggedIn($localStorage.currentUser.username, $localStorage.currentUser.token);
    }

    // redirect to login page if not logged in and trying to access a restricted page
    $rootScope.$on('$locationChangeStart', function (event, next, current) {
      var publicPages = ['/login', '/register'];
      var restrictedPage = publicPages.indexOf($location.path()) === -1;
      if (restrictedPage && !$localStorage.currentUser) {
        $location.path('/login');
      }
    });
});
angular
  .module('app')
  .factory('AuthService',
  ['$http', '$rootScope', '$localStorage', function ($http, $rootScope, $localStorage) {

    function login(credentials) {
      let promise = $http.post('auth/login', credentials);

      promise.then(function (response) {
          loggedIn(credentials.username, response.data.token);
        }, function () {
          loggedOut();
        });

      return promise;
    }

    function logout() {
      return $http.post('auth/logout', {}).then(function () {
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

     return {
       login: login,
       logout: logout,
       loggedIn: loggedIn,
     };
  }]);

angular
  .module('app')
  .factory('UserService',
  ['$http', function ($http) {

    function register(user) {
      return $http.post('users', user);
    }

     return {
       register: register
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
          }, function () {
              //TODO
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
    ['$scope', 'UserService', '$state', function ($scope, UserService, $state) {
      $scope.user = {};

      $scope.register = function () {
        UserService
          .register($scope.user)
          .then(function () {
            $state.go('home');
          }, function () {
            //TODO
          });
      };
    }]);
