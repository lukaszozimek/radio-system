(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibTrackDetailController', LibTrackDetailController);

    LibTrackDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibTrack', 'LibAlbum', 'LibArtist', 'CorNetwork'];

    function LibTrackDetailController($scope, $rootScope, $stateParams, previousState, entity, LibTrack, LibAlbum, LibArtist, CorNetwork) {
        var vm = this;

        vm.libTrack = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libTrackUpdate', function(event, result) {
            vm.libTrack = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
