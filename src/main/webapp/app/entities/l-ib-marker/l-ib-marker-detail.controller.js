(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBMarkerDetailController', LIBMarkerDetailController);

    LIBMarkerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBMarker', 'LIBMediaItem'];

    function LIBMarkerDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBMarker, LIBMediaItem) {
        var vm = this;

        vm.lIBMarker = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBMarkerUpdate', function(event, result) {
            vm.lIBMarker = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
