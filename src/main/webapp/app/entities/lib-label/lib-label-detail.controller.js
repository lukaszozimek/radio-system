(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibLabelDetailController', LibLabelDetailController);

    LibLabelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibLabel', 'CorNetwork'];

    function LibLabelDetailController($scope, $rootScope, $stateParams, previousState, entity, LibLabel, CorNetwork) {
        var vm = this;

        vm.libLabel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libLabelUpdate', function(event, result) {
            vm.libLabel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
