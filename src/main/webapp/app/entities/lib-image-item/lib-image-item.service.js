(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LibImageItem', LibImageItem);

    LibImageItem.$inject = ['$resource'];

    function LibImageItem ($resource) {
        var resourceUrl =  'api/lib-image-items/:id';

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
