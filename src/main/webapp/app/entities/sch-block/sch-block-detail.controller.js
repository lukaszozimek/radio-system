(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchBlockDetailController', SchBlockDetailController);

    SchBlockDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SchBlock', 'SchPlaylist', 'SchTemplate'];

    function SchBlockDetailController($scope, $rootScope, $stateParams, previousState, entity, SchBlock, SchPlaylist, SchTemplate) {
        var vm = this;

        vm.schBlock = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:schBlockUpdate', function(event, result) {
            vm.schBlock = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
