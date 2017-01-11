(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CORAddress', CORAddress);

    CORAddress.$inject = ['$resource'];

    function CORAddress ($resource) {
        var resourceUrl =  'api/c-or-addresses/:id';

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
