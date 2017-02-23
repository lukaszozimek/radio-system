(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LibAlbumDialogController', LibAlbumDialogController);

    LibAlbumDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'LibAlbum', 'LibImageItem', 'LibLabel', 'LibArtist', 'CorNetwork'];

    function LibAlbumDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, LibAlbum, LibImageItem, LibLabel, LibArtist, CorNetwork) {
        var vm = this;

        vm.libAlbum = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.covers = LibImageItem.query({filter: 'libalbum-is-null'});
        $q.all([vm.libAlbum.$promise, vm.covers.$promise]).then(function() {
            if (!vm.libAlbum.coverId) {
                return $q.reject();
            }
            return LibImageItem.get({id : vm.libAlbum.coverId}).$promise;
        }).then(function(cover) {
            vm.covers.push(cover);
        });
        vm.liblabels = LibLabel.query();
        vm.libartists = LibArtist.query();
        vm.cornetworks = CorNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.libAlbum.id !== null) {
                LibAlbum.update(vm.libAlbum, onSaveSuccess, onSaveError);
            } else {
                LibAlbum.save(vm.libAlbum, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:libAlbumUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.releaseDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
