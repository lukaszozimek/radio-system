(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchPlaylistDetailController', SchPlaylistDetailController);

    SchPlaylistDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SchPlaylist', 'CorChannel'];

    function SchPlaylistDetailController($scope, $rootScope, $stateParams, previousState, entity, SchPlaylist, CorChannel) {
        var vm = this;

        vm.schPlaylist = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:schPlaylistUpdate', function(event, result) {
            vm.schPlaylist = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
