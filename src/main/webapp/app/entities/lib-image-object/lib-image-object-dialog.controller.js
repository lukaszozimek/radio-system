(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibImageObjectDialogController', LibImageObjectDialogController);

    LibImageObjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'LibImageObject', 'LibCloudObject', 'LibImageItem'];

    function LibImageObjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, LibImageObject, LibCloudObject, LibImageItem) {
        var vm = this;

        vm.libImageObject = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cloudobjects = LibCloudObject.query({filter: 'libimageobject-is-null'});
        $q.all([vm.libImageObject.$promise, vm.cloudobjects.$promise]).then(function() {
            if (!vm.libImageObject.cloudObjectId) {
                return $q.reject();
            }
            return LibCloudObject.get({id : vm.libImageObject.cloudObjectId}).$promise;
        }).then(function(cloudObject) {
            vm.cloudobjects.push(cloudObject);
        });
        vm.libimageitems = LibImageItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.libImageObject.id !== null) {
                LibImageObject.update(vm.libImageObject, onSaveSuccess, onSaveError);
            } else {
                LibImageObject.save(vm.libImageObject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libImageObjectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
