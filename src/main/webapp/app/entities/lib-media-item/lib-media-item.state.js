(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-media-item', {
            parent: 'entity',
            url: '/lib-media-item',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libMediaItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-media-item/lib-media-items.html',
                    controller: 'LibMediaItemController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libMediaItem');
                    $translatePartialLoader.addPart('libItemTypeEnum');
                    $translatePartialLoader.addPart('libItemStateEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-media-item-detail', {
            parent: 'lib-media-item',
            url: '/lib-media-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libMediaItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-media-item/lib-media-item-detail.html',
                    controller: 'LibMediaItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libMediaItem');
                    $translatePartialLoader.addPart('libItemTypeEnum');
                    $translatePartialLoader.addPart('libItemStateEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibMediaItem', function($stateParams, LibMediaItem) {
                    return LibMediaItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-media-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-media-item-detail.edit', {
            parent: 'lib-media-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-media-item/lib-media-item-dialog.html',
                    controller: 'LibMediaItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibMediaItem', function(LibMediaItem) {
                            return LibMediaItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-media-item.new', {
            parent: 'lib-media-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-media-item/lib-media-item-dialog.html',
                    controller: 'LibMediaItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idx: null,
                                name: null,
                                itemType: null,
                                length: null,
                                state: null,
                                command: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lib-media-item', null, { reload: 'lib-media-item' });
                }, function() {
                    $state.go('lib-media-item');
                });
            }]
        })
        .state('lib-media-item.edit', {
            parent: 'lib-media-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-media-item/lib-media-item-dialog.html',
                    controller: 'LibMediaItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibMediaItem', function(LibMediaItem) {
                            return LibMediaItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-media-item', null, { reload: 'lib-media-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-media-item.delete', {
            parent: 'lib-media-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-media-item/lib-media-item-delete-dialog.html',
                    controller: 'LibMediaItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibMediaItem', function(LibMediaItem) {
                            return LibMediaItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-media-item', null, { reload: 'lib-media-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
