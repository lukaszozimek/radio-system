(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBVideoObject', LIBVideoObject);

    LIBVideoObject.$inject = ['$resource'];

    function LIBVideoObject ($resource) {
        var resourceUrl =  'api/l-ib-video-objects/:id';

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
