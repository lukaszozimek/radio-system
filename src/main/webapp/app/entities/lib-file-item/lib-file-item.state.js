(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-file-item', {
            parent: 'entity',
            url: '/lib-file-item',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libFileItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-file-item/lib-file-items.html',
                    controller: 'LibFileItemController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libFileItem');
                    $translatePartialLoader.addPart('libFileTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-file-item-detail', {
            parent: 'lib-file-item',
            url: '/lib-file-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libFileItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-file-item/lib-file-item-detail.html',
                    controller: 'LibFileItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libFileItem');
                    $translatePartialLoader.addPart('libFileTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibFileItem', function($stateParams, LibFileItem) {
                    return LibFileItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-file-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-file-item-detail.edit', {
            parent: 'lib-file-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-file-item/lib-file-item-dialog.html',
                    controller: 'LibFileItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibFileItem', function(LibFileItem) {
                            return LibFileItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-file-item.new', {
            parent: 'lib-file-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-file-item/lib-file-item-dialog.html',
                    controller: 'LibFileItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idx: null,
                                name: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lib-file-item', null, { reload: 'lib-file-item' });
                }, function() {
                    $state.go('lib-file-item');
                });
            }]
        })
        .state('lib-file-item.edit', {
            parent: 'lib-file-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-file-item/lib-file-item-dialog.html',
                    controller: 'LibFileItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibFileItem', function(LibFileItem) {
                            return LibFileItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-file-item', null, { reload: 'lib-file-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-file-item.delete', {
            parent: 'lib-file-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-file-item/lib-file-item-delete-dialog.html',
                    controller: 'LibFileItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibFileItem', function(LibFileItem) {
                            return LibFileItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-file-item', null, { reload: 'lib-file-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
