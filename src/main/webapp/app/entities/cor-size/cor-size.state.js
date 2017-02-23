(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-size', {
            parent: 'entity',
            url: '/cor-size',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corSize.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-size/cor-sizes.html',
                    controller: 'CorSizeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corSize');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-size-detail', {
            parent: 'cor-size',
            url: '/cor-size/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corSize.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-size/cor-size-detail.html',
                    controller: 'CorSizeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corSize');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorSize', function($stateParams, CorSize) {
                    return CorSize.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-size',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-size-detail.edit', {
            parent: 'cor-size-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-size/cor-size-dialog.html',
                    controller: 'CorSizeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorSize', function(CorSize) {
                            return CorSize.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-size.new', {
            parent: 'cor-size',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-size/cor-size-dialog.html',
                    controller: 'CorSizeDialogController',
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
                    $state.go('cor-size', null, { reload: 'cor-size' });
                }, function() {
                    $state.go('cor-size');
                });
            }]
        })
        .state('cor-size.edit', {
            parent: 'cor-size',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-size/cor-size-dialog.html',
                    controller: 'CorSizeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorSize', function(CorSize) {
                            return CorSize.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-size', null, { reload: 'cor-size' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-size.delete', {
            parent: 'cor-size',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-size/cor-size-delete-dialog.html',
                    controller: 'CorSizeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorSize', function(CorSize) {
                            return CorSize.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-size', null, { reload: 'cor-size' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
