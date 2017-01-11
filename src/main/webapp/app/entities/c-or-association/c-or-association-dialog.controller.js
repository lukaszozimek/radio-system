(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('CORAssociationDialogController', CORAssociationDialogController);

    CORAssociationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CORAssociation', 'CORNetwork'];

    function CORAssociationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CORAssociation, CORNetwork) {
        var vm = this;

        vm.cORAssociation = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cornetworks = CORNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cORAssociation.id !== null) {
                CORAssociation.update(vm.cORAssociation, onSaveSuccess, onSaveError);
            } else {
                CORAssociation.save(vm.cORAssociation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:cORAssociationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
