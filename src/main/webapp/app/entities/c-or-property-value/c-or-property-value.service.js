(function() {
    'use strict';
    angular
        .module('protoneApp')
        .factory('CORPropertyValue', CORPropertyValue);

    CORPropertyValue.$inject = ['$resource'];

    function CORPropertyValue ($resource) {
        var resourceUrl =  'api/c-or-property-values/:id';

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
