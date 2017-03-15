angular
  .module('app')
  .controller('LogoutCtrl',
    ['$scope', 'AuthService', '$state', function ($scope, AuthService, $state) {
      AuthService
        .logout()
        .then(function () {
          $state.go('home');
        });
    }]);
