(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CrmContactDetailController', CrmContactDetailController);

    CrmContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CrmContact', 'CorAddress', 'CorCountry', 'CorPerson', 'CorNetwork', 'CorRange', 'CorSize', 'TraIndustry', 'CorArea', 'CorUser', 'CrmContactStatus', 'CrmTask'];

    function CrmContactDetailController($scope, $rootScope, $stateParams, previousState, entity, CrmContact, CorAddress, CorCountry, CorPerson, CorNetwork, CorRange, CorSize, TraIndustry, CorArea, CorUser, CrmContactStatus, CrmTask) {
        var vm = this;

        vm.crmContact = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:crmContactUpdate', function(event, result) {
            vm.crmContact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
