(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('TraAdvertisementDialogController', TraAdvertisementDialogController);

    TraAdvertisementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TraAdvertisement', 'LibMediaItem', 'TraIndustry', 'TraAdvertismentType', 'CrmAccount', 'CorNetwork'];

    function TraAdvertisementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TraAdvertisement, LibMediaItem, TraIndustry, TraAdvertismentType, CrmAccount, CorNetwork) {
        var vm = this;

        vm.traAdvertisement = entity;
        vm.clear = clear;
        vm.save = save;
        vm.mediaitems = LibMediaItem.query({filter: 'traadvertisement-is-null'});
        $q.all([vm.traAdvertisement.$promise, vm.mediaitems.$promise]).then(function() {
            if (!vm.traAdvertisement.mediaItemId) {
                return $q.reject();
            }
            return LibMediaItem.get({id : vm.traAdvertisement.mediaItemId}).$promise;
        }).then(function(mediaItem) {
            vm.mediaitems.push(mediaItem);
        });
        vm.libitems = LibMediaItem.query({filter: 'traadvertisement-is-null'});
        $q.all([vm.traAdvertisement.$promise, vm.libitems.$promise]).then(function() {
            if (!vm.traAdvertisement.libitemId) {
                return $q.reject();
            }
            return LibMediaItem.get({id : vm.traAdvertisement.libitemId}).$promise;
        }).then(function(libitem) {
            vm.libitems.push(libitem);
        });
        vm.industries = TraIndustry.query({filter: 'traadvertisement-is-null'});
        $q.all([vm.traAdvertisement.$promise, vm.industries.$promise]).then(function() {
            if (!vm.traAdvertisement.industryId) {
                return $q.reject();
            }
            return TraIndustry.get({id : vm.traAdvertisement.industryId}).$promise;
        }).then(function(industry) {
            vm.industries.push(industry);
        });
        vm.types = TraAdvertismentType.query({filter: 'traadvertisement-is-null'});
        $q.all([vm.traAdvertisement.$promise, vm.types.$promise]).then(function() {
            if (!vm.traAdvertisement.typeId) {
                return $q.reject();
            }
            return TraAdvertismentType.get({id : vm.traAdvertisement.typeId}).$promise;
        }).then(function(type) {
            vm.types.push(type);
        });
        vm.crmaccounts = CrmAccount.query();
        vm.cornetworks = CorNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.traAdvertisement.id !== null) {
                TraAdvertisement.update(vm.traAdvertisement, onSaveSuccess, onSaveError);
            } else {
                TraAdvertisement.save(vm.traAdvertisement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:traAdvertisementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
