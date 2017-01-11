(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBLabelDetailController', LIBLabelDetailController);

    LIBLabelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBLabel', 'CORNetwork'];

    function LIBLabelDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBLabel, CORNetwork) {
        var vm = this;

        vm.lIBLabel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBLabelUpdate', function(event, result) {
            vm.lIBLabel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
