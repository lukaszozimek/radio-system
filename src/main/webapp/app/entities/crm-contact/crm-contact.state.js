(function() {
    'use strict';

    angular
        .module('protoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('crm-contact', {
            parent: 'entity',
            url: '/crm-contact',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmContact.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-contact/crm-contacts.html',
                    controller: 'CrmContactController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmContact');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('crm-contact-detail', {
            parent: 'crm-contact',
            url: '/crm-contact/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'protoneApp.crmContact.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/crm-contact/crm-contact-detail.html',
                    controller: 'CrmContactDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('crmContact');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CrmContact', function($stateParams, CrmContact) {
                    return CrmContact.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'crm-contact',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('crm-contact-detail.edit', {
            parent: 'crm-contact-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-contact/crm-contact-dialog.html',
                    controller: 'CrmContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmContact', function(CrmContact) {
                            return CrmContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-contact.new', {
            parent: 'crm-contact',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-contact/crm-contact-dialog.html',
                    controller: 'CrmContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                shortName: null,
                                externalId1: null,
                                externalId2: null,
                                paymentDate: null,
                                name: null,
                                paymentDelay: null,
                                vatNumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('crm-contact', null, { reload: 'crm-contact' });
                }, function() {
                    $state.go('crm-contact');
                });
            }]
        })
        .state('crm-contact.edit', {
            parent: 'crm-contact',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-contact/crm-contact-dialog.html',
                    controller: 'CrmContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CrmContact', function(CrmContact) {
                            return CrmContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-contact', null, { reload: 'crm-contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('crm-contact.delete', {
            parent: 'crm-contact',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/crm-contact/crm-contact-delete-dialog.html',
                    controller: 'CrmContactDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CrmContact', function(CrmContact) {
                            return CrmContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('crm-contact', null, { reload: 'crm-contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
