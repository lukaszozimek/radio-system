(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorNotificationDetailController', CorNotificationDetailController);

    CorNotificationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CorNotification', 'CorUser'];

    function CorNotificationDetailController($scope, $rootScope, $stateParams, previousState, entity, CorNotification, CorUser) {
        var vm = this;

        vm.corNotification = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:corNotificationUpdate', function(event, result) {
            vm.corNotification = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
