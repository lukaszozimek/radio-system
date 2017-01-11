(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CRMLeadSourceDetailController', CRMLeadSourceDetailController);

    CRMLeadSourceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CRMLeadSource', 'CORNetwork'];

    function CRMLeadSourceDetailController($scope, $rootScope, $stateParams, previousState, entity, CRMLeadSource, CORNetwork) {
        var vm = this;

        vm.cRMLeadSource = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:cRMLeadSourceUpdate', function(event, result) {
            vm.cRMLeadSource = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
