(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tra-discount', {
            parent: 'entity',
            url: '/tra-discount',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traDiscount.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-discount/tra-discounts.html',
                    controller: 'TraDiscountController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traDiscount');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tra-discount-detail', {
            parent: 'tra-discount',
            url: '/tra-discount/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traDiscount.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-discount/tra-discount-detail.html',
                    controller: 'TraDiscountDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traDiscount');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraDiscount', function($stateParams, TraDiscount) {
                    return TraDiscount.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tra-discount',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tra-discount-detail.edit', {
            parent: 'tra-discount-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-discount/tra-discount-dialog.html',
                    controller: 'TraDiscountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraDiscount', function(TraDiscount) {
                            return TraDiscount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-discount.new', {
            parent: 'tra-discount',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-discount/tra-discount-dialog.html',
                    controller: 'TraDiscountDialogController',
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
                    $state.go('tra-discount', null, { reload: 'tra-discount' });
                }, function() {
                    $state.go('tra-discount');
                });
            }]
        })
        .state('tra-discount.edit', {
            parent: 'tra-discount',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-discount/tra-discount-dialog.html',
                    controller: 'TraDiscountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraDiscount', function(TraDiscount) {
                            return TraDiscount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-discount', null, { reload: 'tra-discount' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-discount.delete', {
            parent: 'tra-discount',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-discount/tra-discount-delete-dialog.html',
                    controller: 'TraDiscountDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraDiscount', function(TraDiscount) {
                            return TraDiscount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-discount', null, { reload: 'tra-discount' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
