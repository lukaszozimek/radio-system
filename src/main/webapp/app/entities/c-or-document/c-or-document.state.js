(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-document', {
            parent: 'entity',
            url: '/c-or-document',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORDocument.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-document/c-or-documents.html',
                    controller: 'CORDocumentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORDocument');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-document-detail', {
            parent: 'entity',
            url: '/c-or-document/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORDocument.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-document/c-or-document-detail.html',
                    controller: 'CORDocumentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORDocument');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORDocument', function($stateParams, CORDocument) {
                    return CORDocument.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-document',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-document-detail.edit', {
            parent: 'c-or-document-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-document/c-or-document-dialog.html',
                    controller: 'CORDocumentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORDocument', function(CORDocument) {
                            return CORDocument.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-document.new', {
            parent: 'c-or-document',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-document/c-or-document-dialog.html',
                    controller: 'CORDocumentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                key: null,
                                description: null,
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-or-document', null, { reload: 'c-or-document' });
                }, function() {
                    $state.go('c-or-document');
                });
            }]
        })
        .state('c-or-document.edit', {
            parent: 'c-or-document',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-document/c-or-document-dialog.html',
                    controller: 'CORDocumentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORDocument', function(CORDocument) {
                            return CORDocument.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-document', null, { reload: 'c-or-document' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-document.delete', {
            parent: 'c-or-document',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-document/c-or-document-delete-dialog.html',
                    controller: 'CORDocumentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORDocument', function(CORDocument) {
                            return CORDocument.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-document', null, { reload: 'c-or-document' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
