(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LibMarker', LibMarker);

    LibMarker.$inject = ['$resource'];

    function LibMarker ($resource) {
        var resourceUrl =  'api/lib-markers/:id';

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
