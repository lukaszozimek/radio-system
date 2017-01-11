(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRADiscountDetailController', TRADiscountDetailController);

    TRADiscountDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TRADiscount', 'TRACustomer'];

    function TRADiscountDetailController($scope, $rootScope, $stateParams, previousState, entity, TRADiscount, TRACustomer) {
        var vm = this;

        vm.tRADiscount = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:tRADiscountUpdate', function(event, result) {
            vm.tRADiscount = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
