(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-area', {
            parent: 'entity',
            url: '/cor-area',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corArea.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-area/cor-areas.html',
                    controller: 'CorAreaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corArea');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-area-detail', {
            parent: 'cor-area',
            url: '/cor-area/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corArea.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-area/cor-area-detail.html',
                    controller: 'CorAreaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corArea');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorArea', function($stateParams, CorArea) {
                    return CorArea.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-area',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-area-detail.edit', {
            parent: 'cor-area-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-area/cor-area-dialog.html',
                    controller: 'CorAreaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorArea', function(CorArea) {
                            return CorArea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-area.new', {
            parent: 'cor-area',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-area/cor-area-dialog.html',
                    controller: 'CorAreaDialogController',
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
                    $state.go('cor-area', null, { reload: 'cor-area' });
                }, function() {
                    $state.go('cor-area');
                });
            }]
        })
        .state('cor-area.edit', {
            parent: 'cor-area',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-area/cor-area-dialog.html',
                    controller: 'CorAreaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorArea', function(CorArea) {
                            return CorArea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-area', null, { reload: 'cor-area' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-area.delete', {
            parent: 'cor-area',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-area/cor-area-delete-dialog.html',
                    controller: 'CorAreaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorArea', function(CorArea) {
                            return CorArea.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-area', null, { reload: 'cor-area' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
