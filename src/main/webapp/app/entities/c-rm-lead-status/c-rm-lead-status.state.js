(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-rm-lead-status', {
            parent: 'entity',
            url: '/c-rm-lead-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMLeadStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-lead-status/c-rm-lead-statuses.html',
                    controller: 'CRMLeadStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMLeadStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-rm-lead-status-detail', {
            parent: 'entity',
            url: '/c-rm-lead-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMLeadStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-lead-status/c-rm-lead-status-detail.html',
                    controller: 'CRMLeadStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMLeadStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CRMLeadStatus', function($stateParams, CRMLeadStatus) {
                    return CRMLeadStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-rm-lead-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-rm-lead-status-detail.edit', {
            parent: 'c-rm-lead-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-lead-status/c-rm-lead-status-dialog.html',
                    controller: 'CRMLeadStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMLeadStatus', function(CRMLeadStatus) {
                            return CRMLeadStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-lead-status.new', {
            parent: 'c-rm-lead-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-lead-status/c-rm-lead-status-dialog.html',
                    controller: 'CRMLeadStatusDialogController',
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
                    $state.go('c-rm-lead-status', null, { reload: 'c-rm-lead-status' });
                }, function() {
                    $state.go('c-rm-lead-status');
                });
            }]
        })
        .state('c-rm-lead-status.edit', {
            parent: 'c-rm-lead-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-lead-status/c-rm-lead-status-dialog.html',
                    controller: 'CRMLeadStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMLeadStatus', function(CRMLeadStatus) {
                            return CRMLeadStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-lead-status', null, { reload: 'c-rm-lead-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-lead-status.delete', {
            parent: 'c-rm-lead-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-lead-status/c-rm-lead-status-delete-dialog.html',
                    controller: 'CRMLeadStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CRMLeadStatus', function(CRMLeadStatus) {
                            return CRMLeadStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-lead-status', null, { reload: 'c-rm-lead-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
