(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('LIBAudioObject', LIBAudioObject);

    LIBAudioObject.$inject = ['$resource'];

    function LIBAudioObject ($resource) {
        var resourceUrl =  'api/l-ib-audio-objects/:id';

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
