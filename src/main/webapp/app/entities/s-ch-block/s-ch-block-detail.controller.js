(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SCHBlockDetailController', SCHBlockDetailController);

    SCHBlockDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SCHBlock', 'CORChannel', 'SCHTemplate'];

    function SCHBlockDetailController($scope, $rootScope, $stateParams, previousState, entity, SCHBlock, CORChannel, SCHTemplate) {
        var vm = this;

        vm.sCHBlock = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:sCHBlockUpdate', function(event, result) {
            vm.sCHBlock = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
