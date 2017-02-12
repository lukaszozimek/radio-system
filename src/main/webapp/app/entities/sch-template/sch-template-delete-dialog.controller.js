(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SchTemplateDeleteController',SchTemplateDeleteController);

    SchTemplateDeleteController.$inject = ['$uibModalInstance', 'entity', 'SchTemplate'];

    function SchTemplateDeleteController($uibModalInstance, entity, SchTemplate) {
        var vm = this;

        vm.schTemplate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SchTemplate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
