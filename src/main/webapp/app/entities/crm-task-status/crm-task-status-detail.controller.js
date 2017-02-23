(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmTaskStatusDetailController', CrmTaskStatusDetailController);

    CrmTaskStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CrmTaskStatus', 'CorNetwork'];

    function CrmTaskStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, CrmTaskStatus, CorNetwork) {
        var vm = this;

        vm.crmTaskStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:crmTaskStatusUpdate', function(event, result) {
            vm.crmTaskStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
