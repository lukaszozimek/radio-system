(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-status', {
            parent: 'entity',
            url: '/cor-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-status/cor-statuses.html',
                    controller: 'CorStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-status-detail', {
            parent: 'cor-status',
            url: '/cor-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-status/cor-status-detail.html',
                    controller: 'CorStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorStatus', function($stateParams, CorStatus) {
                    return CorStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-status-detail.edit', {
            parent: 'cor-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-status/cor-status-dialog.html',
                    controller: 'CorStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorStatus', function(CorStatus) {
                            return CorStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-status.new', {
            parent: 'cor-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-status/cor-status-dialog.html',
                    controller: 'CorStatusDialogController',
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
                    $state.go('cor-status', null, { reload: 'cor-status' });
                }, function() {
                    $state.go('cor-status');
                });
            }]
        })
        .state('cor-status.edit', {
            parent: 'cor-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-status/cor-status-dialog.html',
                    controller: 'CorStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorStatus', function(CorStatus) {
                            return CorStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-status', null, { reload: 'cor-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-status.delete', {
            parent: 'cor-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-status/cor-status-delete-dialog.html',
                    controller: 'CorStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorStatus', function(CorStatus) {
                            return CorStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-status', null, { reload: 'cor-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
