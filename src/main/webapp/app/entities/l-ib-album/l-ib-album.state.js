(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-album', {
            parent: 'entity',
            url: '/l-ib-album',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBAlbum.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-album/l-ib-albums.html',
                    controller: 'LIBAlbumController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBAlbum');
                    $translatePartialLoader.addPart('lIBAlbumTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-album-detail', {
            parent: 'entity',
            url: '/l-ib-album/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBAlbum.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-album/l-ib-album-detail.html',
                    controller: 'LIBAlbumDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBAlbum');
                    $translatePartialLoader.addPart('lIBAlbumTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBAlbum', function($stateParams, LIBAlbum) {
                    return LIBAlbum.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-album',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-album-detail.edit', {
            parent: 'l-ib-album-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-album/l-ib-album-dialog.html',
                    controller: 'LIBAlbumDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBAlbum', function(LIBAlbum) {
                            return LIBAlbum.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-album.new', {
            parent: 'l-ib-album',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-album/l-ib-album-dialog.html',
                    controller: 'LIBAlbumDialogController',
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
                    $state.go('l-ib-album', null, { reload: 'l-ib-album' });
                }, function() {
                    $state.go('l-ib-album');
                });
            }]
        })
        .state('l-ib-album.edit', {
            parent: 'l-ib-album',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-album/l-ib-album-dialog.html',
                    controller: 'LIBAlbumDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBAlbum', function(LIBAlbum) {
                            return LIBAlbum.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-album', null, { reload: 'l-ib-album' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-album.delete', {
            parent: 'l-ib-album',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-album/l-ib-album-delete-dialog.html',
                    controller: 'LIBAlbumDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBAlbum', function(LIBAlbum) {
                            return LIBAlbum.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-album', null, { reload: 'l-ib-album' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
