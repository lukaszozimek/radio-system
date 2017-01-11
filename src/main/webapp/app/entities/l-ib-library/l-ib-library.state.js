(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('l-ib-library', {
            parent: 'entity',
            url: '/l-ib-library',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBLibrary.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-library/l-ib-libraries.html',
                    controller: 'LIBLibraryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBLibrary');
                    $translatePartialLoader.addPart('lIBCounterTypeEnum');
                    $translatePartialLoader.addPart('lIBObjectTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('l-ib-library-detail', {
            parent: 'entity',
            url: '/l-ib-library/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.lIBLibrary.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/l-ib-library/l-ib-library-detail.html',
                    controller: 'LIBLibraryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lIBLibrary');
                    $translatePartialLoader.addPart('lIBCounterTypeEnum');
                    $translatePartialLoader.addPart('lIBObjectTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LIBLibrary', function($stateParams, LIBLibrary) {
                    return LIBLibrary.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'l-ib-library',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('l-ib-library-detail.edit', {
            parent: 'l-ib-library-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-library/l-ib-library-dialog.html',
                    controller: 'LIBLibraryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBLibrary', function(LIBLibrary) {
                            return LIBLibrary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-library.new', {
            parent: 'l-ib-library',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-library/l-ib-library-dialog.html',
                    controller: 'LIBLibraryDialogController',
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
                    $state.go('l-ib-library', null, { reload: 'l-ib-library' });
                }, function() {
                    $state.go('l-ib-library');
                });
            }]
        })
        .state('l-ib-library.edit', {
            parent: 'l-ib-library',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-library/l-ib-library-dialog.html',
                    controller: 'LIBLibraryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LIBLibrary', function(LIBLibrary) {
                            return LIBLibrary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-library', null, { reload: 'l-ib-library' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('l-ib-library.delete', {
            parent: 'l-ib-library',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/l-ib-library/l-ib-library-delete-dialog.html',
                    controller: 'LIBLibraryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LIBLibrary', function(LIBLibrary) {
                            return LIBLibrary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('l-ib-library', null, { reload: 'l-ib-library' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
