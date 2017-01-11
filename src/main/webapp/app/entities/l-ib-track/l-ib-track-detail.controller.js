(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBTrackDetailController', LIBTrackDetailController);

    LIBTrackDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBTrack', 'LIBAlbum', 'LIBArtist', 'CORNetwork'];

    function LIBTrackDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBTrack, LIBAlbum, LIBArtist, CORNetwork) {
        var vm = this;

        vm.lIBTrack = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBTrackUpdate', function(event, result) {
            vm.lIBTrack = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
