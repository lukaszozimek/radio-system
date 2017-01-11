(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-rm-task-status', {
            parent: 'entity',
            url: '/c-rm-task-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMTaskStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-task-status/c-rm-task-statuses.html',
                    controller: 'CRMTaskStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMTaskStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-rm-task-status-detail', {
            parent: 'entity',
            url: '/c-rm-task-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMTaskStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-task-status/c-rm-task-status-detail.html',
                    controller: 'CRMTaskStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMTaskStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CRMTaskStatus', function($stateParams, CRMTaskStatus) {
                    return CRMTaskStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-rm-task-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-rm-task-status-detail.edit', {
            parent: 'c-rm-task-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-task-status/c-rm-task-status-dialog.html',
                    controller: 'CRMTaskStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMTaskStatus', function(CRMTaskStatus) {
                            return CRMTaskStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-task-status.new', {
            parent: 'c-rm-task-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-task-status/c-rm-task-status-dialog.html',
                    controller: 'CRMTaskStatusDialogController',
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
                    $state.go('c-rm-task-status', null, { reload: 'c-rm-task-status' });
                }, function() {
                    $state.go('c-rm-task-status');
                });
            }]
        })
        .state('c-rm-task-status.edit', {
            parent: 'c-rm-task-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-task-status/c-rm-task-status-dialog.html',
                    controller: 'CRMTaskStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMTaskStatus', function(CRMTaskStatus) {
                            return CRMTaskStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-task-status', null, { reload: 'c-rm-task-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-task-status.delete', {
            parent: 'c-rm-task-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-task-status/c-rm-task-status-delete-dialog.html',
                    controller: 'CRMTaskStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CRMTaskStatus', function(CRMTaskStatus) {
                            return CRMTaskStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-task-status', null, { reload: 'c-rm-task-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
