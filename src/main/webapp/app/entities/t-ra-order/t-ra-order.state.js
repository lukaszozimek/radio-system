(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-ra-order', {
            parent: 'entity',
            url: '/t-ra-order',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRAOrder.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-order/t-ra-orders.html',
                    controller: 'TRAOrderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRAOrder');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-ra-order-detail', {
            parent: 'entity',
            url: '/t-ra-order/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRAOrder.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-order/t-ra-order-detail.html',
                    controller: 'TRAOrderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRAOrder');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TRAOrder', function($stateParams, TRAOrder) {
                    return TRAOrder.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-ra-order',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-ra-order-detail.edit', {
            parent: 't-ra-order-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-order/t-ra-order-dialog.html',
                    controller: 'TRAOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRAOrder', function(TRAOrder) {
                            return TRAOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-order.new', {
            parent: 't-ra-order',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-order/t-ra-order-dialog.html',
                    controller: 'TRAOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                startDate: null,
                                endDate: null,
                                calculatedPrize: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-ra-order', null, { reload: 't-ra-order' });
                }, function() {
                    $state.go('t-ra-order');
                });
            }]
        })
        .state('t-ra-order.edit', {
            parent: 't-ra-order',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-order/t-ra-order-dialog.html',
                    controller: 'TRAOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRAOrder', function(TRAOrder) {
                            return TRAOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-order', null, { reload: 't-ra-order' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-order.delete', {
            parent: 't-ra-order',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-order/t-ra-order-delete-dialog.html',
                    controller: 'TRAOrderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TRAOrder', function(TRAOrder) {
                            return TRAOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-order', null, { reload: 't-ra-order' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
