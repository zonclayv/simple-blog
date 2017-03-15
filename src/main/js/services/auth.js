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
