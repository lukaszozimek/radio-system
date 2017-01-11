(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('SCHTemplateDeleteController',SCHTemplateDeleteController);

    SCHTemplateDeleteController.$inject = ['$uibModalInstance', 'entity', 'SCHTemplate'];

    function SCHTemplateDeleteController($uibModalInstance, entity, SCHTemplate) {
        var vm = this;

        vm.sCHTemplate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SCHTemplate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
