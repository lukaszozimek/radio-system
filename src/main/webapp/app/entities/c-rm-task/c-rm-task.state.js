(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-rm-task', {
            parent: 'entity',
            url: '/c-rm-task',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMTask.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-task/c-rm-tasks.html',
                    controller: 'CRMTaskController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMTask');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-rm-task-detail', {
            parent: 'entity',
            url: '/c-rm-task/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMTask.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-task/c-rm-task-detail.html',
                    controller: 'CRMTaskDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMTask');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CRMTask', function($stateParams, CRMTask) {
                    return CRMTask.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-rm-task',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-rm-task-detail.edit', {
            parent: 'c-rm-task-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-task/c-rm-task-dialog.html',
                    controller: 'CRMTaskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMTask', function(CRMTask) {
                            return CRMTask.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-task.new', {
            parent: 'c-rm-task',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-task/c-rm-task-dialog.html',
                    controller: 'CRMTaskDialogController',
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
                    $state.go('c-rm-task', null, { reload: 'c-rm-task' });
                }, function() {
                    $state.go('c-rm-task');
                });
            }]
        })
        .state('c-rm-task.edit', {
            parent: 'c-rm-task',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-task/c-rm-task-dialog.html',
                    controller: 'CRMTaskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMTask', function(CRMTask) {
                            return CRMTask.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-task', null, { reload: 'c-rm-task' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-task.delete', {
            parent: 'c-rm-task',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-task/c-rm-task-delete-dialog.html',
                    controller: 'CRMTaskDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CRMTask', function(CRMTask) {
                            return CRMTask.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-task', null, { reload: 'c-rm-task' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
