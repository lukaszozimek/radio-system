(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibAudioObjectController', LibAudioObjectController);

    LibAudioObjectController.$inject = ['$scope', '$state', 'LibAudioObject'];

    function LibAudioObjectController ($scope, $state, LibAudioObject) {
        var vm = this;

        vm.libAudioObjects = [];

        loadAll();

        function loadAll() {
            LibAudioObject.query(function(result) {
                vm.libAudioObjects = result;
                vm.searchQuery = null;
            });
        }
    }
})();
