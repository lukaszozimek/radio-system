(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-ra-price', {
            parent: 'entity',
            url: '/t-ra-price',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRAPrice.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-price/t-ra-prices.html',
                    controller: 'TRAPriceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRAPrice');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-ra-price-detail', {
            parent: 'entity',
            url: '/t-ra-price/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRAPrice.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-price/t-ra-price-detail.html',
                    controller: 'TRAPriceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRAPrice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TRAPrice', function($stateParams, TRAPrice) {
                    return TRAPrice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-ra-price',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-ra-price-detail.edit', {
            parent: 't-ra-price-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-price/t-ra-price-dialog.html',
                    controller: 'TRAPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRAPrice', function(TRAPrice) {
                            return TRAPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-price.new', {
            parent: 't-ra-price',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-price/t-ra-price-dialog.html',
                    controller: 'TRAPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                validFrom: null,
                                validTo: null,
                                price: null,
                                baseLength: null,
                                priceAlternative: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-ra-price', null, { reload: 't-ra-price' });
                }, function() {
                    $state.go('t-ra-price');
                });
            }]
        })
        .state('t-ra-price.edit', {
            parent: 't-ra-price',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-price/t-ra-price-dialog.html',
                    controller: 'TRAPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRAPrice', function(TRAPrice) {
                            return TRAPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-price', null, { reload: 't-ra-price' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-price.delete', {
            parent: 't-ra-price',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-price/t-ra-price-delete-dialog.html',
                    controller: 'TRAPriceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TRAPrice', function(TRAPrice) {
                            return TRAPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-price', null, { reload: 't-ra-price' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
