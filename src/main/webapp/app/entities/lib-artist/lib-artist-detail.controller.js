(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibArtistDetailController', LibArtistDetailController);

    LibArtistDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibArtist', 'CorNetwork'];

    function LibArtistDetailController($scope, $rootScope, $stateParams, previousState, entity, LibArtist, CorNetwork) {
        var vm = this;

        vm.libArtist = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libArtistUpdate', function(event, result) {
            vm.libArtist = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
