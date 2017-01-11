(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CORDocument', CORDocument);

    CORDocument.$inject = ['$resource'];

    function CORDocument ($resource) {
        var resourceUrl =  'api/c-or-documents/:id';

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
