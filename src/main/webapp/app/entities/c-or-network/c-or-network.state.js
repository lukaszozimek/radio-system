(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-network', {
            parent: 'entity',
            url: '/c-or-network',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORNetwork.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-network/c-or-networks.html',
                    controller: 'CORNetworkController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORNetwork');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-network-detail', {
            parent: 'entity',
            url: '/c-or-network/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORNetwork.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-network/c-or-network-detail.html',
                    controller: 'CORNetworkDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORNetwork');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORNetwork', function($stateParams, CORNetwork) {
                    return CORNetwork.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-network',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-network-detail.edit', {
            parent: 'c-or-network-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-network/c-or-network-dialog.html',
                    controller: 'CORNetworkDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORNetwork', function(CORNetwork) {
                            return CORNetwork.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-network.new', {
            parent: 'c-or-network',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-network/c-or-network-dialog.html',
                    controller: 'CORNetworkDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                shortcut: null,
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-or-network', null, { reload: 'c-or-network' });
                }, function() {
                    $state.go('c-or-network');
                });
            }]
        })
        .state('c-or-network.edit', {
            parent: 'c-or-network',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-network/c-or-network-dialog.html',
                    controller: 'CORNetworkDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORNetwork', function(CORNetwork) {
                            return CORNetwork.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-network', null, { reload: 'c-or-network' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-network.delete', {
            parent: 'c-or-network',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-network/c-or-network-delete-dialog.html',
                    controller: 'CORNetworkDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORNetwork', function(CORNetwork) {
                            return CORNetwork.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-network', null, { reload: 'c-or-network' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
