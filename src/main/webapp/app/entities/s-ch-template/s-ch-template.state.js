(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('s-ch-template', {
            parent: 'entity',
            url: '/s-ch-template',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.sCHTemplate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/s-ch-template/s-ch-templates.html',
                    controller: 'SCHTemplateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sCHTemplate');
                    $translatePartialLoader.addPart('sCHBlockTypeEnum');
                    $translatePartialLoader.addPart('sCHStartTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('s-ch-template-detail', {
            parent: 'entity',
            url: '/s-ch-template/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.sCHTemplate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/s-ch-template/s-ch-template-detail.html',
                    controller: 'SCHTemplateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('sCHTemplate');
                    $translatePartialLoader.addPart('sCHBlockTypeEnum');
                    $translatePartialLoader.addPart('sCHStartTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SCHTemplate', function($stateParams, SCHTemplate) {
                    return SCHTemplate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 's-ch-template',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('s-ch-template-detail.edit', {
            parent: 's-ch-template-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-ch-template/s-ch-template-dialog.html',
                    controller: 'SCHTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SCHTemplate', function(SCHTemplate) {
                            return SCHTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('s-ch-template.new', {
            parent: 's-ch-template',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-ch-template/s-ch-template-dialog.html',
                    controller: 'SCHTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                seq: null,
                                name: null,
                                type: null,
                                startTime: null,
                                startType: null,
                                relativeDelay: null,
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
                    $state.go('s-ch-template', null, { reload: 's-ch-template' });
                }, function() {
                    $state.go('s-ch-template');
                });
            }]
        })
        .state('s-ch-template.edit', {
            parent: 's-ch-template',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-ch-template/s-ch-template-dialog.html',
                    controller: 'SCHTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SCHTemplate', function(SCHTemplate) {
                            return SCHTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('s-ch-template', null, { reload: 's-ch-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('s-ch-template.delete', {
            parent: 's-ch-template',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/s-ch-template/s-ch-template-delete-dialog.html',
                    controller: 'SCHTemplateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SCHTemplate', function(SCHTemplate) {
                            return SCHTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('s-ch-template', null, { reload: 's-ch-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
