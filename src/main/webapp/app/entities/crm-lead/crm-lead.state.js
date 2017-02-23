(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('crm-lead', {
            parent: 'entity',
            url: '/crm-lead',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmLead.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-lead/crm-leads.html',
                    controller: 'CrmLeadController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmLead');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('crm-lead-detail', {
            parent: 'crm-lead',
            url: '/crm-lead/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmLead.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-lead/crm-lead-detail.html',
                    controller: 'CrmLeadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmLead');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CrmLead', function($stateParams, CrmLead) {
                    return CrmLead.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'crm-lead',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('crm-lead-detail.edit', {
            parent: 'crm-lead-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-lead/crm-lead-dialog.html',
                    controller: 'CrmLeadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmLead', function(CrmLead) {
                            return CrmLead.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-lead.new', {
            parent: 'crm-lead',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-lead/crm-lead-dialog.html',
                    controller: 'CrmLeadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                shortname: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('crm-lead', null, { reload: 'crm-lead' });
                }, function() {
                    $state.go('crm-lead');
                });
            }]
        })
        .state('crm-lead.edit', {
            parent: 'crm-lead',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-lead/crm-lead-dialog.html',
                    controller: 'CrmLeadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmLead', function(CrmLead) {
                            return CrmLead.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-lead', null, { reload: 'crm-lead' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-lead.delete', {
            parent: 'crm-lead',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-lead/crm-lead-delete-dialog.html',
                    controller: 'CrmLeadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CrmLead', function(CrmLead) {
                            return CrmLead.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-lead', null, { reload: 'crm-lead' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
