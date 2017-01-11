(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORAddressDetailController', CORAddressDetailController);

    CORAddressDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CORAddress', 'CORNetwork'];

    function CORAddressDetailController($scope, $rootScope, $stateParams, previousState, entity, CORAddress, CORNetwork) {
        var vm = this;

        vm.cORAddress = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cORAddressUpdate', function(event, result) {
            vm.cORAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
