(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CorPropertyKey', CorPropertyKey);

    CorPropertyKey.$inject = ['$resource'];

    function CorPropertyKey ($resource) {
        var resourceUrl =  'api/cor-property-keys/:id';

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
