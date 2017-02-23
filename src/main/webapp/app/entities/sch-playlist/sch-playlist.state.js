(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sch-playlist', {
            parent: 'entity',
            url: '/sch-playlist',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.schPlaylist.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sch-playlist/sch-playlists.html',
                    controller: 'SchPlaylistController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schPlaylist');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sch-playlist-detail', {
            parent: 'sch-playlist',
            url: '/sch-playlist/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.schPlaylist.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sch-playlist/sch-playlist-detail.html',
                    controller: 'SchPlaylistDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schPlaylist');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SchPlaylist', function($stateParams, SchPlaylist) {
                    return SchPlaylist.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sch-playlist',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sch-playlist-detail.edit', {
            parent: 'sch-playlist-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-playlist/sch-playlist-dialog.html',
                    controller: 'SchPlaylistDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchPlaylist', function(SchPlaylist) {
                            return SchPlaylist.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sch-playlist.new', {
            parent: 'sch-playlist',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-playlist/sch-playlist-dialog.html',
                    controller: 'SchPlaylistDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                dimYear: null,
                                dimMonth: null,
                                dimDay: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sch-playlist', null, { reload: 'sch-playlist' });
                }, function() {
                    $state.go('sch-playlist');
                });
            }]
        })
        .state('sch-playlist.edit', {
            parent: 'sch-playlist',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-playlist/sch-playlist-dialog.html',
                    controller: 'SchPlaylistDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchPlaylist', function(SchPlaylist) {
                            return SchPlaylist.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sch-playlist', null, { reload: 'sch-playlist' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sch-playlist.delete', {
            parent: 'sch-playlist',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-playlist/sch-playlist-delete-dialog.html',
                    controller: 'SchPlaylistDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SchPlaylist', function(SchPlaylist) {
                            return SchPlaylist.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sch-playlist', null, { reload: 'sch-playlist' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
