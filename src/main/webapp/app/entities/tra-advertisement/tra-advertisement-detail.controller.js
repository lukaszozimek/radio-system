(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraAdvertisementDetailController', TraAdvertisementDetailController);

    TraAdvertisementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TraAdvertisement', 'LibMediaItem', 'TraIndustry', 'TraAdvertismentType', 'CrmAccount', 'CorNetwork'];

    function TraAdvertisementDetailController($scope, $rootScope, $stateParams, previousState, entity, TraAdvertisement, LibMediaItem, TraIndustry, TraAdvertismentType, CrmAccount, CorNetwork) {
        var vm = this;

        vm.traAdvertisement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:traAdvertisementUpdate', function(event, result) {
            vm.traAdvertisement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
