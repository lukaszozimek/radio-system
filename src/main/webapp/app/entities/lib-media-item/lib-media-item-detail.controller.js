(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibMediaItemDetailController', LibMediaItemDetailController);

    LibMediaItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibMediaItem', 'LibLibrary', 'LibLabel', 'LibArtist', 'LibAlbum', 'LibTrack'];

    function LibMediaItemDetailController($scope, $rootScope, $stateParams, previousState, entity, LibMediaItem, LibLibrary, LibLabel, LibArtist, LibAlbum, LibTrack) {
        var vm = this;

        vm.libMediaItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libMediaItemUpdate', function(event, result) {
            vm.libMediaItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
