(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorNetworkDetailController', CorNetworkDetailController);

    CorNetworkDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorNetwork'];

    function CorNetworkDetailController($scope, $rootScope, $stateParams, previousState, entity, CorNetwork) {
        var vm = this;

        vm.corNetwork = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corNetworkUpdate', function(event, result) {
            vm.corNetwork = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
