(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorPropertyValueDetailController', CorPropertyValueDetailController);

    CorPropertyValueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorPropertyValue', 'CorPropertyKey', 'LibMediaItem'];

    function CorPropertyValueDetailController($scope, $rootScope, $stateParams, previousState, entity, CorPropertyValue, CorPropertyKey, LibMediaItem) {
        var vm = this;

        vm.corPropertyValue = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corPropertyValueUpdate', function(event, result) {
            vm.corPropertyValue = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
