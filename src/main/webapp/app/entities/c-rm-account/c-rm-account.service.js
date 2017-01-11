(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CRMAccount', CRMAccount);

    CRMAccount.$inject = ['$resource'];

    function CRMAccount ($resource) {
        var resourceUrl =  'api/c-rm-accounts/:id';

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
