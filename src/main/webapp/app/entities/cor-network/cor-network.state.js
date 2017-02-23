(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-network', {
            parent: 'entity',
            url: '/cor-network',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corNetwork.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-network/cor-networks.html',
                    controller: 'CorNetworkController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corNetwork');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-network-detail', {
            parent: 'cor-network',
            url: '/cor-network/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corNetwork.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-network/cor-network-detail.html',
                    controller: 'CorNetworkDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corNetwork');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorNetwork', function($stateParams, CorNetwork) {
                    return CorNetwork.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-network',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-network-detail.edit', {
            parent: 'cor-network-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-network/cor-network-dialog.html',
                    controller: 'CorNetworkDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorNetwork', function(CorNetwork) {
                            return CorNetwork.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-network.new', {
            parent: 'cor-network',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-network/cor-network-dialog.html',
                    controller: 'CorNetworkDialogController',
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
                    $state.go('cor-network', null, { reload: 'cor-network' });
                }, function() {
                    $state.go('cor-network');
                });
            }]
        })
        .state('cor-network.edit', {
            parent: 'cor-network',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-network/cor-network-dialog.html',
                    controller: 'CorNetworkDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorNetwork', function(CorNetwork) {
                            return CorNetwork.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-network', null, { reload: 'cor-network' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-network.delete', {
            parent: 'cor-network',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-network/cor-network-delete-dialog.html',
                    controller: 'CorNetworkDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorNetwork', function(CorNetwork) {
                            return CorNetwork.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-network', null, { reload: 'cor-network' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
