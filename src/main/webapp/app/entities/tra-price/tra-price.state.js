(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tra-price', {
            parent: 'entity',
            url: '/tra-price',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traPrice.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-price/tra-prices.html',
                    controller: 'TraPriceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traPrice');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tra-price-detail', {
            parent: 'tra-price',
            url: '/tra-price/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traPrice.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-price/tra-price-detail.html',
                    controller: 'TraPriceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traPrice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraPrice', function($stateParams, TraPrice) {
                    return TraPrice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tra-price',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tra-price-detail.edit', {
            parent: 'tra-price-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-price/tra-price-dialog.html',
                    controller: 'TraPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraPrice', function(TraPrice) {
                            return TraPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-price.new', {
            parent: 'tra-price',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-price/tra-price-dialog.html',
                    controller: 'TraPriceDialogController',
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
                    $state.go('tra-price', null, { reload: 'tra-price' });
                }, function() {
                    $state.go('tra-price');
                });
            }]
        })
        .state('tra-price.edit', {
            parent: 'tra-price',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-price/tra-price-dialog.html',
                    controller: 'TraPriceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraPrice', function(TraPrice) {
                            return TraPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-price', null, { reload: 'tra-price' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-price.delete', {
            parent: 'tra-price',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-price/tra-price-delete-dialog.html',
                    controller: 'TraPriceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraPrice', function(TraPrice) {
                            return TraPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-price', null, { reload: 'tra-price' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
