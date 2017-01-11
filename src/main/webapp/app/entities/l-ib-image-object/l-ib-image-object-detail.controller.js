(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBImageObjectDetailController', LIBImageObjectDetailController);

    LIBImageObjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBImageObject', 'LIBCloudObject', 'LIBImageItem'];

    function LIBImageObjectDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBImageObject, LIBCloudObject, LIBImageItem) {
        var vm = this;

        vm.lIBImageObject = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBImageObjectUpdate', function(event, result) {
            vm.lIBImageObject = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
