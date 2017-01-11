(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-marker', {
            parent: 'entity',
            url: '/l-ib-marker',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBMarker.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-marker/l-ib-markers.html',
                    controller: 'LIBMarkerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBMarker');
                    $translatePartialLoader.addPart('lIBMarkerTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-marker-detail', {
            parent: 'entity',
            url: '/l-ib-marker/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBMarker.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-marker/l-ib-marker-detail.html',
                    controller: 'LIBMarkerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBMarker');
                    $translatePartialLoader.addPart('lIBMarkerTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBMarker', function($stateParams, LIBMarker) {
                    return LIBMarker.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-marker',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-marker-detail.edit', {
            parent: 'l-ib-marker-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-marker/l-ib-marker-dialog.html',
                    controller: 'LIBMarkerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBMarker', function(LIBMarker) {
                            return LIBMarker.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-marker.new', {
            parent: 'l-ib-marker',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-marker/l-ib-marker-dialog.html',
                    controller: 'LIBMarkerDialogController',
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
                    $state.go('l-ib-marker', null, { reload: 'l-ib-marker' });
                }, function() {
                    $state.go('l-ib-marker');
                });
            }]
        })
        .state('l-ib-marker.edit', {
            parent: 'l-ib-marker',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-marker/l-ib-marker-dialog.html',
                    controller: 'LIBMarkerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBMarker', function(LIBMarker) {
                            return LIBMarker.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-marker', null, { reload: 'l-ib-marker' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-marker.delete', {
            parent: 'l-ib-marker',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-marker/l-ib-marker-delete-dialog.html',
                    controller: 'LIBMarkerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBMarker', function(LIBMarker) {
                            return LIBMarker.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-marker', null, { reload: 'l-ib-marker' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
