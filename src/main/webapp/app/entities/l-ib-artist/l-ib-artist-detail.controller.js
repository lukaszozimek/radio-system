(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBArtistDetailController', LIBArtistDetailController);

    LIBArtistDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LIBArtist', 'CORNetwork'];

    function LIBArtistDetailController($scope, $rootScope, $stateParams, previousState, entity, LIBArtist, CORNetwork) {
        var vm = this;

        vm.lIBArtist = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:lIBArtistUpdate', function(event, result) {
            vm.lIBArtist = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
