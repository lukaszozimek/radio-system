(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-video-object', {
            parent: 'entity',
            url: '/lib-video-object',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libVideoObject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-video-object/lib-video-objects.html',
                    controller: 'LibVideoObjectController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libVideoObject');
                    $translatePartialLoader.addPart('libVideoQualityEnum');
                    $translatePartialLoader.addPart('libAspecTratioEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-video-object-detail', {
            parent: 'lib-video-object',
            url: '/lib-video-object/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libVideoObject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-video-object/lib-video-object-detail.html',
                    controller: 'LibVideoObjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libVideoObject');
                    $translatePartialLoader.addPart('libVideoQualityEnum');
                    $translatePartialLoader.addPart('libAspecTratioEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibVideoObject', function($stateParams, LibVideoObject) {
                    return LibVideoObject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-video-object',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-video-object-detail.edit', {
            parent: 'lib-video-object-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-video-object/lib-video-object-dialog.html',
                    controller: 'LibVideoObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibVideoObject', function(LibVideoObject) {
                            return LibVideoObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-video-object.new', {
            parent: 'lib-video-object',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-video-object/lib-video-object-dialog.html',
                    controller: 'LibVideoObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                width: null,
                                height: null,
                                length: null,
                                biTrate: null,
                                codec: null,
                                quality: null,
                                aspecTratio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lib-video-object', null, { reload: 'lib-video-object' });
                }, function() {
                    $state.go('lib-video-object');
                });
            }]
        })
        .state('lib-video-object.edit', {
            parent: 'lib-video-object',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-video-object/lib-video-object-dialog.html',
                    controller: 'LibVideoObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibVideoObject', function(LibVideoObject) {
                            return LibVideoObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-video-object', null, { reload: 'lib-video-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-video-object.delete', {
            parent: 'lib-video-object',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-video-object/lib-video-object-delete-dialog.html',
                    controller: 'LibVideoObjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibVideoObject', function(LibVideoObject) {
                            return LibVideoObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-video-object', null, { reload: 'lib-video-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
