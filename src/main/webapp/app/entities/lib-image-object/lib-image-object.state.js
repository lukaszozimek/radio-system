(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-image-object', {
            parent: 'entity',
            url: '/lib-image-object',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libImageObject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-image-object/lib-image-objects.html',
                    controller: 'LibImageObjectController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libImageObject');
                    $translatePartialLoader.addPart('libImageSizeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-image-object-detail', {
            parent: 'lib-image-object',
            url: '/lib-image-object/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libImageObject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-image-object/lib-image-object-detail.html',
                    controller: 'LibImageObjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libImageObject');
                    $translatePartialLoader.addPart('libImageSizeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibImageObject', function($stateParams, LibImageObject) {
                    return LibImageObject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-image-object',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-image-object-detail.edit', {
            parent: 'lib-image-object-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-image-object/lib-image-object-dialog.html',
                    controller: 'LibImageObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibImageObject', function(LibImageObject) {
                            return LibImageObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-image-object.new', {
            parent: 'lib-image-object',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-image-object/lib-image-object-dialog.html',
                    controller: 'LibImageObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                width: null,
                                height: null,
                                imageSize: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lib-image-object', null, { reload: 'lib-image-object' });
                }, function() {
                    $state.go('lib-image-object');
                });
            }]
        })
        .state('lib-image-object.edit', {
            parent: 'lib-image-object',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-image-object/lib-image-object-dialog.html',
                    controller: 'LibImageObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibImageObject', function(LibImageObject) {
                            return LibImageObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-image-object', null, { reload: 'lib-image-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-image-object.delete', {
            parent: 'lib-image-object',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-image-object/lib-image-object-delete-dialog.html',
                    controller: 'LibImageObjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibImageObject', function(LibImageObject) {
                            return LibImageObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-image-object', null, { reload: 'lib-image-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
