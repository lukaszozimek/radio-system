(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-user', {
            parent: 'entity',
            url: '/cor-user',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corUser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-user/cor-users.html',
                    controller: 'CorUserController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corUser');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-user-detail', {
            parent: 'cor-user',
            url: '/cor-user/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corUser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-user/cor-user-detail.html',
                    controller: 'CorUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corUser');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorUser', function($stateParams, CorUser) {
                    return CorUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-user-detail.edit', {
            parent: 'cor-user-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-user/cor-user-dialog.html',
                    controller: 'CorUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorUser', function(CorUser) {
                            return CorUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-user.new', {
            parent: 'cor-user',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-user/cor-user-dialog.html',
                    controller: 'CorUserDialogController',
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
                    $state.go('cor-user', null, { reload: 'cor-user' });
                }, function() {
                    $state.go('cor-user');
                });
            }]
        })
        .state('cor-user.edit', {
            parent: 'cor-user',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-user/cor-user-dialog.html',
                    controller: 'CorUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorUser', function(CorUser) {
                            return CorUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-user', null, { reload: 'cor-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-user.delete', {
            parent: 'cor-user',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-user/cor-user-delete-dialog.html',
                    controller: 'CorUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorUser', function(CorUser) {
                            return CorUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-user', null, { reload: 'cor-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
