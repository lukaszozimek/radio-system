(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRACampaignDetailController', TRACampaignDetailController);

    TRACampaignDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TRACampaign', 'TRACustomer'];

    function TRACampaignDetailController($scope, $rootScope, $stateParams, previousState, entity, TRACampaign, TRACustomer) {
        var vm = this;

        vm.tRACampaign = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:tRACampaignUpdate', function(event, result) {
            vm.tRACampaign = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
