(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMContactDetailController', CRMContactDetailController);

    CRMContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CRMContact', 'CORNetwork'];

    function CRMContactDetailController($scope, $rootScope, $stateParams, previousState, entity, CRMContact, CORNetwork) {
        var vm = this;

        vm.cRMContact = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cRMContactUpdate', function(event, result) {
            vm.cRMContact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
