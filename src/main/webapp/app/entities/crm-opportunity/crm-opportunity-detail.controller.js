(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmOpportunityDetailController', CrmOpportunityDetailController);

    CrmOpportunityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CrmOpportunity', 'CrmStage', 'CorUser', 'CrmContact', 'CrmAccount', 'CrmLead', 'CorNetwork', 'CrmTask'];

    function CrmOpportunityDetailController($scope, $rootScope, $stateParams, previousState, entity, CrmOpportunity, CrmStage, CorUser, CrmContact, CrmAccount, CrmLead, CorNetwork, CrmTask) {
        var vm = this;

        vm.crmOpportunity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:crmOpportunityUpdate', function(event, result) {
            vm.crmOpportunity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
