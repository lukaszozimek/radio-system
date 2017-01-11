(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAOrderDetailController', TRAOrderDetailController);

    TRAOrderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TRAOrder', 'TRACampaign', 'TRACustomer'];

    function TRAOrderDetailController($scope, $rootScope, $stateParams, previousState, entity, TRAOrder, TRACampaign, TRACustomer) {
        var vm = this;

        vm.tRAOrder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:tRAOrderUpdate', function(event, result) {
            vm.tRAOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
