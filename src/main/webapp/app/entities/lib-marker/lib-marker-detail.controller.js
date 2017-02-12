(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibMarkerDetailController', LibMarkerDetailController);

    LibMarkerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibMarker', 'LibMediaItem'];

    function LibMarkerDetailController($scope, $rootScope, $stateParams, previousState, entity, LibMarker, LibMediaItem) {
        var vm = this;

        vm.libMarker = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libMarkerUpdate', function(event, result) {
            vm.libMarker = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
