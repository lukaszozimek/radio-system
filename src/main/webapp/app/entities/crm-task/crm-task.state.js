(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('crm-task', {
            parent: 'entity',
            url: '/crm-task',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmTask.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-task/crm-tasks.html',
                    controller: 'CrmTaskController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmTask');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('crm-task-detail', {
            parent: 'crm-task',
            url: '/crm-task/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmTask.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-task/crm-task-detail.html',
                    controller: 'CrmTaskDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmTask');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CrmTask', function($stateParams, CrmTask) {
                    return CrmTask.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'crm-task',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('crm-task-detail.edit', {
            parent: 'crm-task-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-task/crm-task-dialog.html',
                    controller: 'CrmTaskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmTask', function(CrmTask) {
                            return CrmTask.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-task.new', {
            parent: 'crm-task',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-task/crm-task-dialog.html',
                    controller: 'CrmTaskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                subject: null,
                                activityDate: null,
                                activityLength: null,
                                comment: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('crm-task', null, { reload: 'crm-task' });
                }, function() {
                    $state.go('crm-task');
                });
            }]
        })
        .state('crm-task.edit', {
            parent: 'crm-task',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-task/crm-task-dialog.html',
                    controller: 'CrmTaskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmTask', function(CrmTask) {
                            return CrmTask.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-task', null, { reload: 'crm-task' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-task.delete', {
            parent: 'crm-task',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-task/crm-task-delete-dialog.html',
                    controller: 'CrmTaskDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CrmTask', function(CrmTask) {
                            return CrmTask.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-task', null, { reload: 'crm-task' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
