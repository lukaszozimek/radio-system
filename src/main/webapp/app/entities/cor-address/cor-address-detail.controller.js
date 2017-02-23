(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorAddressDetailController', CorAddressDetailController);

    CorAddressDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorAddress', 'CorNetwork'];

    function CorAddressDetailController($scope, $rootScope, $stateParams, previousState, entity, CorAddress, CorNetwork) {
        var vm = this;

        vm.corAddress = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corAddressUpdate', function(event, result) {
            vm.corAddress = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
