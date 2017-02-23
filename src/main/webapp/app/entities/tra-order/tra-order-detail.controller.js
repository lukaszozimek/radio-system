(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraOrderDetailController', TraOrderDetailController);

    TraOrderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraOrder', 'CrmAccount', 'TraCampaign', 'TraPrice', 'CorNetwork', 'TraOrderStatus', 'TraInvoice'];

    function TraOrderDetailController($scope, $rootScope, $stateParams, previousState, entity, TraOrder, CrmAccount, TraCampaign, TraPrice, CorNetwork, TraOrderStatus, TraInvoice) {
        var vm = this;

        vm.traOrder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:traOrderUpdate', function(event, result) {
            vm.traOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
