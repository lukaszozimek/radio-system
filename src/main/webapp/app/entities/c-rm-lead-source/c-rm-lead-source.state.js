(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-rm-lead-source', {
            parent: 'entity',
            url: '/c-rm-lead-source',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMLeadSource.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-lead-source/c-rm-lead-sources.html',
                    controller: 'CRMLeadSourceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMLeadSource');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-rm-lead-source-detail', {
            parent: 'entity',
            url: '/c-rm-lead-source/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMLeadSource.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-lead-source/c-rm-lead-source-detail.html',
                    controller: 'CRMLeadSourceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMLeadSource');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CRMLeadSource', function($stateParams, CRMLeadSource) {
                    return CRMLeadSource.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-rm-lead-source',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-rm-lead-source-detail.edit', {
            parent: 'c-rm-lead-source-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-lead-source/c-rm-lead-source-dialog.html',
                    controller: 'CRMLeadSourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMLeadSource', function(CRMLeadSource) {
                            return CRMLeadSource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-lead-source.new', {
            parent: 'c-rm-lead-source',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-lead-source/c-rm-lead-source-dialog.html',
                    controller: 'CRMLeadSourceDialogController',
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
                    $state.go('c-rm-lead-source', null, { reload: 'c-rm-lead-source' });
                }, function() {
                    $state.go('c-rm-lead-source');
                });
            }]
        })
        .state('c-rm-lead-source.edit', {
            parent: 'c-rm-lead-source',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-lead-source/c-rm-lead-source-dialog.html',
                    controller: 'CRMLeadSourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMLeadSource', function(CRMLeadSource) {
                            return CRMLeadSource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-lead-source', null, { reload: 'c-rm-lead-source' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-lead-source.delete', {
            parent: 'c-rm-lead-source',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-lead-source/c-rm-lead-source-delete-dialog.html',
                    controller: 'CRMLeadSourceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CRMLeadSource', function(CRMLeadSource) {
                            return CRMLeadSource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-lead-source', null, { reload: 'c-rm-lead-source' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
