(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-media-item', {
            parent: 'entity',
            url: '/l-ib-media-item',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBMediaItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-media-item/l-ib-media-items.html',
                    controller: 'LIBMediaItemController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBMediaItem');
                    $translatePartialLoader.addPart('lIBItemTypeEnum');
                    $translatePartialLoader.addPart('lIBItemStateEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-media-item-detail', {
            parent: 'entity',
            url: '/l-ib-media-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBMediaItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-media-item/l-ib-media-item-detail.html',
                    controller: 'LIBMediaItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBMediaItem');
                    $translatePartialLoader.addPart('lIBItemTypeEnum');
                    $translatePartialLoader.addPart('lIBItemStateEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBMediaItem', function($stateParams, LIBMediaItem) {
                    return LIBMediaItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-media-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-media-item-detail.edit', {
            parent: 'l-ib-media-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-media-item/l-ib-media-item-dialog.html',
                    controller: 'LIBMediaItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBMediaItem', function(LIBMediaItem) {
                            return LIBMediaItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-media-item.new', {
            parent: 'l-ib-media-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-media-item/l-ib-media-item-dialog.html',
                    controller: 'LIBMediaItemDialogController',
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
                    $state.go('l-ib-media-item', null, { reload: 'l-ib-media-item' });
                }, function() {
                    $state.go('l-ib-media-item');
                });
            }]
        })
        .state('l-ib-media-item.edit', {
            parent: 'l-ib-media-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-media-item/l-ib-media-item-dialog.html',
                    controller: 'LIBMediaItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBMediaItem', function(LIBMediaItem) {
                            return LIBMediaItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-media-item', null, { reload: 'l-ib-media-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-media-item.delete', {
            parent: 'l-ib-media-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-media-item/l-ib-media-item-delete-dialog.html',
                    controller: 'LIBMediaItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBMediaItem', function(LIBMediaItem) {
                            return LIBMediaItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-media-item', null, { reload: 'l-ib-media-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
