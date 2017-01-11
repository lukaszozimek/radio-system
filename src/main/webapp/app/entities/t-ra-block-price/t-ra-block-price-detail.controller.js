(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRABlockPriceDetailController', TRABlockPriceDetailController);

    TRABlockPriceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TRABlockPrice'];

    function TRABlockPriceDetailController($scope, $rootScope, $stateParams, previousState, entity, TRABlockPrice) {
        var vm = this;

        vm.tRABlockPrice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:tRABlockPriceUpdate', function(event, result) {
            vm.tRABlockPrice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
