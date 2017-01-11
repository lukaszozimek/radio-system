(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-ra-invoice', {
            parent: 'entity',
            url: '/t-ra-invoice',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRAInvoice.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-invoice/t-ra-invoices.html',
                    controller: 'TRAInvoiceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRAInvoice');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-ra-invoice-detail', {
            parent: 'entity',
            url: '/t-ra-invoice/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRAInvoice.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-invoice/t-ra-invoice-detail.html',
                    controller: 'TRAInvoiceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRAInvoice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TRAInvoice', function($stateParams, TRAInvoice) {
                    return TRAInvoice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-ra-invoice',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-ra-invoice-detail.edit', {
            parent: 't-ra-invoice-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-invoice/t-ra-invoice-dialog.html',
                    controller: 'TRAInvoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRAInvoice', function(TRAInvoice) {
                            return TRAInvoice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-invoice.new', {
            parent: 't-ra-invoice',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-invoice/t-ra-invoice-dialog.html',
                    controller: 'TRAInvoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                paid: null,
                                price: null,
                                paymentDay: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-ra-invoice', null, { reload: 't-ra-invoice' });
                }, function() {
                    $state.go('t-ra-invoice');
                });
            }]
        })
        .state('t-ra-invoice.edit', {
            parent: 't-ra-invoice',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-invoice/t-ra-invoice-dialog.html',
                    controller: 'TRAInvoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRAInvoice', function(TRAInvoice) {
                            return TRAInvoice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-invoice', null, { reload: 't-ra-invoice' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-invoice.delete', {
            parent: 't-ra-invoice',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-invoice/t-ra-invoice-delete-dialog.html',
                    controller: 'TRAInvoiceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TRAInvoice', function(TRAInvoice) {
                            return TRAInvoice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-invoice', null, { reload: 't-ra-invoice' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
