(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-notification', {
            parent: 'entity',
            url: '/cor-notification',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corNotification.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-notification/cor-notifications.html',
                    controller: 'CorNotificationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corNotification');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-notification-detail', {
            parent: 'cor-notification',
            url: '/cor-notification/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corNotification.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-notification/cor-notification-detail.html',
                    controller: 'CorNotificationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corNotification');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorNotification', function($stateParams, CorNotification) {
                    return CorNotification.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-notification',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-notification-detail.edit', {
            parent: 'cor-notification-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-notification/cor-notification-dialog.html',
                    controller: 'CorNotificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorNotification', function(CorNotification) {
                            return CorNotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-notification.new', {
            parent: 'cor-notification',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-notification/cor-notification-dialog.html',
                    controller: 'CorNotificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cor-notification', null, { reload: 'cor-notification' });
                }, function() {
                    $state.go('cor-notification');
                });
            }]
        })
        .state('cor-notification.edit', {
            parent: 'cor-notification',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-notification/cor-notification-dialog.html',
                    controller: 'CorNotificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorNotification', function(CorNotification) {
                            return CorNotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-notification', null, { reload: 'cor-notification' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-notification.delete', {
            parent: 'cor-notification',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-notification/cor-notification-delete-dialog.html',
                    controller: 'CorNotificationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorNotification', function(CorNotification) {
                            return CorNotification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-notification', null, { reload: 'cor-notification' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
