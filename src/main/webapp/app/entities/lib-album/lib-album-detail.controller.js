(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibAlbumDetailController', LibAlbumDetailController);

    LibAlbumDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibAlbum', 'LibImageItem', 'LibLabel', 'LibArtist', 'CorNetwork'];

    function LibAlbumDetailController($scope, $rootScope, $stateParams, previousState, entity, LibAlbum, LibImageItem, LibLabel, LibArtist, CorNetwork) {
        var vm = this;

        vm.libAlbum = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libAlbumUpdate', function(event, result) {
            vm.libAlbum = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
