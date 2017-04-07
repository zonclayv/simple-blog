angular
  .module('app')
  .factory('UserService',['$http',
    function ($http) {

      const USER_PREFIX = "/api/users/";

      function getAll() {
        return $http.get(USER_PREFIX);
      }

      function updateById(id, attributes) {
        return $http.patch(USER_PREFIX + id, attributes);
      }

      function deleteById(id) {
        return $http.delete(USER_PREFIX + id);
      }

      return {
        getAll: getAll,
        updateById: updateById,
        deleteById: deleteById
      };
    }]);
