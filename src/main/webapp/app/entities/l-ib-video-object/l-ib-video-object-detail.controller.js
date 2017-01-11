(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBVideoObjectDetailController', LIBVideoObjectDetailController);

    LIBVideoObjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBVideoObject', 'LIBCloudObject', 'LIBMediaItem'];

    function LIBVideoObjectDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBVideoObject, LIBCloudObject, LIBMediaItem) {
        var vm = this;

        vm.lIBVideoObject = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBVideoObjectUpdate', function(event, result) {
            vm.lIBVideoObject = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
