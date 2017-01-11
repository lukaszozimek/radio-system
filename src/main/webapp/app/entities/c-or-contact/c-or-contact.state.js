(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-or-contact', {
            parent: 'entity',
            url: '/c-or-contact',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORContact.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-contact/c-or-contacts.html',
                    controller: 'CORContactController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORContact');
                    $translatePartialLoader.addPart('cORContactTypeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-or-contact-detail', {
            parent: 'entity',
            url: '/c-or-contact/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cORContact.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-or-contact/c-or-contact-detail.html',
                    controller: 'CORContactDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cORContact');
                    $translatePartialLoader.addPart('cORContactTypeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CORContact', function($stateParams, CORContact) {
                    return CORContact.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-or-contact',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-or-contact-detail.edit', {
            parent: 'c-or-contact-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-contact/c-or-contact-dialog.html',
                    controller: 'CORContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORContact', function(CORContact) {
                            return CORContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-contact.new', {
            parent: 'c-or-contact',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-contact/c-or-contact-dialog.html',
                    controller: 'CORContactDialogController',
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
                    $state.go('c-or-contact', null, { reload: 'c-or-contact' });
                }, function() {
                    $state.go('c-or-contact');
                });
            }]
        })
        .state('c-or-contact.edit', {
            parent: 'c-or-contact',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-contact/c-or-contact-dialog.html',
                    controller: 'CORContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CORContact', function(CORContact) {
                            return CORContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-contact', null, { reload: 'c-or-contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-or-contact.delete', {
            parent: 'c-or-contact',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-or-contact/c-or-contact-delete-dialog.html',
                    controller: 'CORContactDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CORContact', function(CORContact) {
                            return CORContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-or-contact', null, { reload: 'c-or-contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
