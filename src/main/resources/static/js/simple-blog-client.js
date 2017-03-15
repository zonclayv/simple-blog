angular
  .module('app', [
    'ui.router'
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

    $urlRouterProvider.otherwise('home');
  }]);
angular
  .module('app')
  .factory('AuthService',
  ['$http', '$rootScope', function ($http, $rootScope) {

    function login(credentials) {

      let authData = btoa(credentials.user + ":" + credentials.password);
      let promise = $http.get('auth/login', {
        headers: (credentials) ? {authorization: "Basic " + authData} : {}
      });

      promise.then(function (response) {
          loggedIn(response.data, authData);
        }, function () {
          loggedOut();
        });

      return promise;
    }

    function logout() {
      return $http.get('auth/logout', {}).then(function () {
        loggedOut();
      }, function () {
        loggedOut();
      });
    }

    function loggedIn(data, authData) {
      $rootScope.authenticated = !!data.username;
      $rootScope.currentUser.name = data.username;
      $rootScope.currentUser.role = data.role;
      $rootScope.currentUser.authData = authData;

      $http.defaults.headers.common['Authorization'] = 'Basic ' + authData;
    }

    function loggedOut() {
      $rootScope.authenticated = false;
      $rootScope.currentUser = {};

      $http.defaults.headers.common.Authorization = 'Basic';
    }

     return {
       login: login,
       logout: logout
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
          .login({user: $scope.credentials.userId, password: $scope.credentials.password})
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
