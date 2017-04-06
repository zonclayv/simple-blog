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
