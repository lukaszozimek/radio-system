(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('s-ch-emission', {
            parent: 'entity',
            url: '/s-ch-emission',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.sCHEmission.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/s-ch-emission/s-ch-emissions.html',
                    controller: 'SCHEmissionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sCHEmission');
                    $translatePartialLoader.addPart('sCHStartTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('s-ch-emission-detail', {
            parent: 'entity',
            url: '/s-ch-emission/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.sCHEmission.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/s-ch-emission/s-ch-emission-detail.html',
                    controller: 'SCHEmissionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sCHEmission');
                    $translatePartialLoader.addPart('sCHStartTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SCHEmission', function($stateParams, SCHEmission) {
                    return SCHEmission.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 's-ch-emission',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('s-ch-emission-detail.edit', {
            parent: 's-ch-emission-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-ch-emission/s-ch-emission-dialog.html',
                    controller: 'SCHEmissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SCHEmission', function(SCHEmission) {
                            return SCHEmission.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('s-ch-emission.new', {
            parent: 's-ch-emission',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-ch-emission/s-ch-emission-dialog.html',
                    controller: 'SCHEmissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                seq: null,
                                startTime: null,
                                startType: null,
                                relativeDelay: null,
                                endTime: null,
                                length: null,
                                finished: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('s-ch-emission', null, { reload: 's-ch-emission' });
                }, function() {
                    $state.go('s-ch-emission');
                });
            }]
        })
        .state('s-ch-emission.edit', {
            parent: 's-ch-emission',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-ch-emission/s-ch-emission-dialog.html',
                    controller: 'SCHEmissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SCHEmission', function(SCHEmission) {
                            return SCHEmission.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('s-ch-emission', null, { reload: 's-ch-emission' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('s-ch-emission.delete', {
            parent: 's-ch-emission',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-ch-emission/s-ch-emission-delete-dialog.html',
                    controller: 'SCHEmissionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SCHEmission', function(SCHEmission) {
                            return SCHEmission.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('s-ch-emission', null, { reload: 's-ch-emission' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
