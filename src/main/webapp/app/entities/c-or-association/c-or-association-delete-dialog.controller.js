(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORAssociationDeleteController',CORAssociationDeleteController);

    CORAssociationDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORAssociation'];

    function CORAssociationDeleteController($uibModalInstance, entity, CORAssociation) {
        var vm = this;

        vm.cORAssociation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORAssociation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
