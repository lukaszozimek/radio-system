(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-property-key', {
            parent: 'entity',
            url: '/c-or-property-key',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORPropertyKey.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-property-key/c-or-property-keys.html',
                    controller: 'CORPropertyKeyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORPropertyKey');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-property-key-detail', {
            parent: 'entity',
            url: '/c-or-property-key/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORPropertyKey.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-property-key/c-or-property-key-detail.html',
                    controller: 'CORPropertyKeyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORPropertyKey');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORPropertyKey', function($stateParams, CORPropertyKey) {
                    return CORPropertyKey.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-property-key',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-property-key-detail.edit', {
            parent: 'c-or-property-key-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-property-key/c-or-property-key-dialog.html',
                    controller: 'CORPropertyKeyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORPropertyKey', function(CORPropertyKey) {
                            return CORPropertyKey.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-property-key.new', {
            parent: 'c-or-property-key',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-property-key/c-or-property-key-dialog.html',
                    controller: 'CORPropertyKeyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                key: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-or-property-key', null, { reload: 'c-or-property-key' });
                }, function() {
                    $state.go('c-or-property-key');
                });
            }]
        })
        .state('c-or-property-key.edit', {
            parent: 'c-or-property-key',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-property-key/c-or-property-key-dialog.html',
                    controller: 'CORPropertyKeyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORPropertyKey', function(CORPropertyKey) {
                            return CORPropertyKey.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-property-key', null, { reload: 'c-or-property-key' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-property-key.delete', {
            parent: 'c-or-property-key',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-property-key/c-or-property-key-delete-dialog.html',
                    controller: 'CORPropertyKeyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORPropertyKey', function(CORPropertyKey) {
                            return CORPropertyKey.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-property-key', null, { reload: 'c-or-property-key' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
