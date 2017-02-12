(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LibFileItem', LibFileItem);

    LibFileItem.$inject = ['$resource'];

    function LibFileItem ($resource) {
        var resourceUrl =  'api/lib-file-items/:id';

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
