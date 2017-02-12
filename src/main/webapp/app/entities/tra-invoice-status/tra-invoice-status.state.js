(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tra-invoice-status', {
            parent: 'entity',
            url: '/tra-invoice-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traInvoiceStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-invoice-status/tra-invoice-statuses.html',
                    controller: 'TraInvoiceStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traInvoiceStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tra-invoice-status-detail', {
            parent: 'tra-invoice-status',
            url: '/tra-invoice-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traInvoiceStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-invoice-status/tra-invoice-status-detail.html',
                    controller: 'TraInvoiceStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traInvoiceStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraInvoiceStatus', function($stateParams, TraInvoiceStatus) {
                    return TraInvoiceStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tra-invoice-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tra-invoice-status-detail.edit', {
            parent: 'tra-invoice-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-invoice-status/tra-invoice-status-dialog.html',
                    controller: 'TraInvoiceStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraInvoiceStatus', function(TraInvoiceStatus) {
                            return TraInvoiceStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-invoice-status.new', {
            parent: 'tra-invoice-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-invoice-status/tra-invoice-status-dialog.html',
                    controller: 'TraInvoiceStatusDialogController',
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
                    $state.go('tra-invoice-status', null, { reload: 'tra-invoice-status' });
                }, function() {
                    $state.go('tra-invoice-status');
                });
            }]
        })
        .state('tra-invoice-status.edit', {
            parent: 'tra-invoice-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-invoice-status/tra-invoice-status-dialog.html',
                    controller: 'TraInvoiceStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraInvoiceStatus', function(TraInvoiceStatus) {
                            return TraInvoiceStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-invoice-status', null, { reload: 'tra-invoice-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-invoice-status.delete', {
            parent: 'tra-invoice-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-invoice-status/tra-invoice-status-delete-dialog.html',
                    controller: 'TraInvoiceStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraInvoiceStatus', function(TraInvoiceStatus) {
                            return TraInvoiceStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-invoice-status', null, { reload: 'tra-invoice-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
