(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-property-key', {
            parent: 'entity',
            url: '/cor-property-key',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corPropertyKey.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-property-key/cor-property-keys.html',
                    controller: 'CorPropertyKeyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corPropertyKey');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-property-key-detail', {
            parent: 'cor-property-key',
            url: '/cor-property-key/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corPropertyKey.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-property-key/cor-property-key-detail.html',
                    controller: 'CorPropertyKeyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corPropertyKey');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorPropertyKey', function($stateParams, CorPropertyKey) {
                    return CorPropertyKey.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-property-key',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-property-key-detail.edit', {
            parent: 'cor-property-key-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-property-key/cor-property-key-dialog.html',
                    controller: 'CorPropertyKeyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorPropertyKey', function(CorPropertyKey) {
                            return CorPropertyKey.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-property-key.new', {
            parent: 'cor-property-key',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-property-key/cor-property-key-dialog.html',
                    controller: 'CorPropertyKeyDialogController',
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
                    $state.go('cor-property-key', null, { reload: 'cor-property-key' });
                }, function() {
                    $state.go('cor-property-key');
                });
            }]
        })
        .state('cor-property-key.edit', {
            parent: 'cor-property-key',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-property-key/cor-property-key-dialog.html',
                    controller: 'CorPropertyKeyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorPropertyKey', function(CorPropertyKey) {
                            return CorPropertyKey.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-property-key', null, { reload: 'cor-property-key' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-property-key.delete', {
            parent: 'cor-property-key',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-property-key/cor-property-key-delete-dialog.html',
                    controller: 'CorPropertyKeyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorPropertyKey', function(CorPropertyKey) {
                            return CorPropertyKey.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-property-key', null, { reload: 'cor-property-key' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
