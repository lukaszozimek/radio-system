(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORPropertyValueDetailController', CORPropertyValueDetailController);

    CORPropertyValueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CORPropertyValue', 'CORPropertyKey'];

    function CORPropertyValueDetailController($scope, $rootScope, $stateParams, previousState, entity, CORPropertyValue, CORPropertyKey) {
        var vm = this;

        vm.cORPropertyValue = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cORPropertyValueUpdate', function(event, result) {
            vm.cORPropertyValue = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
