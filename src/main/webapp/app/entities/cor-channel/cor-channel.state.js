(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-channel', {
            parent: 'entity',
            url: '/cor-channel',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corChannel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-channel/cor-channels.html',
                    controller: 'CorChannelController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corChannel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-channel-detail', {
            parent: 'cor-channel',
            url: '/cor-channel/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corChannel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-channel/cor-channel-detail.html',
                    controller: 'CorChannelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corChannel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorChannel', function($stateParams, CorChannel) {
                    return CorChannel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-channel',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-channel-detail.edit', {
            parent: 'cor-channel-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-channel/cor-channel-dialog.html',
                    controller: 'CorChannelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorChannel', function(CorChannel) {
                            return CorChannel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-channel.new', {
            parent: 'cor-channel',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-channel/cor-channel-dialog.html',
                    controller: 'CorChannelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                shortcut: null,
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cor-channel', null, { reload: 'cor-channel' });
                }, function() {
                    $state.go('cor-channel');
                });
            }]
        })
        .state('cor-channel.edit', {
            parent: 'cor-channel',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-channel/cor-channel-dialog.html',
                    controller: 'CorChannelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorChannel', function(CorChannel) {
                            return CorChannel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-channel', null, { reload: 'cor-channel' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-channel.delete', {
            parent: 'cor-channel',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-channel/cor-channel-delete-dialog.html',
                    controller: 'CorChannelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorChannel', function(CorChannel) {
                            return CorChannel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-channel', null, { reload: 'cor-channel' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
