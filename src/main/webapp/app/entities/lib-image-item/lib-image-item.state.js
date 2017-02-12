(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-image-item', {
            parent: 'entity',
            url: '/lib-image-item',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libImageItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-image-item/lib-image-items.html',
                    controller: 'LibImageItemController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libImageItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-image-item-detail', {
            parent: 'lib-image-item',
            url: '/lib-image-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libImageItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-image-item/lib-image-item-detail.html',
                    controller: 'LibImageItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libImageItem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibImageItem', function($stateParams, LibImageItem) {
                    return LibImageItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-image-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-image-item-detail.edit', {
            parent: 'lib-image-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-image-item/lib-image-item-dialog.html',
                    controller: 'LibImageItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibImageItem', function(LibImageItem) {
                            return LibImageItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-image-item.new', {
            parent: 'lib-image-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-image-item/lib-image-item-dialog.html',
                    controller: 'LibImageItemDialogController',
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
                    $state.go('lib-image-item', null, { reload: 'lib-image-item' });
                }, function() {
                    $state.go('lib-image-item');
                });
            }]
        })
        .state('lib-image-item.edit', {
            parent: 'lib-image-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-image-item/lib-image-item-dialog.html',
                    controller: 'LibImageItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibImageItem', function(LibImageItem) {
                            return LibImageItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-image-item', null, { reload: 'lib-image-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-image-item.delete', {
            parent: 'lib-image-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-image-item/lib-image-item-delete-dialog.html',
                    controller: 'LibImageItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibImageItem', function(LibImageItem) {
                            return LibImageItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-image-item', null, { reload: 'lib-image-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
