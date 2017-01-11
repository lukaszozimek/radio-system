(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRACustomerDetailController', TRACustomerDetailController);

    TRACustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TRACustomer', 'TRAIndustry', 'CORNetwork', 'User'];

    function TRACustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, TRACustomer, TRAIndustry, CORNetwork, User) {
        var vm = this;

        vm.tRACustomer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:tRACustomerUpdate', function(event, result) {
            vm.tRACustomer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
