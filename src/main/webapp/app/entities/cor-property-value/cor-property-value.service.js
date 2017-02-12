(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorPropertyValue', CorPropertyValue);

    CorPropertyValue.$inject = ['$resource'];

    function CorPropertyValue ($resource) {
        var resourceUrl =  'api/cor-property-values/:id';

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
