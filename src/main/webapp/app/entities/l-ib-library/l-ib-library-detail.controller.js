(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBLibraryDetailController', LIBLibraryDetailController);

    LIBLibraryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBLibrary', 'CORNetwork'];

    function LIBLibraryDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBLibrary, CORNetwork) {
        var vm = this;

        vm.lIBLibrary = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBLibraryUpdate', function(event, result) {
            vm.lIBLibrary = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
