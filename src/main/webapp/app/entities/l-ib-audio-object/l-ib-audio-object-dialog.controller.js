(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBAudioObjectDialogController', LIBAudioObjectDialogController);

    LIBAudioObjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'LIBAudioObject', 'LIBCloudObject', 'LIBMediaItem'];

    function LIBAudioObjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, LIBAudioObject, LIBCloudObject, LIBMediaItem) {
        var vm = this;

        vm.lIBAudioObject = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cloudobjects = LIBCloudObject.query({filter: 'libaudioobject-is-null'});
        $q.all([vm.lIBAudioObject.$promise, vm.cloudobjects.$promise]).then(function() {
            if (!vm.lIBAudioObject.cloudObjectId) {
                return $q.reject();
            }
            return LIBCloudObject.get({id : vm.lIBAudioObject.cloudObjectId}).$promise;
        }).then(function(cloudObject) {
            vm.cloudobjects.push(cloudObject);
        });
        vm.libmediaitems = LIBMediaItem.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lIBAudioObject.id !== null) {
                LIBAudioObject.update(vm.lIBAudioObject, onSaveSuccess, onSaveError);
            } else {
                LIBAudioObject.save(vm.lIBAudioObject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBAudioObjectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
