angular
  .module('app')
  .controller('RegisterCtrl',
    ['$scope', 'AuthService', '$state', function ($scope, AuthService, $state) {
      $scope.user = {};

      $scope.register = function () {
        AuthService
          .register($scope.user)
          .then(function () {
            $state.go('login');
          }, function (e) {
            let data = e.data;
            $scope.error = data.errors ? data.errors.join("\n") : data.message;
          });
      };
    }]);
