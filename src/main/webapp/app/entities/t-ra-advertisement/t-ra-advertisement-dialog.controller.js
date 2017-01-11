(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TRAAdvertisementDialogController', TRAAdvertisementDialogController);

    TRAAdvertisementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TRAAdvertisement', 'LIBMediaItem', 'TRACustomer', 'TRAIndustry'];

    function TRAAdvertisementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TRAAdvertisement, LIBMediaItem, TRACustomer, TRAIndustry) {
        var vm = this;

        vm.tRAAdvertisement = entity;
        vm.clear = clear;
        vm.save = save;
        vm.mediaitems = LIBMediaItem.query({filter: 'traadvertisement-is-null'});
        $q.all([vm.tRAAdvertisement.$promise, vm.mediaitems.$promise]).then(function() {
            if (!vm.tRAAdvertisement.mediaItemId) {
                return $q.reject();
            }
            return LIBMediaItem.get({id : vm.tRAAdvertisement.mediaItemId}).$promise;
        }).then(function(mediaItem) {
            vm.mediaitems.push(mediaItem);
        });
        vm.tracustomers = TRACustomer.query();
        vm.traindustries = TRAIndustry.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tRAAdvertisement.id !== null) {
                TRAAdvertisement.update(vm.tRAAdvertisement, onSaveSuccess, onSaveError);
            } else {
                TRAAdvertisement.save(vm.tRAAdvertisement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:tRAAdvertisementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
