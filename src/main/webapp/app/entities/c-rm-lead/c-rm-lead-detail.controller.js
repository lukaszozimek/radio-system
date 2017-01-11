(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMLeadDetailController', CRMLeadDetailController);

    CRMLeadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CRMLead', 'CORNetwork'];

    function CRMLeadDetailController($scope, $rootScope, $stateParams, previousState, entity, CRMLead, CORNetwork) {
        var vm = this;

        vm.cRMLead = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cRMLeadUpdate', function(event, result) {
            vm.cRMLead = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
