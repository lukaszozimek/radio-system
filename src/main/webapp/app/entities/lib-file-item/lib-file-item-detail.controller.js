(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibFileItemDetailController', LibFileItemDetailController);

    LibFileItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibFileItem', 'LibLibrary'];

    function LibFileItemDetailController($scope, $rootScope, $stateParams, previousState, entity, LibFileItem, LibLibrary) {
        var vm = this;

        vm.libFileItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libFileItemUpdate', function(event, result) {
            vm.libFileItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
