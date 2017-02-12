(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-library', {
            parent: 'entity',
            url: '/lib-library',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libLibrary.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-library/lib-libraries.html',
                    controller: 'LibLibraryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libLibrary');
                    $translatePartialLoader.addPart('libCounterTypeEnum');
                    $translatePartialLoader.addPart('libObjectTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-library-detail', {
            parent: 'lib-library',
            url: '/lib-library/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libLibrary.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-library/lib-library-detail.html',
                    controller: 'LibLibraryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libLibrary');
                    $translatePartialLoader.addPart('libCounterTypeEnum');
                    $translatePartialLoader.addPart('libObjectTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibLibrary', function($stateParams, LibLibrary) {
                    return LibLibrary.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-library',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-library-detail.edit', {
            parent: 'lib-library-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-library/lib-library-dialog.html',
                    controller: 'LibLibraryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibLibrary', function(LibLibrary) {
                            return LibLibrary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-library.new', {
            parent: 'lib-library',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-library/lib-library-dialog.html',
                    controller: 'LibLibraryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                prefix: null,
                                idxLength: null,
                                shortcut: null,
                                name: null,
                                counter: null,
                                counterType: null,
                                libraryType: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lib-library', null, { reload: 'lib-library' });
                }, function() {
                    $state.go('lib-library');
                });
            }]
        })
        .state('lib-library.edit', {
            parent: 'lib-library',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-library/lib-library-dialog.html',
                    controller: 'LibLibraryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibLibrary', function(LibLibrary) {
                            return LibLibrary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-library', null, { reload: 'lib-library' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-library.delete', {
            parent: 'lib-library',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-library/lib-library-delete-dialog.html',
                    controller: 'LibLibraryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibLibrary', function(LibLibrary) {
                            return LibLibrary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-library', null, { reload: 'lib-library' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
