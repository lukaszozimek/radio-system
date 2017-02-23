(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibVideoObjectDetailController', LibVideoObjectDetailController);

    LibVideoObjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibVideoObject', 'LibCloudObject', 'LibMediaItem'];

    function LibVideoObjectDetailController($scope, $rootScope, $stateParams, previousState, entity, LibVideoObject, LibCloudObject, LibMediaItem) {
        var vm = this;

        vm.libVideoObject = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libVideoObjectUpdate', function(event, result) {
            vm.libVideoObject = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
