(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBImageObjectDialogController', LIBImageObjectDialogController);

    LIBImageObjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'LIBImageObject', 'LIBCloudObject', 'LIBImageItem'];

    function LIBImageObjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, LIBImageObject, LIBCloudObject, LIBImageItem) {
        var vm = this;

        vm.lIBImageObject = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cloudobjects = LIBCloudObject.query({filter: 'libimageobject-is-null'});
        $q.all([vm.lIBImageObject.$promise, vm.cloudobjects.$promise]).then(function() {
            if (!vm.lIBImageObject.cloudObjectId) {
                return $q.reject();
            }
            return LIBCloudObject.get({id : vm.lIBImageObject.cloudObjectId}).$promise;
        }).then(function(cloudObject) {
            vm.cloudobjects.push(cloudObject);
        });
        vm.libimageitems = LIBImageItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lIBImageObject.id !== null) {
                LIBImageObject.update(vm.lIBImageObject, onSaveSuccess, onSaveError);
            } else {
                LIBImageObject.save(vm.lIBImageObject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBImageObjectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
