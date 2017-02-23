(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraDiscountDetailController', TraDiscountDetailController);

    TraDiscountDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraDiscount', 'CorNetwork'];

    function TraDiscountDetailController($scope, $rootScope, $stateParams, previousState, entity, TraDiscount, CorNetwork) {
        var vm = this;

        vm.traDiscount = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:traDiscountUpdate', function(event, result) {
            vm.traDiscount = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
