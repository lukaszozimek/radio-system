(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-rm-account', {
            parent: 'entity',
            url: '/c-rm-account',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMAccount.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-account/c-rm-accounts.html',
                    controller: 'CRMAccountController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMAccount');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-rm-account-detail', {
            parent: 'entity',
            url: '/c-rm-account/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMAccount.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-account/c-rm-account-detail.html',
                    controller: 'CRMAccountDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMAccount');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CRMAccount', function($stateParams, CRMAccount) {
                    return CRMAccount.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-rm-account',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-rm-account-detail.edit', {
            parent: 'c-rm-account-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-account/c-rm-account-dialog.html',
                    controller: 'CRMAccountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMAccount', function(CRMAccount) {
                            return CRMAccount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-account.new', {
            parent: 'c-rm-account',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-account/c-rm-account-dialog.html',
                    controller: 'CRMAccountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                shortName: null,
                                externalId1: null,
                                externalId2: null,
                                name: null,
                                paymentDelay: null,
                                vatNumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-rm-account', null, { reload: 'c-rm-account' });
                }, function() {
                    $state.go('c-rm-account');
                });
            }]
        })
        .state('c-rm-account.edit', {
            parent: 'c-rm-account',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-account/c-rm-account-dialog.html',
                    controller: 'CRMAccountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMAccount', function(CRMAccount) {
                            return CRMAccount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-account', null, { reload: 'c-rm-account' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-account.delete', {
            parent: 'c-rm-account',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-account/c-rm-account-delete-dialog.html',
                    controller: 'CRMAccountDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CRMAccount', function(CRMAccount) {
                            return CRMAccount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-account', null, { reload: 'c-rm-account' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
