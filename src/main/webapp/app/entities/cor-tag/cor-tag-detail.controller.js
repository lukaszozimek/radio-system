(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorTagDetailController', CorTagDetailController);

    CorTagDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorTag', 'CorNetwork', 'LibMediaItem'];

    function CorTagDetailController($scope, $rootScope, $stateParams, previousState, entity, CorTag, CorNetwork, LibMediaItem) {
        var vm = this;

        vm.corTag = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corTagUpdate', function(event, result) {
            vm.corTag = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
