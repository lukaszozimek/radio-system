(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lib-label', {
            parent: 'entity',
            url: '/lib-label',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libLabel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-label/lib-labels.html',
                    controller: 'LibLabelController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libLabel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lib-label-detail', {
            parent: 'lib-label',
            url: '/lib-label/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.libLabel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lib-label/lib-label-detail.html',
                    controller: 'LibLabelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('libLabel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LibLabel', function($stateParams, LibLabel) {
                    return LibLabel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lib-label',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lib-label-detail.edit', {
            parent: 'lib-label-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-label/lib-label-dialog.html',
                    controller: 'LibLabelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibLabel', function(LibLabel) {
                            return LibLabel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-label.new', {
            parent: 'lib-label',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-label/lib-label-dialog.html',
                    controller: 'LibLabelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lib-label', null, { reload: 'lib-label' });
                }, function() {
                    $state.go('lib-label');
                });
            }]
        })
        .state('lib-label.edit', {
            parent: 'lib-label',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-label/lib-label-dialog.html',
                    controller: 'LibLabelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LibLabel', function(LibLabel) {
                            return LibLabel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-label', null, { reload: 'lib-label' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lib-label.delete', {
            parent: 'lib-label',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lib-label/lib-label-delete-dialog.html',
                    controller: 'LibLabelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LibLabel', function(LibLabel) {
                            return LibLabel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lib-label', null, { reload: 'lib-label' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
