(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibImageObjectDetailController', LibImageObjectDetailController);

    LibImageObjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibImageObject', 'LibCloudObject', 'LibImageItem'];

    function LibImageObjectDetailController($scope, $rootScope, $stateParams, previousState, entity, LibImageObject, LibCloudObject, LibImageItem) {
        var vm = this;

        vm.libImageObject = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libImageObjectUpdate', function(event, result) {
            vm.libImageObject = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
