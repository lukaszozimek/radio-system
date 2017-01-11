(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-fg-marker-configuration', {
            parent: 'entity',
            url: '/c-fg-marker-configuration',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cFGMarkerConfiguration.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-fg-marker-configuration/c-fg-marker-configurations.html',
                    controller: 'CFGMarkerConfigurationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cFGMarkerConfiguration');
                    $translatePartialLoader.addPart('lIBMarkerTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-fg-marker-configuration-detail', {
            parent: 'entity',
            url: '/c-fg-marker-configuration/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cFGMarkerConfiguration.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-fg-marker-configuration/c-fg-marker-configuration-detail.html',
                    controller: 'CFGMarkerConfigurationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cFGMarkerConfiguration');
                    $translatePartialLoader.addPart('lIBMarkerTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CFGMarkerConfiguration', function($stateParams, CFGMarkerConfiguration) {
                    return CFGMarkerConfiguration.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-fg-marker-configuration',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-fg-marker-configuration-detail.edit', {
            parent: 'c-fg-marker-configuration-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-fg-marker-configuration/c-fg-marker-configuration-dialog.html',
                    controller: 'CFGMarkerConfigurationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CFGMarkerConfiguration', function(CFGMarkerConfiguration) {
                            return CFGMarkerConfiguration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-fg-marker-configuration.new', {
            parent: 'c-fg-marker-configuration',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-fg-marker-configuration/c-fg-marker-configuration-dialog.html',
                    controller: 'CFGMarkerConfigurationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                displayName: null,
                                color: null,
                                keyboardShortcut: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-fg-marker-configuration', null, { reload: 'c-fg-marker-configuration' });
                }, function() {
                    $state.go('c-fg-marker-configuration');
                });
            }]
        })
        .state('c-fg-marker-configuration.edit', {
            parent: 'c-fg-marker-configuration',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-fg-marker-configuration/c-fg-marker-configuration-dialog.html',
                    controller: 'CFGMarkerConfigurationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CFGMarkerConfiguration', function(CFGMarkerConfiguration) {
                            return CFGMarkerConfiguration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-fg-marker-configuration', null, { reload: 'c-fg-marker-configuration' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-fg-marker-configuration.delete', {
            parent: 'c-fg-marker-configuration',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-fg-marker-configuration/c-fg-marker-configuration-delete-dialog.html',
                    controller: 'CFGMarkerConfigurationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CFGMarkerConfiguration', function(CFGMarkerConfiguration) {
                            return CFGMarkerConfiguration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-fg-marker-configuration', null, { reload: 'c-fg-marker-configuration' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
