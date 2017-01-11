(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAAdvertisementDetailController', TRAAdvertisementDetailController);

    TRAAdvertisementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TRAAdvertisement', 'LIBMediaItem', 'TRACustomer', 'TRAIndustry'];

    function TRAAdvertisementDetailController($scope, $rootScope, $stateParams, previousState, entity, TRAAdvertisement, LIBMediaItem, TRACustomer, TRAIndustry) {
        var vm = this;

        vm.tRAAdvertisement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:tRAAdvertisementUpdate', function(event, result) {
            vm.tRAAdvertisement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
