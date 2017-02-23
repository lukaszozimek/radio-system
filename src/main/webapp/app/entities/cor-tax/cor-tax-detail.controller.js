(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorTaxDetailController', CorTaxDetailController);

    CorTaxDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorTax', 'CorNetwork'];

    function CorTaxDetailController($scope, $rootScope, $stateParams, previousState, entity, CorTax, CorNetwork) {
        var vm = this;

        vm.corTax = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corTaxUpdate', function(event, result) {
            vm.corTax = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
