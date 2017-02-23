(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tra-order', {
            parent: 'entity',
            url: '/tra-order',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traOrder.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-order/tra-orders.html',
                    controller: 'TraOrderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traOrder');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tra-order-detail', {
            parent: 'tra-order',
            url: '/tra-order/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traOrder.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-order/tra-order-detail.html',
                    controller: 'TraOrderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traOrder');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraOrder', function($stateParams, TraOrder) {
                    return TraOrder.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tra-order',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tra-order-detail.edit', {
            parent: 'tra-order-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-order/tra-order-dialog.html',
                    controller: 'TraOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraOrder', function(TraOrder) {
                            return TraOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-order.new', {
            parent: 'tra-order',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-order/tra-order-dialog.html',
                    controller: 'TraOrderDialogController',
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
                    $state.go('tra-order', null, { reload: 'tra-order' });
                }, function() {
                    $state.go('tra-order');
                });
            }]
        })
        .state('tra-order.edit', {
            parent: 'tra-order',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-order/tra-order-dialog.html',
                    controller: 'TraOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraOrder', function(TraOrder) {
                            return TraOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-order', null, { reload: 'tra-order' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-order.delete', {
            parent: 'tra-order',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-order/tra-order-delete-dialog.html',
                    controller: 'TraOrderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraOrder', function(TraOrder) {
                            return TraOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-order', null, { reload: 'tra-order' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
