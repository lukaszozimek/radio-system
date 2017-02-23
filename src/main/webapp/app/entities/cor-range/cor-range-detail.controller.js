(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorRangeDetailController', CorRangeDetailController);

    CorRangeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorRange', 'CorNetwork'];

    function CorRangeDetailController($scope, $rootScope, $stateParams, previousState, entity, CorRange, CorNetwork) {
        var vm = this;

        vm.corRange = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corRangeUpdate', function(event, result) {
            vm.corRange = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
