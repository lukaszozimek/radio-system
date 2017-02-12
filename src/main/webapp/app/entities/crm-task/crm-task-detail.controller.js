(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmTaskDetailController', CrmTaskDetailController);

    CrmTaskDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CrmTask', 'CorUser', 'CrmTaskStatus', 'CorNetwork', 'CrmOpportunity', 'CrmContact', 'CrmAccount', 'CrmLead'];

    function CrmTaskDetailController($scope, $rootScope, $stateParams, previousState, entity, CrmTask, CorUser, CrmTaskStatus, CorNetwork, CrmOpportunity, CrmContact, CrmAccount, CrmLead) {
        var vm = this;

        vm.crmTask = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:crmTaskUpdate', function(event, result) {
            vm.crmTask = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
