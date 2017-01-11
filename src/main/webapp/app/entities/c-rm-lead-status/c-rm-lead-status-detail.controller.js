(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMLeadStatusDetailController', CRMLeadStatusDetailController);

    CRMLeadStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CRMLeadStatus', 'CORNetwork'];

    function CRMLeadStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, CRMLeadStatus, CORNetwork) {
        var vm = this;

        vm.cRMLeadStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cRMLeadStatusUpdate', function(event, result) {
            vm.cRMLeadStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
