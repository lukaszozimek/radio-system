(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cfg-marker-configuration', {
            parent: 'entity',
            url: '/cfg-marker-configuration',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cfgMarkerConfiguration.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfg-marker-configuration/cfg-marker-configurations.html',
                    controller: 'CfgMarkerConfigurationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cfgMarkerConfiguration');
                    $translatePartialLoader.addPart('libMarkerTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cfg-marker-configuration-detail', {
            parent: 'cfg-marker-configuration',
            url: '/cfg-marker-configuration/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cfgMarkerConfiguration.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfg-marker-configuration/cfg-marker-configuration-detail.html',
                    controller: 'CfgMarkerConfigurationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cfgMarkerConfiguration');
                    $translatePartialLoader.addPart('libMarkerTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CfgMarkerConfiguration', function($stateParams, CfgMarkerConfiguration) {
                    return CfgMarkerConfiguration.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cfg-marker-configuration',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cfg-marker-configuration-detail.edit', {
            parent: 'cfg-marker-configuration-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfg-marker-configuration/cfg-marker-configuration-dialog.html',
                    controller: 'CfgMarkerConfigurationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CfgMarkerConfiguration', function(CfgMarkerConfiguration) {
                            return CfgMarkerConfiguration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cfg-marker-configuration.new', {
            parent: 'cfg-marker-configuration',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfg-marker-configuration/cfg-marker-configuration-dialog.html',
                    controller: 'CfgMarkerConfigurationDialogController',
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
                    $state.go('cfg-marker-configuration', null, { reload: 'cfg-marker-configuration' });
                }, function() {
                    $state.go('cfg-marker-configuration');
                });
            }]
        })
        .state('cfg-marker-configuration.edit', {
            parent: 'cfg-marker-configuration',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfg-marker-configuration/cfg-marker-configuration-dialog.html',
                    controller: 'CfgMarkerConfigurationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CfgMarkerConfiguration', function(CfgMarkerConfiguration) {
                            return CfgMarkerConfiguration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfg-marker-configuration', null, { reload: 'cfg-marker-configuration' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cfg-marker-configuration.delete', {
            parent: 'cfg-marker-configuration',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfg-marker-configuration/cfg-marker-configuration-delete-dialog.html',
                    controller: 'CfgMarkerConfigurationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CfgMarkerConfiguration', function(CfgMarkerConfiguration) {
                            return CfgMarkerConfiguration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfg-marker-configuration', null, { reload: 'cfg-marker-configuration' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
