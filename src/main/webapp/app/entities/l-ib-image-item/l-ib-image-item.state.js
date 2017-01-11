(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-image-item', {
            parent: 'entity',
            url: '/l-ib-image-item',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBImageItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-image-item/l-ib-image-items.html',
                    controller: 'LIBImageItemController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBImageItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-image-item-detail', {
            parent: 'entity',
            url: '/l-ib-image-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBImageItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-image-item/l-ib-image-item-detail.html',
                    controller: 'LIBImageItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBImageItem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBImageItem', function($stateParams, LIBImageItem) {
                    return LIBImageItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-image-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-image-item-detail.edit', {
            parent: 'l-ib-image-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-image-item/l-ib-image-item-dialog.html',
                    controller: 'LIBImageItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBImageItem', function(LIBImageItem) {
                            return LIBImageItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-image-item.new', {
            parent: 'l-ib-image-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-image-item/l-ib-image-item-dialog.html',
                    controller: 'LIBImageItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idx: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('l-ib-image-item', null, { reload: 'l-ib-image-item' });
                }, function() {
                    $state.go('l-ib-image-item');
                });
            }]
        })
        .state('l-ib-image-item.edit', {
            parent: 'l-ib-image-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-image-item/l-ib-image-item-dialog.html',
                    controller: 'LIBImageItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBImageItem', function(LIBImageItem) {
                            return LIBImageItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-image-item', null, { reload: 'l-ib-image-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-image-item.delete', {
            parent: 'l-ib-image-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-image-item/l-ib-image-item-delete-dialog.html',
                    controller: 'LIBImageItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBImageItem', function(LIBImageItem) {
                            return LIBImageItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-image-item', null, { reload: 'l-ib-image-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
