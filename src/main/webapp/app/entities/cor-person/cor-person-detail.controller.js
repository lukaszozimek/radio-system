(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorPersonDetailController', CorPersonDetailController);

    CorPersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorPerson', 'CorNetwork', 'CorContact'];

    function CorPersonDetailController($scope, $rootScope, $stateParams, previousState, entity, CorPerson, CorNetwork, CorContact) {
        var vm = this;

        vm.corPerson = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corPersonUpdate', function(event, result) {
            vm.corPerson = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
