(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('crm-task-status', {
            parent: 'entity',
            url: '/crm-task-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmTaskStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-task-status/crm-task-statuses.html',
                    controller: 'CrmTaskStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmTaskStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('crm-task-status-detail', {
            parent: 'crm-task-status',
            url: '/crm-task-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmTaskStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-task-status/crm-task-status-detail.html',
                    controller: 'CrmTaskStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmTaskStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CrmTaskStatus', function($stateParams, CrmTaskStatus) {
                    return CrmTaskStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'crm-task-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('crm-task-status-detail.edit', {
            parent: 'crm-task-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-task-status/crm-task-status-dialog.html',
                    controller: 'CrmTaskStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmTaskStatus', function(CrmTaskStatus) {
                            return CrmTaskStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-task-status.new', {
            parent: 'crm-task-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-task-status/crm-task-status-dialog.html',
                    controller: 'CrmTaskStatusDialogController',
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
                    $state.go('crm-task-status', null, { reload: 'crm-task-status' });
                }, function() {
                    $state.go('crm-task-status');
                });
            }]
        })
        .state('crm-task-status.edit', {
            parent: 'crm-task-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-task-status/crm-task-status-dialog.html',
                    controller: 'CrmTaskStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmTaskStatus', function(CrmTaskStatus) {
                            return CrmTaskStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-task-status', null, { reload: 'crm-task-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-task-status.delete', {
            parent: 'crm-task-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-task-status/crm-task-status-delete-dialog.html',
                    controller: 'CrmTaskStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CrmTaskStatus', function(CrmTaskStatus) {
                            return CrmTaskStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-task-status', null, { reload: 'crm-task-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
