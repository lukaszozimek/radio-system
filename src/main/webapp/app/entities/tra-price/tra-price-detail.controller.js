(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraPriceDetailController', TraPriceDetailController);

    TraPriceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraPrice', 'CorCurrency', 'CorNetwork'];

    function TraPriceDetailController($scope, $rootScope, $stateParams, previousState, entity, TraPrice, CorCurrency, CorNetwork) {
        var vm = this;

        vm.traPrice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:traPriceUpdate', function(event, result) {
            vm.traPrice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
