angular
  .module('app')
  .factory('AuthService',
  ['$http', '$rootScope', '$localStorage', function ($http, $rootScope, $localStorage) {

    const AUTH_PREFIX = "auth/";

    $rootScope.hasRole = function(roles){
      if(!roles || !$rootScope.currentUser.role){
        return false;
      }

      return roles.indexOf($rootScope.currentUser.role)>-1;
    };

    function login(credentials) {
      let promise = $http.post(AUTH_PREFIX + 'login', credentials);

      promise.then(function (response) {
          loggedIn(credentials.username, response.data.token);
        }, function () {
          loggedOut();
        });

      return promise;
    }

    function checkRoles() {
      me().then(function (response) {
          let user = response.data;
          $rootScope.currentUser.role = user.role;
        });
    }

    function me() {
      return $http.get(AUTH_PREFIX + 'me');
    }

    function logout() {
      let promise = $http.post(AUTH_PREFIX + 'logout', {})
      promise.then(function () {
        loggedOut();
      }, function () {
        loggedOut();
      });

      return promise;
    }

    function loggedIn(username, token) {
      $rootScope.authenticated = !!token;
      $rootScope.currentUser = {};
      $rootScope.currentUser.name = username;
      $rootScope.currentUser.token = token;

      $localStorage.currentUser = { username: username, token: token };

      $http.defaults.headers.common['Authorization'] = token;

      checkRoles();
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
       me: me,
       login: login,
       logout: logout,
       loggedIn: loggedIn,
       register: register
     };
  }]);
