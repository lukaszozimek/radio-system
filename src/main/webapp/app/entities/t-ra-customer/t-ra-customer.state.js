(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-ra-customer', {
            parent: 'entity',
            url: '/t-ra-customer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRACustomer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-customer/t-ra-customers.html',
                    controller: 'TRACustomerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRACustomer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-ra-customer-detail', {
            parent: 'entity',
            url: '/t-ra-customer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRACustomer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-customer/t-ra-customer-detail.html',
                    controller: 'TRACustomerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRACustomer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TRACustomer', function($stateParams, TRACustomer) {
                    return TRACustomer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-ra-customer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-ra-customer-detail.edit', {
            parent: 't-ra-customer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-customer/t-ra-customer-dialog.html',
                    controller: 'TRACustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRACustomer', function(TRACustomer) {
                            return TRACustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-customer.new', {
            parent: 't-ra-customer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-customer/t-ra-customer-dialog.html',
                    controller: 'TRACustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                size: null,
                                range: null,
                                area: null,
                                vatNumber: null,
                                idNumber1: null,
                                idNumber2: null,
                                paymentDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-ra-customer', null, { reload: 't-ra-customer' });
                }, function() {
                    $state.go('t-ra-customer');
                });
            }]
        })
        .state('t-ra-customer.edit', {
            parent: 't-ra-customer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-customer/t-ra-customer-dialog.html',
                    controller: 'TRACustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRACustomer', function(TRACustomer) {
                            return TRACustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-customer', null, { reload: 't-ra-customer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-customer.delete', {
            parent: 't-ra-customer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-customer/t-ra-customer-delete-dialog.html',
                    controller: 'TRACustomerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TRACustomer', function(TRACustomer) {
                            return TRACustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-customer', null, { reload: 't-ra-customer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
