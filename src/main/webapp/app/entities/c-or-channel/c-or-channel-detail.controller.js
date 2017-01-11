(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORChannelDetailController', CORChannelDetailController);

    CORChannelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CORChannel', 'CORNetwork'];

    function CORChannelDetailController($scope, $rootScope, $stateParams, previousState, entity, CORChannel, CORNetwork) {
        var vm = this;

        vm.cORChannel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cORChannelUpdate', function(event, result) {
            vm.cORChannel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
