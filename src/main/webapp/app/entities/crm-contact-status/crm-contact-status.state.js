(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('crm-contact-status', {
            parent: 'entity',
            url: '/crm-contact-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmContactStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-contact-status/crm-contact-statuses.html',
                    controller: 'CrmContactStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmContactStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('crm-contact-status-detail', {
            parent: 'crm-contact-status',
            url: '/crm-contact-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmContactStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-contact-status/crm-contact-status-detail.html',
                    controller: 'CrmContactStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmContactStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CrmContactStatus', function($stateParams, CrmContactStatus) {
                    return CrmContactStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'crm-contact-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('crm-contact-status-detail.edit', {
            parent: 'crm-contact-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-contact-status/crm-contact-status-dialog.html',
                    controller: 'CrmContactStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmContactStatus', function(CrmContactStatus) {
                            return CrmContactStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-contact-status.new', {
            parent: 'crm-contact-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-contact-status/crm-contact-status-dialog.html',
                    controller: 'CrmContactStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('crm-contact-status', null, { reload: 'crm-contact-status' });
                }, function() {
                    $state.go('crm-contact-status');
                });
            }]
        })
        .state('crm-contact-status.edit', {
            parent: 'crm-contact-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-contact-status/crm-contact-status-dialog.html',
                    controller: 'CrmContactStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmContactStatus', function(CrmContactStatus) {
                            return CrmContactStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-contact-status', null, { reload: 'crm-contact-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-contact-status.delete', {
            parent: 'crm-contact-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-contact-status/crm-contact-status-delete-dialog.html',
                    controller: 'CrmContactStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CrmContactStatus', function(CrmContactStatus) {
                            return CrmContactStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-contact-status', null, { reload: 'crm-contact-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
