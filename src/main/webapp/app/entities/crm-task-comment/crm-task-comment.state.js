(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('crm-task-comment', {
            parent: 'entity',
            url: '/crm-task-comment',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmTaskComment.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-task-comment/crm-task-comments.html',
                    controller: 'CrmTaskCommentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmTaskComment');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('crm-task-comment-detail', {
            parent: 'crm-task-comment',
            url: '/crm-task-comment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmTaskComment.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-task-comment/crm-task-comment-detail.html',
                    controller: 'CrmTaskCommentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmTaskComment');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CrmTaskComment', function($stateParams, CrmTaskComment) {
                    return CrmTaskComment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'crm-task-comment',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('crm-task-comment-detail.edit', {
            parent: 'crm-task-comment-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-task-comment/crm-task-comment-dialog.html',
                    controller: 'CrmTaskCommentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmTaskComment', function(CrmTaskComment) {
                            return CrmTaskComment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-task-comment.new', {
            parent: 'crm-task-comment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-task-comment/crm-task-comment-dialog.html',
                    controller: 'CrmTaskCommentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                comment: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('crm-task-comment', null, { reload: 'crm-task-comment' });
                }, function() {
                    $state.go('crm-task-comment');
                });
            }]
        })
        .state('crm-task-comment.edit', {
            parent: 'crm-task-comment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-task-comment/crm-task-comment-dialog.html',
                    controller: 'CrmTaskCommentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmTaskComment', function(CrmTaskComment) {
                            return CrmTaskComment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-task-comment', null, { reload: 'crm-task-comment' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-task-comment.delete', {
            parent: 'crm-task-comment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-task-comment/crm-task-comment-delete-dialog.html',
                    controller: 'CrmTaskCommentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CrmTaskComment', function(CrmTaskComment) {
                            return CrmTaskComment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-task-comment', null, { reload: 'crm-task-comment' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
