angular
  .module('app')
  .controller('AllUsersCtrl', ['$scope', 'UserService', 'SpringDataRestAdapter', function ($scope, UserService, SpringDataRestAdapter) {

    function getUsers() {
      let promise = UserService
        .getAll();

      SpringDataRestAdapter.process(promise).then(function (processedResponse) {
        $scope.users = processedResponse._embeddedItems;
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
