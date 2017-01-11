(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBMarker', LIBMarker);

    LIBMarker.$inject = ['$resource'];

    function LIBMarker ($resource) {
        var resourceUrl =  'api/l-ib-markers/:id';

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
