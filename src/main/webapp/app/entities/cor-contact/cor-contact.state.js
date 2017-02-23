(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cor-contact', {
            parent: 'entity',
            url: '/cor-contact',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corContact.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-contact/cor-contacts.html',
                    controller: 'CorContactController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corContact');
                    $translatePartialLoader.addPart('corContactTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cor-contact-detail', {
            parent: 'cor-contact',
            url: '/cor-contact/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.corContact.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cor-contact/cor-contact-detail.html',
                    controller: 'CorContactDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('corContact');
                    $translatePartialLoader.addPart('corContactTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CorContact', function($stateParams, CorContact) {
                    return CorContact.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cor-contact',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cor-contact-detail.edit', {
            parent: 'cor-contact-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-contact/cor-contact-dialog.html',
                    controller: 'CorContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorContact', function(CorContact) {
                            return CorContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-contact.new', {
            parent: 'cor-contact',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-contact/cor-contact-dialog.html',
                    controller: 'CorContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                contact: null,
                                contactType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cor-contact', null, { reload: 'cor-contact' });
                }, function() {
                    $state.go('cor-contact');
                });
            }]
        })
        .state('cor-contact.edit', {
            parent: 'cor-contact',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-contact/cor-contact-dialog.html',
                    controller: 'CorContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CorContact', function(CorContact) {
                            return CorContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-contact', null, { reload: 'cor-contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cor-contact.delete', {
            parent: 'cor-contact',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cor-contact/cor-contact-delete-dialog.html',
                    controller: 'CorContactDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CorContact', function(CorContact) {
                            return CorContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cor-contact', null, { reload: 'cor-contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
