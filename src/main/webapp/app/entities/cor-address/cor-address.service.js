(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorAddress', CorAddress);

    CorAddress.$inject = ['$resource'];

    function CorAddress ($resource) {
        var resourceUrl =  'api/cor-addresses/:id';

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
