(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBVideoObjectDeleteController',LIBVideoObjectDeleteController);

    LIBVideoObjectDeleteController.$inject = ['$uibModalInstance', 'entity', 'LIBVideoObject'];

    function LIBVideoObjectDeleteController($uibModalInstance, entity, LIBVideoObject) {
        var vm = this;

        vm.lIBVideoObject = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LIBVideoObject.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
