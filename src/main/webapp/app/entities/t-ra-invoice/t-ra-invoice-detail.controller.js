(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAInvoiceDetailController', TRAInvoiceDetailController);

    TRAInvoiceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TRAInvoice', 'TRAOrder'];

    function TRAInvoiceDetailController($scope, $rootScope, $stateParams, previousState, entity, TRAInvoice, TRAOrder) {
        var vm = this;

        vm.tRAInvoice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:tRAInvoiceUpdate', function(event, result) {
            vm.tRAInvoice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
