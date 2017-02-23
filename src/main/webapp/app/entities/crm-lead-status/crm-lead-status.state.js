(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('crm-lead-status', {
            parent: 'entity',
            url: '/crm-lead-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmLeadStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-lead-status/crm-lead-statuses.html',
                    controller: 'CrmLeadStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmLeadStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('crm-lead-status-detail', {
            parent: 'crm-lead-status',
            url: '/crm-lead-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmLeadStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-lead-status/crm-lead-status-detail.html',
                    controller: 'CrmLeadStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmLeadStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CrmLeadStatus', function($stateParams, CrmLeadStatus) {
                    return CrmLeadStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'crm-lead-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('crm-lead-status-detail.edit', {
            parent: 'crm-lead-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-lead-status/crm-lead-status-dialog.html',
                    controller: 'CrmLeadStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmLeadStatus', function(CrmLeadStatus) {
                            return CrmLeadStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-lead-status.new', {
            parent: 'crm-lead-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-lead-status/crm-lead-status-dialog.html',
                    controller: 'CrmLeadStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('crm-lead-status', null, { reload: 'crm-lead-status' });
                }, function() {
                    $state.go('crm-lead-status');
                });
            }]
        })
        .state('crm-lead-status.edit', {
            parent: 'crm-lead-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-lead-status/crm-lead-status-dialog.html',
                    controller: 'CrmLeadStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmLeadStatus', function(CrmLeadStatus) {
                            return CrmLeadStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-lead-status', null, { reload: 'crm-lead-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-lead-status.delete', {
            parent: 'crm-lead-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-lead-status/crm-lead-status-delete-dialog.html',
                    controller: 'CrmLeadStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CrmLeadStatus', function(CrmLeadStatus) {
                            return CrmLeadStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-lead-status', null, { reload: 'crm-lead-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
