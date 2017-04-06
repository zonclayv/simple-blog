angular
  .module('app')
  .controller('RegisterCtrl',
    ['$scope', 'AuthService', '$state', function ($scope, AuthService, $state) {
      $scope.user = {};

      $scope.register = function () {
        AuthService
          .register($scope.user)
          .then(function () {
            $state.go('home');
          }, function (e) {
            console.log(e);
          });
      };
    }]);
