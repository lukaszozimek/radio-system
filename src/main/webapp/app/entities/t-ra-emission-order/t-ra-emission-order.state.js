(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-ra-emission-order', {
            parent: 'entity',
            url: '/t-ra-emission-order',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRAEmissionOrder.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-emission-order/t-ra-emission-orders.html',
                    controller: 'TRAEmissionOrderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRAEmissionOrder');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-ra-emission-order-detail', {
            parent: 'entity',
            url: '/t-ra-emission-order/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRAEmissionOrder.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-emission-order/t-ra-emission-order-detail.html',
                    controller: 'TRAEmissionOrderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRAEmissionOrder');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TRAEmissionOrder', function($stateParams, TRAEmissionOrder) {
                    return TRAEmissionOrder.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-ra-emission-order',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-ra-emission-order-detail.edit', {
            parent: 't-ra-emission-order-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-emission-order/t-ra-emission-order-dialog.html',
                    controller: 'TRAEmissionOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRAEmissionOrder', function(TRAEmissionOrder) {
                            return TRAEmissionOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-emission-order.new', {
            parent: 't-ra-emission-order',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-emission-order/t-ra-emission-order-dialog.html',
                    controller: 'TRAEmissionOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                calculatedPrice: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-ra-emission-order', null, { reload: 't-ra-emission-order' });
                }, function() {
                    $state.go('t-ra-emission-order');
                });
            }]
        })
        .state('t-ra-emission-order.edit', {
            parent: 't-ra-emission-order',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-emission-order/t-ra-emission-order-dialog.html',
                    controller: 'TRAEmissionOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRAEmissionOrder', function(TRAEmissionOrder) {
                            return TRAEmissionOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-emission-order', null, { reload: 't-ra-emission-order' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-emission-order.delete', {
            parent: 't-ra-emission-order',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-emission-order/t-ra-emission-order-delete-dialog.html',
                    controller: 'TRAEmissionOrderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TRAEmissionOrder', function(TRAEmissionOrder) {
                            return TRAEmissionOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-emission-order', null, { reload: 't-ra-emission-order' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
