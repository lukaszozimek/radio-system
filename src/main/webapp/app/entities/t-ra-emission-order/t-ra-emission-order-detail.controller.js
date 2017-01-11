(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAEmissionOrderDetailController', TRAEmissionOrderDetailController);

    TRAEmissionOrderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TRAEmissionOrder'];

    function TRAEmissionOrderDetailController($scope, $rootScope, $stateParams, previousState, entity, TRAEmissionOrder) {
        var vm = this;

        vm.tRAEmissionOrder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:tRAEmissionOrderUpdate', function(event, result) {
            vm.tRAEmissionOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
