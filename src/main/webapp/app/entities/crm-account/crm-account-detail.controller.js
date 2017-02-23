(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmAccountDetailController', CrmAccountDetailController);

    CrmAccountDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CrmAccount', 'CorPerson', 'CorAddress', 'CorNetwork', 'TraDiscount', 'CorUser', 'CorCountry', 'CorRange', 'CorSize', 'TraIndustry', 'CorArea', 'CrmTask'];

    function CrmAccountDetailController($scope, $rootScope, $stateParams, previousState, entity, CrmAccount, CorPerson, CorAddress, CorNetwork, TraDiscount, CorUser, CorCountry, CorRange, CorSize, TraIndustry, CorArea, CrmTask) {
        var vm = this;

        vm.crmAccount = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:crmAccountUpdate', function(event, result) {
            vm.crmAccount = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
