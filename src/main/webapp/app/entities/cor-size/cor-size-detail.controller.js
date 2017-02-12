(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorSizeDetailController', CorSizeDetailController);

    CorSizeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorSize', 'CorNetwork'];

    function CorSizeDetailController($scope, $rootScope, $stateParams, previousState, entity, CorSize, CorNetwork) {
        var vm = this;

        vm.corSize = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corSizeUpdate', function(event, result) {
            vm.corSize = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
