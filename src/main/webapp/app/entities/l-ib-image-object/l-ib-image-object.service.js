(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBImageObject', LIBImageObject);

    LIBImageObject.$inject = ['$resource'];

    function LIBImageObject ($resource) {
        var resourceUrl =  'api/l-ib-image-objects/:id';

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
