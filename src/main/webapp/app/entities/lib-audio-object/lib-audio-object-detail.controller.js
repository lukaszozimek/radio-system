(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibAudioObjectDetailController', LibAudioObjectDetailController);

    LibAudioObjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LibAudioObject', 'LibCloudObject', 'LibMediaItem'];

    function LibAudioObjectDetailController($scope, $rootScope, $stateParams, previousState, entity, LibAudioObject, LibCloudObject, LibMediaItem) {
        var vm = this;

        vm.libAudioObject = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('protoneApp:libAudioObjectUpdate', function(event, result) {
            vm.libAudioObject = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
