(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBAudioObjectDeleteController',LIBAudioObjectDeleteController);

    LIBAudioObjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBAudioObject'];

    function LIBAudioObjectDeleteController($uibModalInstance, entity, LIBAudioObject) {
        var vm = this;

        vm.lIBAudioObject = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBAudioObject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
