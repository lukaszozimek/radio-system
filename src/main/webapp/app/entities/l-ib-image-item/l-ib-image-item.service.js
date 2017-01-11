(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBImageItem', LIBImageItem);

    LIBImageItem.$inject = ['$resource'];

    function LIBImageItem ($resource) {
        var resourceUrl =  'api/l-ib-image-items/:id';

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
