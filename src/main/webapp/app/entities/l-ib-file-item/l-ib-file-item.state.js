(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-file-item', {
            parent: 'entity',
            url: '/l-ib-file-item',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBFileItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-file-item/l-ib-file-items.html',
                    controller: 'LIBFileItemController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBFileItem');
                    $translatePartialLoader.addPart('lIBFileTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-file-item-detail', {
            parent: 'entity',
            url: '/l-ib-file-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBFileItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-file-item/l-ib-file-item-detail.html',
                    controller: 'LIBFileItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBFileItem');
                    $translatePartialLoader.addPart('lIBFileTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBFileItem', function($stateParams, LIBFileItem) {
                    return LIBFileItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-file-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-file-item-detail.edit', {
            parent: 'l-ib-file-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-file-item/l-ib-file-item-dialog.html',
                    controller: 'LIBFileItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBFileItem', function(LIBFileItem) {
                            return LIBFileItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-file-item.new', {
            parent: 'l-ib-file-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-file-item/l-ib-file-item-dialog.html',
                    controller: 'LIBFileItemDialogController',
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
                    $state.go('l-ib-file-item', null, { reload: 'l-ib-file-item' });
                }, function() {
                    $state.go('l-ib-file-item');
                });
            }]
        })
        .state('l-ib-file-item.edit', {
            parent: 'l-ib-file-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-file-item/l-ib-file-item-dialog.html',
                    controller: 'LIBFileItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBFileItem', function(LIBFileItem) {
                            return LIBFileItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-file-item', null, { reload: 'l-ib-file-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-file-item.delete', {
            parent: 'l-ib-file-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-file-item/l-ib-file-item-delete-dialog.html',
                    controller: 'LIBFileItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBFileItem', function(LIBFileItem) {
                            return LIBFileItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-file-item', null, { reload: 'l-ib-file-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
