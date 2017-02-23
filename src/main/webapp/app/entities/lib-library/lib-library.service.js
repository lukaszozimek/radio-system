(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LibLibrary', LibLibrary);

    LibLibrary.$inject = ['$resource'];

    function LibLibrary ($resource) {
        var resourceUrl =  'api/lib-libraries/:id';

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
