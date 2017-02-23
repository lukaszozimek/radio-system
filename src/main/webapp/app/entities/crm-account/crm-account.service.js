(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CrmAccount', CrmAccount);

    CrmAccount.$inject = ['$resource'];

    function CrmAccount ($resource) {
        var resourceUrl =  'api/crm-accounts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
