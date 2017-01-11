(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-rm-contact', {
            parent: 'entity',
            url: '/c-rm-contact',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMContact.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-contact/c-rm-contacts.html',
                    controller: 'CRMContactController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMContact');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-rm-contact-detail', {
            parent: 'entity',
            url: '/c-rm-contact/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.cRMContact.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-rm-contact/c-rm-contact-detail.html',
                    controller: 'CRMContactDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cRMContact');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CRMContact', function($stateParams, CRMContact) {
                    return CRMContact.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'c-rm-contact',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('c-rm-contact-detail.edit', {
            parent: 'c-rm-contact-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-contact/c-rm-contact-dialog.html',
                    controller: 'CRMContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMContact', function(CRMContact) {
                            return CRMContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-contact.new', {
            parent: 'c-rm-contact',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-contact/c-rm-contact-dialog.html',
                    controller: 'CRMContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                shortName: null,
                                externalId1: null,
                                externalId2: null,
                                name: null,
                                paymentDelay: null,
                                vatNumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-rm-contact', null, { reload: 'c-rm-contact' });
                }, function() {
                    $state.go('c-rm-contact');
                });
            }]
        })
        .state('c-rm-contact.edit', {
            parent: 'c-rm-contact',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-contact/c-rm-contact-dialog.html',
                    controller: 'CRMContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CRMContact', function(CRMContact) {
                            return CRMContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-contact', null, { reload: 'c-rm-contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-rm-contact.delete', {
            parent: 'c-rm-contact',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-rm-contact/c-rm-contact-delete-dialog.html',
                    controller: 'CRMContactDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CRMContact', function(CRMContact) {
                            return CRMContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-rm-contact', null, { reload: 'c-rm-contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
