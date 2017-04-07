angular
  .module('app')
  .controller('ProfileCtrl',  ['$stateParams', '$scope', 'AuthService', '$state', 'UserService',
    function ($stateParams, $scope, AuthService, $state, UserService) {

      $scope.getUser = function() {
        AuthService
          .me()
          .then(function (result) {
            $scope.selectedUser = result.data;
          });
      };

      $scope.getUser();

      $scope.fileChanged = function (file) {
        $scope.file = file;
      };

      $scope.saveUser = function () {

        let userAttr = {
          firstname: $scope.selectedUser.firstname,
          lastname: $scope.selectedUser.lastname,
          email: $scope.selectedUser.email,
          username: $scope.selectedUser.username
        };

        if ($scope.file && $scope.file.files[0]) {
          let reader = new FileReader();

          reader.onload = function (readerEvt) {
            let binaryString = readerEvt.target.result;
            userAttr.avatar = $scope.selectedUser.picture = btoa(binaryString);
            UserService
              .updateById($scope.selectedUser.id, userAttr)
              .then(function () {
                $scope.getUser();
              });
          };

          reader.readAsBinaryString($scope.file.files[0]);
          return;
        }

        UserService
          .updateById($scope.selectedUser.id, userAttr)
          .then(function () {
            $scope.getUser();
          });
      };

      $scope.cancel = function () {
        $state.go('home');
      };

    }]);
