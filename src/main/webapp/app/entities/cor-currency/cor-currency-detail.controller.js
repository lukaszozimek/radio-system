(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorCurrencyDetailController', CorCurrencyDetailController);

    CorCurrencyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorCurrency', 'CorNetwork'];

    function CorCurrencyDetailController($scope, $rootScope, $stateParams, previousState, entity, CorCurrency, CorNetwork) {
        var vm = this;

        vm.corCurrency = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corCurrencyUpdate', function(event, result) {
            vm.corCurrency = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
