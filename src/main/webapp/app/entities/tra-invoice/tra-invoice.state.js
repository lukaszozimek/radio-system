(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tra-invoice', {
            parent: 'entity',
            url: '/tra-invoice',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traInvoice.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-invoice/tra-invoices.html',
                    controller: 'TraInvoiceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traInvoice');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tra-invoice-detail', {
            parent: 'tra-invoice',
            url: '/tra-invoice/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traInvoice.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-invoice/tra-invoice-detail.html',
                    controller: 'TraInvoiceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traInvoice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraInvoice', function($stateParams, TraInvoice) {
                    return TraInvoice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tra-invoice',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tra-invoice-detail.edit', {
            parent: 'tra-invoice-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-invoice/tra-invoice-dialog.html',
                    controller: 'TraInvoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraInvoice', function(TraInvoice) {
                            return TraInvoice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-invoice.new', {
            parent: 'tra-invoice',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-invoice/tra-invoice-dialog.html',
                    controller: 'TraInvoiceDialogController',
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
                    $state.go('tra-invoice', null, { reload: 'tra-invoice' });
                }, function() {
                    $state.go('tra-invoice');
                });
            }]
        })
        .state('tra-invoice.edit', {
            parent: 'tra-invoice',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-invoice/tra-invoice-dialog.html',
                    controller: 'TraInvoiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraInvoice', function(TraInvoice) {
                            return TraInvoice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-invoice', null, { reload: 'tra-invoice' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-invoice.delete', {
            parent: 'tra-invoice',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-invoice/tra-invoice-delete-dialog.html',
                    controller: 'TraInvoiceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraInvoice', function(TraInvoice) {
                            return TraInvoice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-invoice', null, { reload: 'tra-invoice' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
