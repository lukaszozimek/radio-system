(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorPropertyKeyDetailController', CorPropertyKeyDetailController);

    CorPropertyKeyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorPropertyKey', 'CorNetwork'];

    function CorPropertyKeyDetailController($scope, $rootScope, $stateParams, previousState, entity, CorPropertyKey, CorNetwork) {
        var vm = this;

        vm.corPropertyKey = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corPropertyKeyUpdate', function(event, result) {
            vm.corPropertyKey = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
