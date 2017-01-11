(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAPriceDetailController', TRAPriceDetailController);

    TRAPriceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TRAPrice', 'CORNetwork'];

    function TRAPriceDetailController($scope, $rootScope, $stateParams, previousState, entity, TRAPrice, CORNetwork) {
        var vm = this;

        vm.tRAPrice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:tRAPriceUpdate', function(event, result) {
            vm.tRAPrice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
