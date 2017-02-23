(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LibLabel', LibLabel);

    LibLabel.$inject = ['$resource'];

    function LibLabel ($resource) {
        var resourceUrl =  'api/lib-labels/:id';

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
