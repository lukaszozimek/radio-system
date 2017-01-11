(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORDocumentDeleteController',CORDocumentDeleteController);

    CORDocumentDeleteController.$inject = ['$uibModalInstance', 'entity', 'CORDocument'];

    function CORDocumentDeleteController($uibModalInstance, entity, CORDocument) {
        var vm = this;

        vm.cORDocument = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CORDocument.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
