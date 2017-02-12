(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tra-campaign', {
            parent: 'entity',
            url: '/tra-campaign',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traCampaign.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-campaign/tra-campaigns.html',
                    controller: 'TraCampaignController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traCampaign');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tra-campaign-detail', {
            parent: 'tra-campaign',
            url: '/tra-campaign/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.traCampaign.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tra-campaign/tra-campaign-detail.html',
                    controller: 'TraCampaignDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('traCampaign');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TraCampaign', function($stateParams, TraCampaign) {
                    return TraCampaign.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tra-campaign',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tra-campaign-detail.edit', {
            parent: 'tra-campaign-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-campaign/tra-campaign-dialog.html',
                    controller: 'TraCampaignDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraCampaign', function(TraCampaign) {
                            return TraCampaign.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-campaign.new', {
            parent: 'tra-campaign',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-campaign/tra-campaign-dialog.html',
                    controller: 'TraCampaignDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                startDate: null,
                                endDate: null,
                                prize: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tra-campaign', null, { reload: 'tra-campaign' });
                }, function() {
                    $state.go('tra-campaign');
                });
            }]
        })
        .state('tra-campaign.edit', {
            parent: 'tra-campaign',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-campaign/tra-campaign-dialog.html',
                    controller: 'TraCampaignDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TraCampaign', function(TraCampaign) {
                            return TraCampaign.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-campaign', null, { reload: 'tra-campaign' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tra-campaign.delete', {
            parent: 'tra-campaign',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tra-campaign/tra-campaign-delete-dialog.html',
                    controller: 'TraCampaignDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TraCampaign', function(TraCampaign) {
                            return TraCampaign.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tra-campaign', null, { reload: 'tra-campaign' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
