(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMAccountDetailController', CRMAccountDetailController);

    CRMAccountDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CRMAccount', 'CORNetwork'];

    function CRMAccountDetailController($scope, $rootScope, $stateParams, previousState, entity, CRMAccount, CORNetwork) {
        var vm = this;

        vm.cRMAccount = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cRMAccountUpdate', function(event, result) {
            vm.cRMAccount = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
