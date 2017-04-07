angular
  .module('app')
  .controller('MyProfileCtrl', ['$stateParams', '$scope', 'AuthService', '$state', 'UserService', '$controller',
    function ($stateParams, $scope, AuthService, $state, UserService, $controller) {

      $controller('ProfileCtrl', {
        $stateParams: $stateParams,
        $scope: $scope,
        AuthService: AuthService,
        $state: $state,
        UserService: UserService});

      $scope.getUser = function() {
        AuthService
          .me()
          .then(function (result) {
            $scope.selectedUser = result.data;
          });
      };

      $scope.getUser();
    }
  ]);