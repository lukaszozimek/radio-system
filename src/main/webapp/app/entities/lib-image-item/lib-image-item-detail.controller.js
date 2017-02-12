(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibImageItemDetailController', LibImageItemDetailController);

    LibImageItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibImageItem', 'LibLibrary'];

    function LibImageItemDetailController($scope, $rootScope, $stateParams, previousState, entity, LibImageItem, LibLibrary) {
        var vm = this;

        vm.libImageItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libImageItemUpdate', function(event, result) {
            vm.libImageItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
