(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibLibraryDetailController', LibLibraryDetailController);

    LibLibraryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibLibrary', 'CorNetwork'];

    function LibLibraryDetailController($scope, $rootScope, $stateParams, previousState, entity, LibLibrary, CorNetwork) {
        var vm = this;

        vm.libLibrary = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libLibraryUpdate', function(event, result) {
            vm.libLibrary = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
