(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-currency', {
            parent: 'entity',
            url: '/cor-currency',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corCurrency.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-currency/cor-currencies.html',
                    controller: 'CorCurrencyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corCurrency');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-currency-detail', {
            parent: 'cor-currency',
            url: '/cor-currency/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corCurrency.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-currency/cor-currency-detail.html',
                    controller: 'CorCurrencyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corCurrency');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorCurrency', function($stateParams, CorCurrency) {
                    return CorCurrency.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-currency',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-currency-detail.edit', {
            parent: 'cor-currency-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-currency/cor-currency-dialog.html',
                    controller: 'CorCurrencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorCurrency', function(CorCurrency) {
                            return CorCurrency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-currency.new', {
            parent: 'cor-currency',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-currency/cor-currency-dialog.html',
                    controller: 'CorCurrencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                symbol: null,
                                delimiter: null,
                                shortName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cor-currency', null, { reload: 'cor-currency' });
                }, function() {
                    $state.go('cor-currency');
                });
            }]
        })
        .state('cor-currency.edit', {
            parent: 'cor-currency',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-currency/cor-currency-dialog.html',
                    controller: 'CorCurrencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorCurrency', function(CorCurrency) {
                            return CorCurrency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-currency', null, { reload: 'cor-currency' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-currency.delete', {
            parent: 'cor-currency',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-currency/cor-currency-delete-dialog.html',
                    controller: 'CorCurrencyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorCurrency', function(CorCurrency) {
                            return CorCurrency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-currency', null, { reload: 'cor-currency' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
