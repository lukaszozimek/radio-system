(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-subscription', {
            parent: 'entity',
            url: '/cor-subscription',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corSubscription.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-subscription/cor-subscriptions.html',
                    controller: 'CorSubscriptionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corSubscription');
                    $translatePartialLoader.addPart('corSubscriptionTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-subscription-detail', {
            parent: 'cor-subscription',
            url: '/cor-subscription/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corSubscription.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-subscription/cor-subscription-detail.html',
                    controller: 'CorSubscriptionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corSubscription');
                    $translatePartialLoader.addPart('corSubscriptionTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorSubscription', function($stateParams, CorSubscription) {
                    return CorSubscription.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-subscription',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-subscription-detail.edit', {
            parent: 'cor-subscription-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-subscription/cor-subscription-dialog.html',
                    controller: 'CorSubscriptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorSubscription', function(CorSubscription) {
                            return CorSubscription.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-subscription.new', {
            parent: 'cor-subscription',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-subscription/cor-subscription-dialog.html',
                    controller: 'CorSubscriptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                price: null,
                                subscription: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cor-subscription', null, { reload: 'cor-subscription' });
                }, function() {
                    $state.go('cor-subscription');
                });
            }]
        })
        .state('cor-subscription.edit', {
            parent: 'cor-subscription',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-subscription/cor-subscription-dialog.html',
                    controller: 'CorSubscriptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorSubscription', function(CorSubscription) {
                            return CorSubscription.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-subscription', null, { reload: 'cor-subscription' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-subscription.delete', {
            parent: 'cor-subscription',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-subscription/cor-subscription-delete-dialog.html',
                    controller: 'CorSubscriptionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorSubscription', function(CorSubscription) {
                            return CorSubscription.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-subscription', null, { reload: 'cor-subscription' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
