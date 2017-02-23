(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sch-template', {
            parent: 'entity',
            url: '/sch-template',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.schTemplate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sch-template/sch-templates.html',
                    controller: 'SchTemplateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schTemplate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('sch-template-detail', {
            parent: 'sch-template',
            url: '/sch-template/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.schTemplate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sch-template/sch-template-detail.html',
                    controller: 'SchTemplateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schTemplate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SchTemplate', function($stateParams, SchTemplate) {
                    return SchTemplate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sch-template',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sch-template-detail.edit', {
            parent: 'sch-template-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-template/sch-template-dialog.html',
                    controller: 'SchTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchTemplate', function(SchTemplate) {
                            return SchTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sch-template.new', {
            parent: 'sch-template',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-template/sch-template-dialog.html',
                    controller: 'SchTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sch-template', null, { reload: 'sch-template' });
                }, function() {
                    $state.go('sch-template');
                });
            }]
        })
        .state('sch-template.edit', {
            parent: 'sch-template',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-template/sch-template-dialog.html',
                    controller: 'SchTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchTemplate', function(SchTemplate) {
                            return SchTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sch-template', null, { reload: 'sch-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sch-template.delete', {
            parent: 'sch-template',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sch-template/sch-template-delete-dialog.html',
                    controller: 'SchTemplateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SchTemplate', function(SchTemplate) {
                            return SchTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sch-template', null, { reload: 'sch-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
