(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CorNotificationController', CorNotificationController);

    CorNotificationController.$inject = ['$scope', '$state', 'CorNotification'];

    function CorNotificationController ($scope, $state, CorNotification) {
        var vm = this;

        vm.corNotifications = [];

        loadAll();

        function loadAll() {
            CorNotification.query(function(result) {
                vm.corNotifications = result;
                vm.searchQuery = null;
            });
        }
    }
})();
