(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBMediaItem', LIBMediaItem);

    LIBMediaItem.$inject = ['$resource'];

    function LIBMediaItem ($resource) {
        var resourceUrl =  'api/l-ib-media-items/:id';

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
