angular
  .module('app')
  .controller('RegisterCtrl',
    ['$scope', 'UserService', '$state', function ($scope, UserService, $state) {
      $scope.user = {};

      $scope.register = function () {
        UserService
          .register($scope.user)
          .then(function () {
            $state.go('home');
          }, function () {
            //TODO
          });
      };
    }]);
