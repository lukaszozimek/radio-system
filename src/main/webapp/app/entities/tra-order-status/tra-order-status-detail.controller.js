(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraOrderStatusDetailController', TraOrderStatusDetailController);

    TraOrderStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraOrderStatus'];

    function TraOrderStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, TraOrderStatus) {
        var vm = this;

        vm.traOrderStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:traOrderStatusUpdate', function(event, result) {
            vm.traOrderStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
