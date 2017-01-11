(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBAudioObjectDetailController', LIBAudioObjectDetailController);

    LIBAudioObjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBAudioObject', 'LIBCloudObject', 'LIBMediaItem'];

    function LIBAudioObjectDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBAudioObject, LIBCloudObject, LIBMediaItem) {
        var vm = this;

        vm.lIBAudioObject = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBAudioObjectUpdate', function(event, result) {
            vm.lIBAudioObject = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
