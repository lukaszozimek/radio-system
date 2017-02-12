(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-album', {
            parent: 'entity',
            url: '/lib-album',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libAlbum.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-album/lib-albums.html',
                    controller: 'LibAlbumController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libAlbum');
                    $translatePartialLoader.addPart('libAlbumTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-album-detail', {
            parent: 'lib-album',
            url: '/lib-album/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libAlbum.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-album/lib-album-detail.html',
                    controller: 'LibAlbumDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libAlbum');
                    $translatePartialLoader.addPart('libAlbumTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibAlbum', function($stateParams, LibAlbum) {
                    return LibAlbum.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-album',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-album-detail.edit', {
            parent: 'lib-album-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-album/lib-album-dialog.html',
                    controller: 'LibAlbumDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibAlbum', function(LibAlbum) {
                            return LibAlbum.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-album.new', {
            parent: 'lib-album',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-album/lib-album-dialog.html',
                    controller: 'LibAlbumDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                albumType: null,
                                name: null,
                                releaseDate: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lib-album', null, { reload: 'lib-album' });
                }, function() {
                    $state.go('lib-album');
                });
            }]
        })
        .state('lib-album.edit', {
            parent: 'lib-album',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-album/lib-album-dialog.html',
                    controller: 'LibAlbumDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibAlbum', function(LibAlbum) {
                            return LibAlbum.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-album', null, { reload: 'lib-album' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-album.delete', {
            parent: 'lib-album',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-album/lib-album-delete-dialog.html',
                    controller: 'LibAlbumDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibAlbum', function(LibAlbum) {
                            return LibAlbum.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-album', null, { reload: 'lib-album' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
