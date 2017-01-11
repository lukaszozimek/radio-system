(function() {
    'use strict';

    angular
        .module('protoneApp')
        .controller('LIBAlbumDialogController', LIBAlbumDialogController);

    LIBAlbumDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'LIBAlbum', 'LIBImageItem', 'LIBLabel', 'LIBArtist', 'CORNetwork'];

    function LIBAlbumDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, LIBAlbum, LIBImageItem, LIBLabel, LIBArtist, CORNetwork) {
        var vm = this;

        vm.lIBAlbum = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.covers = LIBImageItem.query({filter: 'libalbum-is-null'});
        $q.all([vm.lIBAlbum.$promise, vm.covers.$promise]).then(function() {
            if (!vm.lIBAlbum.coverId) {
                return $q.reject();
            }
            return LIBImageItem.get({id : vm.lIBAlbum.coverId}).$promise;
        }).then(function(cover) {
            vm.covers.push(cover);
        });
        vm.liblabels = LIBLabel.query();
        vm.libartists = LIBArtist.query();
        vm.cornetworks = CORNetwork.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lIBAlbum.id !== null) {
                LIBAlbum.update(vm.lIBAlbum, onSaveSuccess, onSaveError);
            } else {
                LIBAlbum.save(vm.lIBAlbum, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('protoneApp:lIBAlbumUpdate', result);
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
