(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-tax', {
            parent: 'entity',
            url: '/cor-tax',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corTax.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-tax/cor-taxes.html',
                    controller: 'CorTaxController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corTax');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-tax-detail', {
            parent: 'cor-tax',
            url: '/cor-tax/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corTax.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-tax/cor-tax-detail.html',
                    controller: 'CorTaxDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corTax');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorTax', function($stateParams, CorTax) {
                    return CorTax.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-tax',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-tax-detail.edit', {
            parent: 'cor-tax-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-tax/cor-tax-dialog.html',
                    controller: 'CorTaxDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorTax', function(CorTax) {
                            return CorTax.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-tax.new', {
            parent: 'cor-tax',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-tax/cor-tax-dialog.html',
                    controller: 'CorTaxDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                value: null,
                                validFrom: null,
                                validTo: null,
                                active: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cor-tax', null, { reload: 'cor-tax' });
                }, function() {
                    $state.go('cor-tax');
                });
            }]
        })
        .state('cor-tax.edit', {
            parent: 'cor-tax',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-tax/cor-tax-dialog.html',
                    controller: 'CorTaxDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorTax', function(CorTax) {
                            return CorTax.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-tax', null, { reload: 'cor-tax' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-tax.delete', {
            parent: 'cor-tax',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-tax/cor-tax-delete-dialog.html',
                    controller: 'CorTaxDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorTax', function(CorTax) {
                            return CorTax.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-tax', null, { reload: 'cor-tax' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
