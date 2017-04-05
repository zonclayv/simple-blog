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
