(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CRMContact', CRMContact);

    CRMContact.$inject = ['$resource'];

    function CRMContact ($resource) {
        var resourceUrl =  'api/c-rm-contacts/:id';

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
