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
