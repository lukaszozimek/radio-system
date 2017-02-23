(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmLeadDetailController', CrmLeadDetailController);

    CrmLeadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CrmLead', 'CorPerson', 'CorAddress', 'CrmLeadStatus', 'CrmLeadSource', 'CorUser', 'TraIndustry', 'CorArea', 'CorNetwork', 'CrmTask'];

    function CrmLeadDetailController($scope, $rootScope, $stateParams, previousState, entity, CrmLead, CorPerson, CorAddress, CrmLeadStatus, CrmLeadSource, CorUser, TraIndustry, CorArea, CorNetwork, CrmTask) {
        var vm = this;

        vm.crmLead = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:crmLeadUpdate', function(event, result) {
            vm.crmLead = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
