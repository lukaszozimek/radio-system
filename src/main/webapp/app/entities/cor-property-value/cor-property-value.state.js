(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-property-value', {
            parent: 'entity',
            url: '/cor-property-value',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corPropertyValue.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-property-value/cor-property-values.html',
                    controller: 'CorPropertyValueController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corPropertyValue');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-property-value-detail', {
            parent: 'cor-property-value',
            url: '/cor-property-value/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corPropertyValue.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-property-value/cor-property-value-detail.html',
                    controller: 'CorPropertyValueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corPropertyValue');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorPropertyValue', function($stateParams, CorPropertyValue) {
                    return CorPropertyValue.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-property-value',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-property-value-detail.edit', {
            parent: 'cor-property-value-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-property-value/cor-property-value-dialog.html',
                    controller: 'CorPropertyValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorPropertyValue', function(CorPropertyValue) {
                            return CorPropertyValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-property-value.new', {
            parent: 'cor-property-value',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-property-value/cor-property-value-dialog.html',
                    controller: 'CorPropertyValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cor-property-value', null, { reload: 'cor-property-value' });
                }, function() {
                    $state.go('cor-property-value');
                });
            }]
        })
        .state('cor-property-value.edit', {
            parent: 'cor-property-value',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-property-value/cor-property-value-dialog.html',
                    controller: 'CorPropertyValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorPropertyValue', function(CorPropertyValue) {
                            return CorPropertyValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-property-value', null, { reload: 'cor-property-value' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-property-value.delete', {
            parent: 'cor-property-value',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-property-value/cor-property-value-delete-dialog.html',
                    controller: 'CorPropertyValueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorPropertyValue', function(CorPropertyValue) {
                            return CorPropertyValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-property-value', null, { reload: 'cor-property-value' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
