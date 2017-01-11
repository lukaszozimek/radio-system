(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-image-object', {
            parent: 'entity',
            url: '/l-ib-image-object',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBImageObject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-image-object/l-ib-image-objects.html',
                    controller: 'LIBImageObjectController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBImageObject');
                    $translatePartialLoader.addPart('lIBImageSizeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-image-object-detail', {
            parent: 'entity',
            url: '/l-ib-image-object/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBImageObject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-image-object/l-ib-image-object-detail.html',
                    controller: 'LIBImageObjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBImageObject');
                    $translatePartialLoader.addPart('lIBImageSizeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBImageObject', function($stateParams, LIBImageObject) {
                    return LIBImageObject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-image-object',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-image-object-detail.edit', {
            parent: 'l-ib-image-object-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-image-object/l-ib-image-object-dialog.html',
                    controller: 'LIBImageObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBImageObject', function(LIBImageObject) {
                            return LIBImageObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-image-object.new', {
            parent: 'l-ib-image-object',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-image-object/l-ib-image-object-dialog.html',
                    controller: 'LIBImageObjectDialogController',
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
                    $state.go('l-ib-image-object', null, { reload: 'l-ib-image-object' });
                }, function() {
                    $state.go('l-ib-image-object');
                });
            }]
        })
        .state('l-ib-image-object.edit', {
            parent: 'l-ib-image-object',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-image-object/l-ib-image-object-dialog.html',
                    controller: 'LIBImageObjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBImageObject', function(LIBImageObject) {
                            return LIBImageObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-image-object', null, { reload: 'l-ib-image-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-image-object.delete', {
            parent: 'l-ib-image-object',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-image-object/l-ib-image-object-delete-dialog.html',
                    controller: 'LIBImageObjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBImageObject', function(LIBImageObject) {
                            return LIBImageObject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-image-object', null, { reload: 'l-ib-image-object' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
