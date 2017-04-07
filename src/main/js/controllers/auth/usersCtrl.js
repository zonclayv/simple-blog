angular
  .module('app')
  .controller('AllUsersCtrl', ['$scope', 'UserService', function ($scope, UserService) {

    function getUsers() {
      UserService
        .getAll()
        .then(function (results) {
          $scope.users = results;
        });
    }

    getUsers();

    $scope.removeUser = function (user) {
      UserService
        .deleteById(user)
        .then(function () {
          getUsers();
        });
    };

  }]);
