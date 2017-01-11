(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SCHEmissionDetailController', SCHEmissionDetailController);

    SCHEmissionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SCHEmission', 'SCHBlock', 'LIBMediaItem', 'SCHTemplate'];

    function SCHEmissionDetailController($scope, $rootScope, $stateParams, previousState, entity, SCHEmission, SCHBlock, LIBMediaItem, SCHTemplate) {
        var vm = this;

        vm.sCHEmission = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:sCHEmissionUpdate', function(event, result) {
            vm.sCHEmission = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
