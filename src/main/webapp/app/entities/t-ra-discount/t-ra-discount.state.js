(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('t-ra-discount', {
            parent: 'entity',
            url: '/t-ra-discount',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRADiscount.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-discount/t-ra-discounts.html',
                    controller: 'TRADiscountController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRADiscount');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('t-ra-discount-detail', {
            parent: 'entity',
            url: '/t-ra-discount/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.tRADiscount.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/t-ra-discount/t-ra-discount-detail.html',
                    controller: 'TRADiscountDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tRADiscount');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TRADiscount', function($stateParams, TRADiscount) {
                    return TRADiscount.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 't-ra-discount',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('t-ra-discount-detail.edit', {
            parent: 't-ra-discount-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-discount/t-ra-discount-dialog.html',
                    controller: 'TRADiscountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRADiscount', function(TRADiscount) {
                            return TRADiscount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-discount.new', {
            parent: 't-ra-discount',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-discount/t-ra-discount-dialog.html',
                    controller: 'TRADiscountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                validFrom: null,
                                validTo: null,
                                discount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('t-ra-discount', null, { reload: 't-ra-discount' });
                }, function() {
                    $state.go('t-ra-discount');
                });
            }]
        })
        .state('t-ra-discount.edit', {
            parent: 't-ra-discount',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-discount/t-ra-discount-dialog.html',
                    controller: 'TRADiscountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TRADiscount', function(TRADiscount) {
                            return TRADiscount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-discount', null, { reload: 't-ra-discount' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('t-ra-discount.delete', {
            parent: 't-ra-discount',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/t-ra-discount/t-ra-discount-delete-dialog.html',
                    controller: 'TRADiscountDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TRADiscount', function(TRADiscount) {
                            return TRADiscount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('t-ra-discount', null, { reload: 't-ra-discount' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
