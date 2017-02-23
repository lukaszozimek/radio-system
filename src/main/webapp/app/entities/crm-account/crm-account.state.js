(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('crm-account', {
            parent: 'entity',
            url: '/crm-account',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmAccount.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-account/crm-accounts.html',
                    controller: 'CrmAccountController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmAccount');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('crm-account-detail', {
            parent: 'crm-account',
            url: '/crm-account/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmAccount.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-account/crm-account-detail.html',
                    controller: 'CrmAccountDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmAccount');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CrmAccount', function($stateParams, CrmAccount) {
                    return CrmAccount.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'crm-account',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('crm-account-detail.edit', {
            parent: 'crm-account-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-account/crm-account-dialog.html',
                    controller: 'CrmAccountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmAccount', function(CrmAccount) {
                            return CrmAccount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-account.new', {
            parent: 'crm-account',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-account/crm-account-dialog.html',
                    controller: 'CrmAccountDialogController',
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
                    $state.go('crm-account', null, { reload: 'crm-account' });
                }, function() {
                    $state.go('crm-account');
                });
            }]
        })
        .state('crm-account.edit', {
            parent: 'crm-account',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-account/crm-account-dialog.html',
                    controller: 'CrmAccountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmAccount', function(CrmAccount) {
                            return CrmAccount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-account', null, { reload: 'crm-account' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-account.delete', {
            parent: 'crm-account',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-account/crm-account-delete-dialog.html',
                    controller: 'CrmAccountDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CrmAccount', function(CrmAccount) {
                            return CrmAccount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-account', null, { reload: 'crm-account' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
