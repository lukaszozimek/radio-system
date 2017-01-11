(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORNetworkDetailController', CORNetworkDetailController);

    CORNetworkDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CORNetwork'];

    function CORNetworkDetailController($scope, $rootScope, $stateParams, previousState, entity, CORNetwork) {
        var vm = this;

        vm.cORNetwork = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cORNetworkUpdate', function(event, result) {
            vm.cORNetwork = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
