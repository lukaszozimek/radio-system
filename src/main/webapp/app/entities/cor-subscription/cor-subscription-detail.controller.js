(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorSubscriptionDetailController', CorSubscriptionDetailController);

    CorSubscriptionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorSubscription'];

    function CorSubscriptionDetailController($scope, $rootScope, $stateParams, previousState, entity, CorSubscription) {
        var vm = this;

        vm.corSubscription = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corSubscriptionUpdate', function(event, result) {
            vm.corSubscription = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
