(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmLeadStatusDetailController', CrmLeadStatusDetailController);

    CrmLeadStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CrmLeadStatus', 'CorNetwork'];

    function CrmLeadStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, CrmLeadStatus, CorNetwork) {
        var vm = this;

        vm.crmLeadStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:crmLeadStatusUpdate', function(event, result) {
            vm.crmLeadStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
