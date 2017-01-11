(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBAlbumDetailController', LIBAlbumDetailController);

    LIBAlbumDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBAlbum', 'LIBImageItem', 'LIBLabel', 'LIBArtist', 'CORNetwork'];

    function LIBAlbumDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBAlbum, LIBImageItem, LIBLabel, LIBArtist, CORNetwork) {
        var vm = this;

        vm.lIBAlbum = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBAlbumUpdate', function(event, result) {
            vm.lIBAlbum = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
