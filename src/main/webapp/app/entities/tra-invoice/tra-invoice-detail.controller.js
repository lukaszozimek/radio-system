(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraInvoiceDetailController', TraInvoiceDetailController);

    TraInvoiceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraInvoice', 'CrmAccount', 'CorNetwork', 'TraInvoiceStatus', 'TraOrder'];

    function TraInvoiceDetailController($scope, $rootScope, $stateParams, previousState, entity, TraInvoice, CrmAccount, CorNetwork, TraInvoiceStatus, TraOrder) {
        var vm = this;

        vm.traInvoice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:traInvoiceUpdate', function(event, result) {
            vm.traInvoice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
