(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraInvoiceStatusDetailController', TraInvoiceStatusDetailController);

    TraInvoiceStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraInvoiceStatus'];

    function TraInvoiceStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, TraInvoiceStatus) {
        var vm = this;

        vm.traInvoiceStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:traInvoiceStatusUpdate', function(event, result) {
            vm.traInvoiceStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
