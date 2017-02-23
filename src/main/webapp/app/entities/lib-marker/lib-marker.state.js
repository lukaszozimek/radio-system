(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-marker', {
            parent: 'entity',
            url: '/lib-marker',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libMarker.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-marker/lib-markers.html',
                    controller: 'LibMarkerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libMarker');
                    $translatePartialLoader.addPart('libMarkerTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-marker-detail', {
            parent: 'lib-marker',
            url: '/lib-marker/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libMarker.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-marker/lib-marker-detail.html',
                    controller: 'LibMarkerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libMarker');
                    $translatePartialLoader.addPart('libMarkerTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibMarker', function($stateParams, LibMarker) {
                    return LibMarker.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-marker',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-marker-detail.edit', {
            parent: 'lib-marker-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-marker/lib-marker-dialog.html',
                    controller: 'LibMarkerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibMarker', function(LibMarker) {
                            return LibMarker.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-marker.new', {
            parent: 'lib-marker',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-marker/lib-marker-dialog.html',
                    controller: 'LibMarkerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                markerType: null,
                                name: null,
                                startTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lib-marker', null, { reload: 'lib-marker' });
                }, function() {
                    $state.go('lib-marker');
                });
            }]
        })
        .state('lib-marker.edit', {
            parent: 'lib-marker',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-marker/lib-marker-dialog.html',
                    controller: 'LibMarkerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibMarker', function(LibMarker) {
                            return LibMarker.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-marker', null, { reload: 'lib-marker' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-marker.delete', {
            parent: 'lib-marker',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-marker/lib-marker-delete-dialog.html',
                    controller: 'LibMarkerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibMarker', function(LibMarker) {
                            return LibMarker.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-marker', null, { reload: 'lib-marker' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
