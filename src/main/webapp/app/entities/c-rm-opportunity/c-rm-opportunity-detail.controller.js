(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMOpportunityDetailController', CRMOpportunityDetailController);

    CRMOpportunityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CRMOpportunity', 'CORNetwork'];

    function CRMOpportunityDetailController($scope, $rootScope, $stateParams, previousState, entity, CRMOpportunity, CORNetwork) {
        var vm = this;

        vm.cRMOpportunity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cRMOpportunityUpdate', function(event, result) {
            vm.cRMOpportunity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
