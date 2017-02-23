(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tra-campaing-status', {
            parent: 'entity',
            url: '/tra-campaing-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traCampaingStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-campaing-status/tra-campaing-statuses.html',
                    controller: 'TraCampaingStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traCampaingStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tra-campaing-status-detail', {
            parent: 'tra-campaing-status',
            url: '/tra-campaing-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traCampaingStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-campaing-status/tra-campaing-status-detail.html',
                    controller: 'TraCampaingStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traCampaingStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraCampaingStatus', function($stateParams, TraCampaingStatus) {
                    return TraCampaingStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tra-campaing-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tra-campaing-status-detail.edit', {
            parent: 'tra-campaing-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-campaing-status/tra-campaing-status-dialog.html',
                    controller: 'TraCampaingStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraCampaingStatus', function(TraCampaingStatus) {
                            return TraCampaingStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-campaing-status.new', {
            parent: 'tra-campaing-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-campaing-status/tra-campaing-status-dialog.html',
                    controller: 'TraCampaingStatusDialogController',
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
                    $state.go('tra-campaing-status', null, { reload: 'tra-campaing-status' });
                }, function() {
                    $state.go('tra-campaing-status');
                });
            }]
        })
        .state('tra-campaing-status.edit', {
            parent: 'tra-campaing-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-campaing-status/tra-campaing-status-dialog.html',
                    controller: 'TraCampaingStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraCampaingStatus', function(TraCampaingStatus) {
                            return TraCampaingStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-campaing-status', null, { reload: 'tra-campaing-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-campaing-status.delete', {
            parent: 'tra-campaing-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-campaing-status/tra-campaing-status-delete-dialog.html',
                    controller: 'TraCampaingStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraCampaingStatus', function(TraCampaingStatus) {
                            return TraCampaingStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-campaing-status', null, { reload: 'tra-campaing-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
