(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBMediaItemDetailController', LIBMediaItemDetailController);

    LIBMediaItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBMediaItem', 'LIBLibrary', 'LIBLabel', 'LIBArtist', 'LIBAlbum', 'LIBTrack'];

    function LIBMediaItemDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBMediaItem, LIBLibrary, LIBLabel, LIBArtist, LIBAlbum, LIBTrack) {
        var vm = this;

        vm.lIBMediaItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBMediaItemUpdate', function(event, result) {
            vm.lIBMediaItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
