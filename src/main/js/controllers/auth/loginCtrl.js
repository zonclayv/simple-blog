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
