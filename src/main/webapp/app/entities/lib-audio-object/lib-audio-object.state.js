(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-audio-object', {
            parent: 'entity',
            url: '/lib-audio-object',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libAudioObject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-audio-object/lib-audio-objects.html',
                    controller: 'LibAudioObjectController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libAudioObject');
                    $translatePartialLoader.addPart('libAudioQualityEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-audio-object-detail', {
            parent: 'lib-audio-object',
            url: '/lib-audio-object/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libAudioObject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-audio-object/lib-audio-object-detail.html',
                    controller: 'LibAudioObjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libAudioObject');
                    $translatePartialLoader.addPart('libAudioQualityEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibAudioObject', function($stateParams, LibAudioObject) {
                    return LibAudioObject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-audio-object',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-audio-object-detail.edit', {
            parent: 'lib-audio-object-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-audio-object/lib-audio-object-dialog.html',
                    controller: 'LibAudioObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibAudioObject', function(LibAudioObject) {
                            return LibAudioObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-audio-object.new', {
            parent: 'lib-audio-object',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-audio-object/lib-audio-object-dialog.html',
                    controller: 'LibAudioObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                length: null,
                                biTrate: null,
                                codec: null,
                                quality: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lib-audio-object', null, { reload: 'lib-audio-object' });
                }, function() {
                    $state.go('lib-audio-object');
                });
            }]
        })
        .state('lib-audio-object.edit', {
            parent: 'lib-audio-object',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-audio-object/lib-audio-object-dialog.html',
                    controller: 'LibAudioObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibAudioObject', function(LibAudioObject) {
                            return LibAudioObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-audio-object', null, { reload: 'lib-audio-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-audio-object.delete', {
            parent: 'lib-audio-object',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-audio-object/lib-audio-object-delete-dialog.html',
                    controller: 'LibAudioObjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibAudioObject', function(LibAudioObject) {
                            return LibAudioObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-audio-object', null, { reload: 'lib-audio-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
