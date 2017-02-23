(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tra-order-status', {
            parent: 'entity',
            url: '/tra-order-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traOrderStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-order-status/tra-order-statuses.html',
                    controller: 'TraOrderStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traOrderStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tra-order-status-detail', {
            parent: 'tra-order-status',
            url: '/tra-order-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traOrderStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-order-status/tra-order-status-detail.html',
                    controller: 'TraOrderStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traOrderStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraOrderStatus', function($stateParams, TraOrderStatus) {
                    return TraOrderStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tra-order-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tra-order-status-detail.edit', {
            parent: 'tra-order-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-order-status/tra-order-status-dialog.html',
                    controller: 'TraOrderStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraOrderStatus', function(TraOrderStatus) {
                            return TraOrderStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-order-status.new', {
            parent: 'tra-order-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-order-status/tra-order-status-dialog.html',
                    controller: 'TraOrderStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tra-order-status', null, { reload: 'tra-order-status' });
                }, function() {
                    $state.go('tra-order-status');
                });
            }]
        })
        .state('tra-order-status.edit', {
            parent: 'tra-order-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-order-status/tra-order-status-dialog.html',
                    controller: 'TraOrderStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraOrderStatus', function(TraOrderStatus) {
                            return TraOrderStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-order-status', null, { reload: 'tra-order-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-order-status.delete', {
            parent: 'tra-order-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-order-status/tra-order-status-delete-dialog.html',
                    controller: 'TraOrderStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraOrderStatus', function(TraOrderStatus) {
                            return TraOrderStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-order-status', null, { reload: 'tra-order-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
