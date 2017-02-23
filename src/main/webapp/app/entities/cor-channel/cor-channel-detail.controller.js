(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorChannelDetailController', CorChannelDetailController);

    CorChannelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorChannel', 'CorNetwork'];

    function CorChannelDetailController($scope, $rootScope, $stateParams, previousState, entity, CorChannel, CorNetwork) {
        var vm = this;

        vm.corChannel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corChannelUpdate', function(event, result) {
            vm.corChannel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
