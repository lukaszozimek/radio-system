(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sch-emission', {
            parent: 'entity',
            url: '/sch-emission',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.schEmission.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sch-emission/sch-emissions.html',
                    controller: 'SchEmissionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schEmission');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sch-emission-detail', {
            parent: 'sch-emission',
            url: '/sch-emission/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.schEmission.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sch-emission/sch-emission-detail.html',
                    controller: 'SchEmissionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schEmission');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SchEmission', function($stateParams, SchEmission) {
                    return SchEmission.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sch-emission',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sch-emission-detail.edit', {
            parent: 'sch-emission-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-emission/sch-emission-dialog.html',
                    controller: 'SchEmissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchEmission', function(SchEmission) {
                            return SchEmission.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sch-emission.new', {
            parent: 'sch-emission',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-emission/sch-emission-dialog.html',
                    controller: 'SchEmissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                seq: null,
                                name: null,
                                startTime: null,
                                endTime: null,
                                length: null,
                                dimYear: null,
                                dimMonth: null,
                                dimDay: null,
                                dimHour: null,
                                dimMinute: null,
                                dimSecond: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sch-emission', null, { reload: 'sch-emission' });
                }, function() {
                    $state.go('sch-emission');
                });
            }]
        })
        .state('sch-emission.edit', {
            parent: 'sch-emission',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-emission/sch-emission-dialog.html',
                    controller: 'SchEmissionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchEmission', function(SchEmission) {
                            return SchEmission.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sch-emission', null, { reload: 'sch-emission' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sch-emission.delete', {
            parent: 'sch-emission',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-emission/sch-emission-delete-dialog.html',
                    controller: 'SchEmissionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SchEmission', function(SchEmission) {
                            return SchEmission.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sch-emission', null, { reload: 'sch-emission' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
