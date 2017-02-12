(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmContactStatusDetailController', CrmContactStatusDetailController);

    CrmContactStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CrmContactStatus', 'CorNetwork'];

    function CrmContactStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, CrmContactStatus, CorNetwork) {
        var vm = this;

        vm.crmContactStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:crmContactStatusUpdate', function(event, result) {
            vm.crmContactStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
