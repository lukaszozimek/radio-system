(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBCloudObjectDetailController', LIBCloudObjectDetailController);

    LIBCloudObjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBCloudObject', 'CORNetwork', 'User'];

    function LIBCloudObjectDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBCloudObject, CORNetwork, User) {
        var vm = this;

        vm.lIBCloudObject = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBCloudObjectUpdate', function(event, result) {
            vm.lIBCloudObject = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
