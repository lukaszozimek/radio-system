(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('crm-lead-source', {
            parent: 'entity',
            url: '/crm-lead-source',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmLeadSource.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-lead-source/crm-lead-sources.html',
                    controller: 'CrmLeadSourceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmLeadSource');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('crm-lead-source-detail', {
            parent: 'crm-lead-source',
            url: '/crm-lead-source/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmLeadSource.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-lead-source/crm-lead-source-detail.html',
                    controller: 'CrmLeadSourceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmLeadSource');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CrmLeadSource', function($stateParams, CrmLeadSource) {
                    return CrmLeadSource.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'crm-lead-source',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('crm-lead-source-detail.edit', {
            parent: 'crm-lead-source-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-lead-source/crm-lead-source-dialog.html',
                    controller: 'CrmLeadSourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmLeadSource', function(CrmLeadSource) {
                            return CrmLeadSource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-lead-source.new', {
            parent: 'crm-lead-source',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-lead-source/crm-lead-source-dialog.html',
                    controller: 'CrmLeadSourceDialogController',
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
                    $state.go('crm-lead-source', null, { reload: 'crm-lead-source' });
                }, function() {
                    $state.go('crm-lead-source');
                });
            }]
        })
        .state('crm-lead-source.edit', {
            parent: 'crm-lead-source',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-lead-source/crm-lead-source-dialog.html',
                    controller: 'CrmLeadSourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmLeadSource', function(CrmLeadSource) {
                            return CrmLeadSource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-lead-source', null, { reload: 'crm-lead-source' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-lead-source.delete', {
            parent: 'crm-lead-source',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-lead-source/crm-lead-source-delete-dialog.html',
                    controller: 'CrmLeadSourceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CrmLeadSource', function(CrmLeadSource) {
                            return CrmLeadSource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-lead-source', null, { reload: 'crm-lead-source' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
