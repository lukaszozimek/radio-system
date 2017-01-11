(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-ra-block-price', {
            parent: 'entity',
            url: '/t-ra-block-price',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRABlockPrice.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-block-price/t-ra-block-prices.html',
                    controller: 'TRABlockPriceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRABlockPrice');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-ra-block-price-detail', {
            parent: 'entity',
            url: '/t-ra-block-price/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRABlockPrice.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-block-price/t-ra-block-price-detail.html',
                    controller: 'TRABlockPriceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRABlockPrice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TRABlockPrice', function($stateParams, TRABlockPrice) {
                    return TRABlockPrice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-ra-block-price',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-ra-block-price-detail.edit', {
            parent: 't-ra-block-price-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-block-price/t-ra-block-price-dialog.html',
                    controller: 'TRABlockPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRABlockPrice', function(TRABlockPrice) {
                            return TRABlockPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-block-price.new', {
            parent: 't-ra-block-price',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-block-price/t-ra-block-price-dialog.html',
                    controller: 'TRABlockPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                calculatedPrice: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-ra-block-price', null, { reload: 't-ra-block-price' });
                }, function() {
                    $state.go('t-ra-block-price');
                });
            }]
        })
        .state('t-ra-block-price.edit', {
            parent: 't-ra-block-price',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-block-price/t-ra-block-price-dialog.html',
                    controller: 'TRABlockPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRABlockPrice', function(TRABlockPrice) {
                            return TRABlockPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-block-price', null, { reload: 't-ra-block-price' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-block-price.delete', {
            parent: 't-ra-block-price',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-block-price/t-ra-block-price-delete-dialog.html',
                    controller: 'TRABlockPriceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TRABlockPrice', function(TRABlockPrice) {
                            return TRABlockPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-block-price', null, { reload: 't-ra-block-price' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
