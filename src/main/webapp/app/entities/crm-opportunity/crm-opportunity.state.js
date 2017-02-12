(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('crm-opportunity', {
            parent: 'entity',
            url: '/crm-opportunity',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmOpportunity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-opportunity/crm-opportunities.html',
                    controller: 'CrmOpportunityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmOpportunity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('crm-opportunity-detail', {
            parent: 'crm-opportunity',
            url: '/crm-opportunity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmOpportunity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-opportunity/crm-opportunity-detail.html',
                    controller: 'CrmOpportunityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmOpportunity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CrmOpportunity', function($stateParams, CrmOpportunity) {
                    return CrmOpportunity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'crm-opportunity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('crm-opportunity-detail.edit', {
            parent: 'crm-opportunity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-opportunity/crm-opportunity-dialog.html',
                    controller: 'CrmOpportunityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmOpportunity', function(CrmOpportunity) {
                            return CrmOpportunity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-opportunity.new', {
            parent: 'crm-opportunity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-opportunity/crm-opportunity-dialog.html',
                    controller: 'CrmOpportunityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                lastTry: null,
                                closeDate: null,
                                probability: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('crm-opportunity', null, { reload: 'crm-opportunity' });
                }, function() {
                    $state.go('crm-opportunity');
                });
            }]
        })
        .state('crm-opportunity.edit', {
            parent: 'crm-opportunity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-opportunity/crm-opportunity-dialog.html',
                    controller: 'CrmOpportunityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmOpportunity', function(CrmOpportunity) {
                            return CrmOpportunity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-opportunity', null, { reload: 'crm-opportunity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-opportunity.delete', {
            parent: 'crm-opportunity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-opportunity/crm-opportunity-delete-dialog.html',
                    controller: 'CrmOpportunityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CrmOpportunity', function(CrmOpportunity) {
                            return CrmOpportunity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-opportunity', null, { reload: 'crm-opportunity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
