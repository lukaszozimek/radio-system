(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraCampaignDetailController', TraCampaignDetailController);

    TraCampaignDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraCampaign', 'CrmAccount', 'CorNetwork', 'TraCampaingStatus', 'TraPrice', 'TraOrder', 'SchEmission'];

    function TraCampaignDetailController($scope, $rootScope, $stateParams, previousState, entity, TraCampaign, CrmAccount, CorNetwork, TraCampaingStatus, TraPrice, TraOrder, SchEmission) {
        var vm = this;

        vm.traCampaign = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:traCampaignUpdate', function(event, result) {
            vm.traCampaign = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
